package com.shop.ShoppingMall_TeamPrj.cart.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.shop.ShoppingMall_TeamPrj.cart.service.CartService;
import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

@Controller("cartController")
public class CartControllerImpl implements CartController {

    @Autowired 
    private CartService cartService;
      
    @Autowired 
    private MemberVO memberVO;
    
    @Autowired 
    private CartVO cartVO;
    
    // 장바구니 목록 조회
    @Override
    @RequestMapping(value = "/cart/myCartList.do", method = {RequestMethod.GET})
    public ModelAndView myCartList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] myCartList: 세션이 존재하지 않습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] myCartList: memberInfo가 세션에 존재하지 않습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        System.out.println("[DEBUG] myCartList: memberVO = " + memberVO);

        cartVO.setUser_id(memberVO.getUser_id());
        System.out.println("[DEBUG] myCartList: cartVO.user_id 설정됨 = " + cartVO.getUser_id());

        Map<String, List> cartMap = cartService.myCartList(cartVO);
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] myCartList: 장바구니가 비어 있습니다.");
            ModelAndView mav = new ModelAndView("cart/myCartList");
            mav.addObject("message", "장바구니가 비어 있습니다.");
            return mav;
        }

        // cartMap 내부 데이터 상세 출력
        System.out.println("[DEBUG] myCartList: cartMap 데이터 = " + cartMap);
        List<CartVO> myCartList = cartMap.get("myCartList");
        List<GoodsVO> myGoodsList = cartMap.get("myGoodsList");

        for (int i = 0; i < myCartList.size(); i++) {
            CartVO cartItem = myCartList.get(i);
            GoodsVO goodsItem = myGoodsList.get(i);
            System.out.println("[DEBUG] myCartList: Cart item " + i + " - product_id: " + cartItem.getProduct_id() +
                               ", quantity: " + cartItem.getQuantity() +
                               ", Goods product_id: " + goodsItem.getProduct_id() +
                               ", Goods price: " + goodsItem.getPrice());
        }

        double totalPrice = 0;
        for (int i = 0; i < myCartList.size(); i++) {
            CartVO cartItem = myCartList.get(i);
            GoodsVO goodsItem = myGoodsList.get(i);
            totalPrice += cartItem.getQuantity() * goodsItem.getPrice();
        }
        System.out.println("[DEBUG] myCartList: 총합계 = " + totalPrice);

        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cartMap", cartMap);
        
        return new ModelAndView("cart/myCartList");
    }
    
    // 장바구니에 상품 추가
    @RequestMapping(value = "/cart/addMyCart.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
    public @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
                                          @RequestParam("quantity") int quantity,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] addMyCart: 로그인 정보 없음");
            return "로그인을 해주세요.";
        }

        int member_id = memberVO.getUser_id();
        cartVO.setUser_id(member_id);
        cartVO.setProduct_id(product_id);
        System.out.println("[DEBUG] addMyCart: product_id = " + product_id + ", quantity = " + quantity);

        CartVO existingCartItem = cartService.findCartItem(cartVO);
        if (existingCartItem != null) {
            int updatedQuantity = existingCartItem.getQuantity() + quantity;
            cartVO.setQuantity(updatedQuantity);
            cartService.updateCartQuantity(cartVO);
            System.out.println("[DEBUG] addMyCart: 기존 상품 수량 업데이트, 새 수량: " + updatedQuantity);
            return "수량을 업데이트 했습니다.";
        } else {
            cartVO.setQuantity(quantity);
            cartService.addGoodsInCart(cartVO);
            System.out.println("[DEBUG] addMyCart: 새로운 상품 추가, 수량: " + quantity);
            return "장바구니에 추가했습니다.";
        }
    }

    // 장바구니 상품 수량 수정
    @Override
    @RequestMapping(value = "/cart/updateCartQuantity.do", method = RequestMethod.POST)
    public ModelAndView updateCartQuantity(@RequestParam("cart_id") int cart_id, 
                                           @RequestParam("quantity") int quantity, 
                                           @RequestParam("product_id") int product_id, 
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] updateCartQuantity: 로그인 정보 없음");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        cartVO.setCart_id(cart_id);
        cartVO.setQuantity(quantity);
        cartVO.setUser_id(memberVO.getUser_id());
        cartVO.setProduct_id(product_id);
        System.out.println("[DEBUG] updateCartQuantity: Cart ID: " + cart_id + ", Quantity: " + quantity + ", Product ID: " + product_id);

        cartService.updateCartQuantity(cartVO);
        Map<String, List> cartMap = cartService.myCartList(cartVO);
        session.setAttribute("cartMap", cartMap);
        System.out.println("[DEBUG] updateCartQuantity: 업데이트 후 cartMap = " + cartMap);

        return new ModelAndView("redirect:/cart/myCartList.do");
    }

    // 장바구니 상품 삭제
    @Override
    @RequestMapping(value = "/cart/deleteCartItem.do", method = RequestMethod.POST)
    public ModelAndView deleteCartItem(@RequestParam("cart_id") int cart_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        cartVO.setCart_id(cart_id);
        cartVO.setUser_id(memberVO.getUser_id());
        System.out.println("[DEBUG] deleteCartItem: Cart ID = " + cart_id);
        cartService.deleteCartItem(cart_id);
        return new ModelAndView("redirect:/cart/myCartList.do");
    }

    // 주문 페이지로 이동 (GET)
    @RequestMapping(value = "/cart/checkout.do", method = RequestMethod.GET)
    public ModelAndView toOrderPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] toOrderPage: 세션 없음");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        @SuppressWarnings("unchecked")
        Map<String, List> cartMap = (Map<String, List>) session.getAttribute("cartMap");
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] toOrderPage: 장바구니 데이터 없음");
            return new ModelAndView("redirect:/cart/myCartList.do");
        }

        System.out.println("[!DEBUG!] toOrderPage: cartMap 데이터 = " + cartMap);
        return new ModelAndView("order/orderPage", "cartMap", cartMap);
    }

    // 주문 완료 후 (POST) 카카오톡 메시지 전송 후 주문 확인 페이지로 이동 (checkout)
    @RequestMapping(value = "/cart/checkout.do", method = RequestMethod.POST)
    public ModelAndView checkout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        @SuppressWarnings("unchecked")
        Map<String, Object> cartMap = (Map<String, Object>) session.getAttribute("cartMap");
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        System.out.println("[DEBUG] checkout: totalPrice = " + totalPrice);
        if (cartMap != null) {
            System.out.println("[DEBUG] checkout: cartMap = " + cartMap);
            mav.addObject("myCartList", cartMap.get("myCartList"));
            mav.addObject("myGoodsList", cartMap.get("myGoodsList"));
            mav.addObject("totalPrice", totalPrice);
        }
        
        String orderNumber = "ORDER" + System.currentTimeMillis();
        System.out.println("[DEBUG] checkout: 생성된 주문번호 = " + orderNumber);
        
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO != null) {
            String accessToken = memberVO.getAccessToken();
            System.out.println("[DEBUG] checkout: 액세스 토큰 = " + accessToken);
            
            String templateObjectJson = "{"
                    + "\"object_type\": \"feed\","
                    + "\"content\": {"
                    + "    \"title\": \"주문이 완료되었습니다!\","
                    + "    \"description\": \"주문번호: " + orderNumber + "\\n총 결제금액: " + totalPrice + "원\","
                    + "    \"image_url\": \"https://source.unsplash.com/random/640x640\","
                    + "    \"image_width\": 640,"
                    + "    \"image_height\": 640,"
                    + "    \"link\": {"
                    + "        \"web_url\": \"http://yourwebsite.com/order/" + orderNumber + "\","
                    + "        \"mobile_web_url\": \"http://yourwebsite.com/order/" + orderNumber + "\""
                    + "    }"
                    + "},"
                    + "\"buttons\": ["
                    + "    {"
                    + "        \"title\": \"주문 상세보기\","
                    + "        \"link\": {"
                    + "            \"web_url\": \"http://yourwebsite.com/order/" + orderNumber + "\","
                    + "            \"mobile_web_url\": \"http://yourwebsite.com/order/" + orderNumber + "\""
                    + "        }"
                    + "    },"
                    + "    {"
                    + "        \"title\": \"쇼핑 계속하기\","
                    + "        \"link\": {"
                    + "            \"web_url\": \"http://yourwebsite.com/\","
                    + "            \"mobile_web_url\": \"http://yourwebsite.com/\""
                    + "        }"
                    + "    }"
                    + "]"
                    + "}";
            System.out.println("[DEBUG] checkout: 전송할 템플릿 JSON = " + templateObjectJson);
            
            try {
                sendKakaoMemoMessage(accessToken, templateObjectJson);
            } catch (Exception e) {
                System.out.println("[DEBUG] checkout: 카카오톡 메시지 전송 중 오류 발생:");
                e.printStackTrace();
            }
        } else {
            System.out.println("[DEBUG] checkout: memberVO가 null 입니다.");
        }
        
        mav.setViewName("order/myOrder");
        return mav;
    }

    // 주문 완료 후, 해당 사용자의 장바구니 항목 전체 삭제
    @RequestMapping(value = "/cart/clearCartAfterOrder.do", method = RequestMethod.POST)
    public ModelAndView clearCartAfterOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] clearCartAfterOrder: 로그인 정보 없음");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        int user_id = memberVO.getUser_id();
        cartService.clearCartForUser(user_id);
        session.removeAttribute("cartMap");
        System.out.println("[DEBUG] clearCartAfterOrder: 사용자(" + user_id + ")의 장바구니가 삭제되었습니다.");
        return new ModelAndView("redirect:/cart/myCartList.do");
    }

    /**
     * 카카오톡 '나에게 보내기' API를 호출하여 메시지를 전송하는 메서드.
     * 
     * @param accessToken  카카오 로그인 후 발급받은 액세스 토큰
     * @param templateObjectJson  전송할 메시지 템플릿 JSON 문자열
     * @throws Exception
     */
    private void sendKakaoMemoMessage(String accessToken, String templateObjectJson) throws Exception {
        String kakaoApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("template_object", templateObjectJson);
        
        System.out.println("[DEBUG] sendKakaoMemoMessage: API 호출 URL = " + kakaoApiUrl);
        System.out.println("[DEBUG] sendKakaoMemoMessage: 요청 헤더 = " + headers);
        System.out.println("[DEBUG] sendKakaoMemoMessage: 요청 파라미터 = " + params);
        
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                kakaoApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);
        
        System.out.println("[DEBUG] sendKakaoMemoMessage: Kakao API Response Status = " + responseEntity.getStatusCode());
        System.out.println("[DEBUG] sendKakaoMemoMessage: Kakao API Response Body = " + responseEntity.getBody());
    }
}
