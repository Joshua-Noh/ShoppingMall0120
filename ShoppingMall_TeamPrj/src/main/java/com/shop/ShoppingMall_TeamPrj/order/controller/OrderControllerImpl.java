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

    // ���� ��û ó�� �޼��� (���� ��û �� �佺 API ȣ��)
    public ModelAndView initiatePayment(
            java.util.Map<String, String> paymentDetails, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        
        System.out.println("[DEBUG] initiatePayment called.");
        System.out.println("[DEBUG] Payment Details: " + paymentDetails);
        
        String apiUrl = "https://api.toss.im/v1/transactions";  // �佺 API URL ����
        System.out.println("[DEBUG] API URL: " + apiUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");  // �׽�Ʈ Ŭ���̾�Ʈ Ű ����
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");        // �׽�Ʈ ��ũ�� Ű ����
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
        
        // ���� ���� ����� ������ ��: order/paymentPage.jsp (���� �Ϸ� �� ������ ������)
        ModelAndView mav = new ModelAndView("order/paymentPage");
        mav.addObject("paymentResponse", paymentResponse);
        System.out.println("[DEBUG] initiatePayment returning view 'order/paymentPage' with paymentResponse.");
        return mav;
    }

    // ���� ���� Ȯ�� ó�� �޼��� (���� ���� ��ȸ)
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] checkPaymentStatus called with paymentId: " + paymentId);
        
        String apiUrl = "https://api.toss.im/v1/transactions/" + paymentId;  // �佺 API URL ���� (���� ���� ��ȸ��)
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

    // ���� ��ǰ �ֹ� �� ������ �����ִ� �޼���
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] orderEachGoods called with OrderVO: " + orderVO);
        ModelAndView mav = new ModelAndView("orderDetailPage");
        mav.addObject("orderDetails", orderVO);
        System.out.println("[DEBUG] orderEachGoods returning view 'orderDetailPage' with orderDetails.");
        return mav;
    }
    
    /**
     * [Phase 1] �ֹ� ��û ó��: �ֹ� ������ �ӽ÷� ���ǿ� �����Ͽ� ���� ��û ȭ������ �����Ѵ�.
     * ���� ���� ��û ������ DB�� ������ �غ� �Ѵ�.
     */
    @RequestMapping(value="/order/myOrder.do", method=RequestMethod.POST)
    public ModelAndView myOrder(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] myOrder (Phase 1) called.");
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] myOrder: ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] myOrder: �α��ε� ȸ�� ������ �����ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        System.out.println("[DEBUG] myOrder: Retrieved memberInfo: " + memberVO);
        
        // �α����� ȸ���� ���̵� OrderVO�� ����
        orderVO.setUser_id(memberVO.getUser_id());
        System.out.println("[DEBUG] myOrder: Set user_id in OrderVO: " + orderVO.getUser_id());
        
        // �ֹ����ڸ� ���� �ð����� ����
        orderVO.setOrder_date(new java.sql.Date(System.currentTimeMillis()));
        System.out.println("[DEBUG] myOrder: Set order_date in OrderVO: " + orderVO.getOrder_date());
        
        // ��� ���� �⺻�� ���� (��: "�ֹ�����")
        if (orderVO.getDelivery_state() == null || orderVO.getDelivery_state().trim().equals("")) {
            orderVO.setDelivery_state("�ֹ�����");
            System.out.println("[DEBUG] myOrder: Set default delivery_state as '�ֹ�����'.");
        }
        
        // �ֹ� ����(OrderVO) ���� Ȯ�� (������)
        System.out.println("[DEBUG] myOrder: OrderVO details: " + orderVO);
        
        // �ֹ� ������ �ӽ� ���ǿ� ���� (���� ���� �� ���� ����)
        session.setAttribute("tempOrder", orderVO);
        System.out.println("[DEBUG] myOrder: Saved OrderVO in session as 'tempOrder'.");
        
        // ���� ��û ȭ������ �̵� (��: order/paymentPage.jsp)
        ModelAndView mav = new ModelAndView("order/paymentPage");
        mav.addObject("orderDetails", orderVO);
        System.out.println("[DEBUG] myOrder: Redirecting to view 'order/paymentPage' with orderDetails.");
        return mav;
    }
    
    /**
     * [Phase 2] �ֹ� ���� �Ϸ� ó��: �� ��ǰ�� �ֹ� ������ DB�� ����ϰ�,
     * ���ÿ� �ֹ� �׷� ���̵� �ο��Ͽ� ���� ��ǰ �ֹ��� �ϳ��� �׷����� �����Ѵ�.
     * �ֹ� �Ϸ� �� ���� �� ��ٱ��� ������ �����ϰ�, �ֹ� �Ϸ� �޽����� �����Ѵ�.
     */
    @RequestMapping(value="/order/completeOrder.do", method=RequestMethod.POST)
    public ModelAndView completeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] completeOrder (Phase 2) called.");
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] completeOrder: ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] completeOrder: �α��ε� ȸ�� ������ �����ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        System.out.println("[DEBUG] completeOrder: Retrieved memberInfo: " + memberVO);
        
        // �ֹ�/���� ���� (tempOrder) ��������
        OrderVO commonOrderInfo = (OrderVO) session.getAttribute("tempOrder");
        if (commonOrderInfo == null) {
            System.out.println("[DEBUG] completeOrder: �ӽ� �ֹ� ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/order/myOrder.do");
        }
        System.out.println("[DEBUG] completeOrder: Retrieved tempOrder from session: " + commonOrderInfo);
        
        // ��ٱ��� ����(cartMap) ��������
        @SuppressWarnings("unchecked")
        Map<String, List> cartMap = (Map<String, List>) session.getAttribute("cartMap");
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] completeOrder: ��ٱ��� ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/cart/myCartList.do");
        }
        List<CartVO> myCartList = (List<CartVO>) cartMap.get("myCartList");
        List<GoodsVO> myGoodsList = (List<GoodsVO>) cartMap.get("myGoodsList");

        System.out.println("[DEBUG] completeOrder: myCartList size = " + myCartList.size());
        System.out.println("[DEBUG] completeOrder: myGoodsList size = " + myGoodsList.size());
        
        // �ֹ� �׷� ID ���� (�� �ֹ��� �ϳ��� �׷����� ���� ���� �ӽ� ID ����)
        int orderGroupId = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);
        System.out.println("[DEBUG] completeOrder: Generated order_group_id = " + orderGroupId);

        // �� ��ǰ�� �ֹ� ������ DB�� ���
        for (int i = 0; i < myCartList.size(); i++) {
            CartVO cartItem = myCartList.get(i);
            GoodsVO goodsItem = myGoodsList.get(i); // ��ٱ��Ͽ� ��ǰ ����� �ε����� ��ġ�Ѵٰ� ����
            OrderVO orderItem = new OrderVO();

            orderItem.setUser_id(commonOrderInfo.getUser_id());
            orderItem.setProduct_id(cartItem.getProduct_id());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder_date(new java.sql.Date(System.currentTimeMillis()));
            // ��ǰ�� �� �ݾ� ��� (��ǰ ���� * ����)
            double itemTotalPrice = goodsItem.getPrice() * cartItem.getQuantity();
            orderItem.setTotal_price(itemTotalPrice);
            
            // �ֹ� ����(��� ���� ��) ����
            orderItem.setDelivery_state(commonOrderInfo.getDelivery_state());
            orderItem.setReceiver_name(commonOrderInfo.getReceiver_name());
            orderItem.setReceiver_hp(commonOrderInfo.getReceiver_hp());
            orderItem.setReceiver_tel(commonOrderInfo.getReceiver_tel());
            orderItem.setDelivery_address(commonOrderInfo.getDelivery_address());
            orderItem.setDelivery_message(commonOrderInfo.getDelivery_message());
            orderItem.setPay_method(commonOrderInfo.getPay_method());
            orderItem.setOrder_group_id(orderGroupId);
            
            // �ֹ� �� ������ ��ǰ�� ����
            orderItem.setProduct_name(goodsItem.getProduct_name());
            
            System.out.println("[DEBUG] completeOrder: Processing orderItem " + i + " -> product_id: " + 
                orderItem.getProduct_id() + ", product_name: " + orderItem.getProduct_name() +
                ", quantity: " + orderItem.getQuantity() + ", itemTotalPrice: " + orderItem.getTotal_price());
            
            // �ֹ� ������ DB�� ���
            orderService.createOrder(orderItem);
            System.out.println("[DEBUG] completeOrder: Order item " + i + " created in DB.");
        }
        
        // �ֹ� �Ϸ� �� ������ �ӽ� �ֹ� ������ ��ٱ��� ���� ����
        session.removeAttribute("tempOrder");
        session.removeAttribute("cartMap");
        System.out.println("[DEBUG] completeOrder: Cleared tempOrder and cartMap from session.");
        
        // ��ٱ��� ���� ���� (CartService�� ���� ��ٱ��� ����)
        cartService.clearCartForUser(memberVO.getUser_id());
        System.out.println("[DEBUG] completeOrder: Cleared cart for user: " + memberVO.getUser_id());
        
        // �ֹ� �Ϸ� �� ��� �޽����� �Բ� ������������ �̵�
        System.out.println("[DEBUG] completeOrder: Redirecting to 'myPageMain' with success message.");
        
        // URL�� �ѱ� �޽����� �����Ͽ� �����̷�Ʈ URL ����
        String message = "�ֹ��� ���������� �Ϸ�Ǿ����ϴ�.";
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
