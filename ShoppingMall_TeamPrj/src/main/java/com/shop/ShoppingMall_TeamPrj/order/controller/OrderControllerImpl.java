package com.shop.ShoppingMall_TeamPrj.order.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.order.service.OrderService;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;
import com.shop.ShoppingMall_TeamPrj.cart.service.CartService;
import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

@Controller
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;


    // 토스 결제 요청 메서드 (결제 진행을 위한 API 호출)
    public ModelAndView initiatePayment(
            java.util.Map<String, String> paymentDetails, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        
        System.out.println("[DEBUG] initiatePayment called.");
        System.out.println("[DEBUG] Payment Details: " + paymentDetails);
        
        String apiUrl = "https://api.toss.im/v1/transactions";  // 실제 API URL로 변경
        System.out.println("[DEBUG] API URL: " + apiUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");  // 클라이언트 키
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");        // 시크릿 키
        System.out.println("[DEBUG] Headers: " + headers);
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("order_id", paymentDetails.get("order_id"));
        body.add("amount", paymentDetails.get("amount"));
        body.add("currency", "KRW");
        body.add("payment_method", paymentDetails.get("payment_method"));
        body.add("return_url", paymentDetails.get("return_url"));
        System.out.println("[DEBUG] Request Body: " + body);
        
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
        
        String paymentResponse = responseEntity.getBody();
        System.out.println("[DEBUG] Payment Response: " + paymentResponse);
        
        // 결제 요청 결과를 보여줄 페이지: order/paymentPage.jsp (원하는 뷰 이름으로 수정 가능)
        ModelAndView mav = new ModelAndView("order/paymentPage");
        mav.addObject("paymentResponse", paymentResponse);
        System.out.println("[DEBUG] initiatePayment returning view 'order/paymentPage' with paymentResponse.");
        return mav;
    }

    // 토스 결제 결과 확인 메서드 (테스트용)
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] checkPaymentStatus called with paymentId: " + paymentId);
        
        String apiUrl = "https://api.toss.im/v1/transactions/" + paymentId;  // 실제 API URL로 변경
        System.out.println("[DEBUG] API URL for payment status: " + apiUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");
        System.out.println("[DEBUG] Headers for status check: " + headers);
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        
        String paymentStatus = responseEntity.getBody();
        System.out.println("[DEBUG] Payment Status Response: " + paymentStatus);
        
        ModelAndView mav = new ModelAndView("paymentResult");
        mav.addObject("paymentStatus", paymentStatus);
        System.out.println("[DEBUG] checkPaymentStatus returning view 'paymentResult' with paymentStatus.");
        return mav;
    }

    // 개별 상품 주문 메서드 (기존 주문 로직)
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] orderEachGoods called with OrderVO: " + orderVO);
        ModelAndView mav = new ModelAndView("orderDetailPage");
        mav.addObject("orderDetails", orderVO);
        System.out.println("[DEBUG] orderEachGoods returning view 'orderDetailPage' with orderDetails.");
        return mav;
    }
    
    /**
     * [Phase 1] 결제 전 단계: 배송정보 등 주문 정보를 입력받아 임시로 세션에 저장하는 메서드.
     * 이 단계에서는 주문 정보를 DB에 기록하지 않습니다.
     */
    @RequestMapping(value="/order/myOrder.do", method=RequestMethod.POST)
    public ModelAndView myOrder(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] myOrder (Phase 1) called.");
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] myOrder: 세션이 없습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] myOrder: 로그인 정보가 없습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        System.out.println("[DEBUG] myOrder: Retrieved memberInfo: " + memberVO);
        
        // 로그인한 회원 정보 설정
        orderVO.setUser_id(memberVO.getUser_id());
        System.out.println("[DEBUG] myOrder: Set user_id in OrderVO: " + orderVO.getUser_id());
        
        // 현재 시간을 주문 날짜로 설정
        orderVO.setOrder_date(new java.sql.Date(System.currentTimeMillis()));
        System.out.println("[DEBUG] myOrder: Set order_date in OrderVO: " + orderVO.getOrder_date());
        
        // 배송 상태 기본값 설정 (예: "주문접수")
        if (orderVO.getDelivery_state() == null || orderVO.getDelivery_state().trim().equals("")) {
            orderVO.setDelivery_state("주문접수");
            System.out.println("[DEBUG] myOrder: Set default delivery_state as '주문접수'.");
        }
        
        // 디버그: OrderVO 전체 내용 출력
        System.out.println("[DEBUG] myOrder: OrderVO details: " + orderVO);
        
        // 임시 주문 정보를 세션에 저장 (DB에는 아직 기록하지 않음)
        session.setAttribute("tempOrder", orderVO);
        System.out.println("[DEBUG] myOrder: Saved OrderVO in session as 'tempOrder'.");
        
        // 결제 진행을 위한 페이지(예: order/paymentPage.jsp)로 이동
        ModelAndView mav = new ModelAndView("order/paymentPage");
        mav.addObject("orderDetails", orderVO);
        System.out.println("[DEBUG] myOrder: Redirecting to view 'order/paymentPage' with orderDetails.");
        return mav;
    }
    
    /**
     * [Phase 2] 최종 주문 완료: 장바구니의 모든 상품 정보를 기반으로 주문 레코드를 생성합니다.
     * 모든 상품에 대해 개별 주문 레코드를 생성하며, 동일한 order_group_id를 부여합니다.
     * 주문 생성 후 세션의 임시 주문 정보와 장바구니 데이터를 정리하고, 마이페이지로 리디렉션합니다.
     */


    @RequestMapping(value="/order/completeOrder.do", method=RequestMethod.POST)
    public ModelAndView completeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] completeOrder (Phase 2) called.");
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] completeOrder: 세션이 없습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] completeOrder: 로그인 정보가 없습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        System.out.println("[DEBUG] completeOrder: Retrieved memberInfo: " + memberVO);
        
        // 공통 배송/결제 정보 (tempOrder)
        OrderVO commonOrderInfo = (OrderVO) session.getAttribute("tempOrder");
        if (commonOrderInfo == null) {
            System.out.println("[DEBUG] completeOrder: 임시 주문 정보가 없습니다.");
            return new ModelAndView("redirect:/order/myOrder.do");
        }
        System.out.println("[DEBUG] completeOrder: Retrieved tempOrder from session: " + commonOrderInfo);
        
        // 장바구니 데이터
        @SuppressWarnings("unchecked")
        Map<String, List> cartMap = (Map<String, List>) session.getAttribute("cartMap");
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] completeOrder: 장바구니 데이터가 없습니다.");
            return new ModelAndView("redirect:/cart/myCartList.do");
        }
        List<CartVO> myCartList = (List<CartVO>) cartMap.get("myCartList");
        List<GoodsVO> myGoodsList = (List<GoodsVO>) cartMap.get("myGoodsList");

        System.out.println("[DEBUG] completeOrder: myCartList size = " + myCartList.size());
        System.out.println("[DEBUG] completeOrder: myGoodsList size = " + myGoodsList.size());
        
        // 생성할 주문 그룹 ID (모든 주문 항목에 동일하게 부여)
        int orderGroupId = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);
        System.out.println("[DEBUG] completeOrder: Generated order_group_id = " + orderGroupId);

        // 모든 장바구니 상품에 대해 주문 생성
        for (int i = 0; i < myCartList.size(); i++) {
            CartVO cartItem = myCartList.get(i);
            GoodsVO goodsItem = myGoodsList.get(i); // 인덱스가 일치한다고 가정
            OrderVO orderItem = new OrderVO();

            orderItem.setUser_id(commonOrderInfo.getUser_id());
            orderItem.setProduct_id(cartItem.getProduct_id());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder_date(new java.sql.Date(System.currentTimeMillis()));
            // 상품 가격과 수량을 곱하여 주문 개별 총액 계산
            double itemTotalPrice = goodsItem.getPrice() * cartItem.getQuantity();
            orderItem.setTotal_price(itemTotalPrice);
            
            // 공통 배송/결제 정보 설정
            orderItem.setDelivery_state(commonOrderInfo.getDelivery_state());
            orderItem.setReceiver_name(commonOrderInfo.getReceiver_name());
            orderItem.setReceiver_hp(commonOrderInfo.getReceiver_hp());
            orderItem.setReceiver_tel(commonOrderInfo.getReceiver_tel());
            orderItem.setDelivery_address(commonOrderInfo.getDelivery_address());
            orderItem.setDelivery_message(commonOrderInfo.getDelivery_message());
            orderItem.setPay_method(commonOrderInfo.getPay_method());
            orderItem.setOrder_group_id(orderGroupId);
            
            // 상품명은 GoodsVO에서 가져오기
            orderItem.setProduct_name(goodsItem.getProduct_name());
            
            System.out.println("[DEBUG] completeOrder: Processing orderItem " + i + " -> product_id: " + 
                orderItem.getProduct_id() + ", product_name: " + orderItem.getProduct_name() +
                ", quantity: " + orderItem.getQuantity() + ", itemTotalPrice: " + orderItem.getTotal_price());
            
            // 주문 DB에 저장
            orderService.createOrder(orderItem);
            System.out.println("[DEBUG] completeOrder: Order item " + i + " created in DB.");
        }
        
        // 주문 생성 후 세션 정리
        session.removeAttribute("tempOrder");
        session.removeAttribute("cartMap");
        System.out.println("[DEBUG] completeOrder: Cleared tempOrder and cartMap from session.");
        
        // 장바구니 데이터도 삭제 (CartService에 구현된 메서드 호출)
        cartService.clearCartForUser(memberVO.getUser_id());
        System.out.println("[DEBUG] completeOrder: Cleared cart for user: " + memberVO.getUser_id());
        
        // 최종 주문 완료 후 마이페이지로 리디렉션
        System.out.println("[DEBUG] completeOrder: Redirecting to 'myPageMain' with success message.");
        
        // URL에 포함되는 한글 메시지는 URL 인코딩 처리 필요
        String message = "주문이 성공적으로 완료되었습니다.";
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        
        return new ModelAndView("redirect:/mypage/myPageMain.do?message=" + encodedMessage);
    }

    
    @RequestMapping(value="/success", method=RequestMethod.GET)
    public ModelAndView success(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] success method called.");
        ModelAndView mav = new ModelAndView("order/success");
        String paymentType = request.getParameter("paymentType");
        String orderId = request.getParameter("orderId");
        String paymentKey = request.getParameter("paymentKey");
        String amount = request.getParameter("amount");
        
        System.out.println("[DEBUG] success: paymentType = " + paymentType);
        System.out.println("[DEBUG] success: orderId = " + orderId);
        System.out.println("[DEBUG] success: paymentKey = " + paymentKey);
        System.out.println("[DEBUG] success: amount = " + amount);
        
        mav.addObject("paymentType", paymentType);
        mav.addObject("orderId", orderId);
        mav.addObject("paymentKey", paymentKey);
        mav.addObject("amount", amount);
        System.out.println("[DEBUG] success: Returning view 'order/success' with payment details.");
        return mav;
    }
}
