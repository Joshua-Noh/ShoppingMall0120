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
    
    // ��ٱ��� ��� ��ȸ
    @Override
    @RequestMapping(value = "/cart/myCartList.do", method = {RequestMethod.GET})
    public ModelAndView myCartList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] myCartList: ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] myCartList: memberInfo�� �������� �ʾ� �α��� �������� �̵��մϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        System.out.println("[DEBUG] myCartList: memberVO = " + memberVO);

        cartVO.setUser_id(memberVO.getUser_id());
        System.out.println("[DEBUG] myCartList: cartVO.user_id ������ �� = " + cartVO.getUser_id());

        Map<String, List> cartMap = cartService.myCartList(cartVO);
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] myCartList: ��ٱ��ϰ� ����ֽ��ϴ�.");
            ModelAndView mav = new ModelAndView("cart/myCartList");
            mav.addObject("message", "��ٱ��ϰ� ����ֽ��ϴ�.");
            return mav;
        }

        // cartMap�� ��� ���� ���
        System.out.println("[DEBUG] myCartList: cartMap ���� = " + cartMap);
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
        System.out.println("[DEBUG] myCartList: �� ���� = " + totalPrice);

        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cartMap", cartMap);
        
        return new ModelAndView("cart/myCartList");
    }
    
    // ��ٱ��Ͽ� ��ǰ �߰�
    @RequestMapping(value = "/cart/addMyCart.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
    public @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
                                          @RequestParam("quantity") int quantity,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] addMyCart: �α������� ���� �����Դϴ�.");
            return "�α����� �ʿ��մϴ�.";
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
            System.out.println("[DEBUG] addMyCart: ��ٱ��Ͽ� �̹� ��ϵ� ��ǰ, ���� ����: " + updatedQuantity);
            return "��ٱ��Ͽ� �߰��� ��ǰ�� ������ ������Ʈ�Ͽ����ϴ�.";
        } else {
            cartVO.setQuantity(quantity);
            cartService.addGoodsInCart(cartVO);
            System.out.println("[DEBUG] addMyCart: ��ٱ��Ͽ� �ű� �߰�, ����: " + quantity);
            return "��ٱ��Ͽ� �߰��Ͽ����ϴ�.";
        }
    }

    // ��ٱ��� ��ǰ ���� ����
    @Override
    @RequestMapping(value = "/cart/updateCartQuantity.do", method = RequestMethod.POST)
    public ModelAndView updateCartQuantity(@RequestParam("cart_id") int cart_id, 
                                           @RequestParam("quantity") int quantity, 
                                           @RequestParam("product_id") int product_id, 
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] updateCartQuantity: �α������� ���� �����Դϴ�.");
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
        System.out.println("[DEBUG] updateCartQuantity: ������Ʈ �� cartMap = " + cartMap);

        return new ModelAndView("redirect:/cart/myCartList.do");
    }

    // ��ٱ��� ��ǰ ����
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

    // �ֹ� �������� �̵� (GET)
    @RequestMapping(value = "/cart/checkout.do", method = RequestMethod.GET)
    public ModelAndView toOrderPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] toOrderPage: ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        @SuppressWarnings("unchecked")
        Map<String, List> cartMap = (Map<String, List>) session.getAttribute("cartMap");
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] toOrderPage: ��ٱ��� ����� �����ϴ�.");
            return new ModelAndView("redirect:/cart/myCartList.do");
        }

        System.out.println("[DEBUG] toOrderPage: cartMap ���� = " + cartMap);
        return new ModelAndView("order/orderPage", "cartMap", cartMap);
    }

    // �ֹ� �Ϸ� ó�� (POST) - �ֹ� Ȯ�� �� �ֹ� �������� �̵� (checkout)
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
        System.out.println("[DEBUG] checkout: ������ �ֹ���ȣ = " + orderNumber);
        
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO != null) {
            String accessToken = memberVO.getAccessToken();
            System.out.println("[DEBUG] checkout: īī���� accessToken = " + accessToken);
            
            String templateObjectJson = "{"
                    + "\"object_type\": \"feed\","
                    + "\"content\": {"
                    + "    \"title\": \"�ֹ��� �Ϸ�Ǿ����ϴ�!\","
                    + "    \"description\": \"�ֹ���ȣ: " + orderNumber + "\\n�� �ֹ��ݾ�: " + totalPrice + "��\","
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
                    + "        \"title\": \"�ֹ� �󼼺���\","
                    + "        \"link\": {"
                    + "            \"web_url\": \"http://yourwebsite.com/order/" + orderNumber + "\","
                    + "            \"mobile_web_url\": \"http://yourwebsite.com/order/" + orderNumber + "\""
                    + "        }"
                    + "    },"
                    + "    {"
                    + "        \"title\": \"���� ����ϱ�\","
                    + "        \"link\": {"
                    + "            \"web_url\": \"http://yourwebsite.com/\","
                    + "            \"mobile_web_url\": \"http://yourwebsite.com/\""
                    + "        }"
                    + "    }"
                    + "]"
                    + "}";
            System.out.println("[DEBUG] checkout: īī���� ���ø� JSON = " + templateObjectJson);
            
            try {
                sendKakaoMemoMessage(accessToken, templateObjectJson);
            } catch (Exception e) {
                System.out.println("[DEBUG] checkout: īī���� �޽��� ���� ����:");
                e.printStackTrace();
            }
        } else {
            System.out.println("[DEBUG] checkout: memberVO�� null �Դϴ�.");
        }
        
        mav.setViewName("order/myOrder");
        return mav;
    }

    // �ֹ� �Ϸ� �� ��ٱ��� �ʱ�ȭ �� ����
    @RequestMapping(value = "/cart/clearCartAfterOrder.do", method = RequestMethod.POST)
    public ModelAndView clearCartAfterOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] clearCartAfterOrder: �α������� ���� �����Դϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        int user_id = memberVO.getUser_id();
        cartService.clearCartForUser(user_id);
        session.removeAttribute("cartMap");
        System.out.println("[DEBUG] clearCartAfterOrder: �ֹ� �Ϸ� �� (" + user_id + ")�� ��ٱ��� �׸��� ��� �����Ͽ����ϴ�.");
        return new ModelAndView("redirect:/cart/myCartList.do");
    }

    /**
     * �� �޼ҵ�� īī���� '�⺻ ���ø� �޽��� ������' API�� ȣ���ϱ� ���� �޼ҵ��Դϴ�.
     * 
     * @param accessToken  īī���� �α��� �� �߱޹��� �׼��� ��ū
     * @param templateObjectJson  īī���� �⺻ ���ø� �޽��� ������ ���� JSON ���ڿ�
     * @throws Exception
     */
    private void sendKakaoMemoMessage(String accessToken, String templateObjectJson) throws Exception {
        String kakaoApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("template_object", templateObjectJson);
        
        System.out.println("[DEBUG] sendKakaoMemoMessage: API ��û URL = " + kakaoApiUrl);
        System.out.println("[DEBUG] sendKakaoMemoMessage: ��û ��� = " + headers);
        System.out.println("[DEBUG] sendKakaoMemoMessage: ��û �Ķ���� = " + params);
        
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
