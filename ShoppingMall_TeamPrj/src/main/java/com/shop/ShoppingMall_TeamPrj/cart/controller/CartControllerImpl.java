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

@Controller("cartController")
public class CartControllerImpl implements CartController {

    @Autowired 
    private CartService cartService;
      
    @Autowired 
    private MemberVO memberVO;
    
    @Autowired 
    private CartVO cartVO;
    
    @Override
    @RequestMapping(value = "/cart/myCartList.do", method = {RequestMethod.GET})
    public ModelAndView myCartList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session == null) {
            System.out.println("[DEBUG] 세션이 존재하지 않습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        // 세션에 저장된 memberInfo 확인
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] memberInfo가 세션에 존재하지 않습니다.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        cartVO.setUser_id(memberVO.getUser_id());

        // 장바구니 정보 조회
        Map<String, List> cartMap = cartService.myCartList(cartVO);
        if (cartMap == null || cartMap.isEmpty()) {
            ModelAndView mav = new ModelAndView("cart/myCartList");
            mav.addObject("message", "장바구니가 비어 있습니다.");
            return mav;
        }

        // 총합 계산
        double totalPrice = 0;
        List<CartVO> myCartList = cartMap.get("myCartList");
        List<GoodsVO> myGoodsList = cartMap.get("myGoodsList");

        for (int i = 0; i < myCartList.size(); i++) {
            CartVO cartItem = myCartList.get(i);
            GoodsVO goodsItem = myGoodsList.get(i);
            totalPrice += cartItem.getQuantity() * goodsItem.getPrice(); // 상품 가격 * 수량
        }

        // totalPrice를 세션에 별도로 저장
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cartMap", cartMap); // 세션에 장바구니 데이터 저장
        
        System.out.println("[DEBUG] 장바구니 조회 완료. 총합계: " + totalPrice);
        return new ModelAndView("cart/myCartList");
    }
    
    @RequestMapping(value = "/cart/addMyCart.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
    public @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
                                          @RequestParam("quantity") int quantity,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");

        if (memberVO == null) {
            System.out.println("[DEBUG] addMyCart: 로그인 정보 없음");
            return "login_required"; // 로그인 필요
        }

        int member_id = memberVO.getUser_id();
        cartVO.setUser_id(member_id);
        cartVO.setProduct_id(product_id);

        CartVO existingCartItem = cartService.findCartItem(cartVO);
        if (existingCartItem != null) {
            int updatedQuantity = existingCartItem.getQuantity() + quantity;
            cartVO.setQuantity(updatedQuantity);
            cartService.updateCartQuantity(cartVO);
            System.out.println("[DEBUG] addMyCart: 기존 상품 수량 업데이트, 새 수량: " + updatedQuantity);
            return "quantity_updated";
        } else {
            cartVO.setQuantity(quantity);
            cartService.addGoodsInCart(cartVO);
            System.out.println("[DEBUG] addMyCart: 새로운 상품 추가, 수량: " + quantity);
            return "add_success";
        }
    }

    @Override
    @RequestMapping(value = "/cart/updateCartQuantity.do", method = RequestMethod.POST)
    public ModelAndView updateCartQuantity(@RequestParam("cart_id") int cart_id, 
                                           @RequestParam("quantity") int quantity, 
                                           @RequestParam("product_id") int product_id, // product_id 추가
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");

        if (memberVO == null) {
            System.out.println("[DEBUG] updateCartQuantity: 로그인 정보 없음");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        // cartVO 설정
        cartVO.setCart_id(cart_id);
        cartVO.setQuantity(quantity);
        cartVO.setUser_id(memberVO.getUser_id());
        cartVO.setProduct_id(product_id); // product_id 설정

        System.out.println("[DEBUG] updateCartQuantity: Cart ID: " + cart_id + ", Quantity: " + quantity + ", Product ID: " + product_id);

        // 수량 업데이트
        cartService.updateCartQuantity(cartVO);

        // DB에서 갱신된 장바구니 데이터를 다시 조회하여 세션에 업데이트
        Map<String, List> cartMap = cartService.myCartList(cartVO);
        session.setAttribute("cartMap", cartMap);

        return new ModelAndView("redirect:/cart/myCartList.do");
    }

    @Override
    @RequestMapping(value = "/cart/deleteCartItem.do", method = RequestMethod.POST)
    public ModelAndView deleteCartItem(@RequestParam("cart_id") int cart_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");

        cartVO.setCart_id(cart_id);
        cartVO.setUser_id(memberVO.getUser_id());

        System.out.println("[DEBUG] deleteCartItem: Cart ID: " + cart_id);
        cartService.deleteCartItem(cart_id);

        return new ModelAndView("redirect:/myCartList.do");
    }

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

        return new ModelAndView("order/orderPage", "cartMap", cartMap);
    }
    
    /**
     * 주문 완료 후(POST) 주문 확인 페이지로 이동하기 전에,
     * 카카오톡 '나에게 보내기' API를 통해 주문 완료 메시지를 전송합니다.
     */
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
        
        if (cartMap != null) {
            mav.addObject("myCartList", cartMap.get("myCartList"));
            mav.addObject("myGoodsList", cartMap.get("myGoodsList"));
            mav.addObject("totalPrice", totalPrice);
        }
        
        // 주문번호 생성(예시: 현재 시간 기반의 임시 주문번호)
        String orderNumber = "ORDER" + System.currentTimeMillis();
        System.out.println("[DEBUG] 주문번호 생성: " + orderNumber);
        
        // 로그인한 회원 정보 및 액세스 토큰
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO != null) {
            String accessToken = memberVO.getAccessToken(); // 카카오 로그인 후 발급받은 액세스 토큰
            System.out.println("[DEBUG] 액세스 토큰: " + accessToken);
            
            // 카카오톡 메시지 템플릿 JSON 구성 (Unsplash의 랜덤 이미지 사용)
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
            
            System.out.println("[DEBUG] 전송할 템플릿 JSON: " + templateObjectJson);
            
            try {
                sendKakaoMemoMessage(accessToken, templateObjectJson);
            } catch (Exception e) {
                System.out.println("[DEBUG] 카카오톡 메시지 전송 중 오류 발생:");
                e.printStackTrace();
            }
        } else {
            System.out.println("[DEBUG] checkout: memberVO가 null 입니다.");
        }
        
        mav.setViewName("order/myOrder");
        return mav;
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
        
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // 요청 파라미터 구성 : template_object 파라미터에 JSON 문자열 전달
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("template_object", templateObjectJson);
        
        System.out.println("[DEBUG] API 호출 URL: " + kakaoApiUrl);
        System.out.println("[DEBUG] 요청 헤더: " + headers);
        System.out.println("[DEBUG] 요청 파라미터: " + params);
        
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                kakaoApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);
        
        System.out.println("[DEBUG] Kakao API Response Status: " + responseEntity.getStatusCode());
        System.out.println("[DEBUG] Kakao API Response Body: " + responseEntity.getBody());
    }

}
