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
        HttpSession session = request.getSession(false); // ���� ���� ��������
        if (session == null) {
            System.out.println("[DEBUG] ������ �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        // ���ǿ� ����� memberInfo Ȯ��
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] memberInfo�� ���ǿ� �������� �ʽ��ϴ�.");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        cartVO.setUser_id(memberVO.getUser_id());

        // ��ٱ��� ���� ��ȸ
        Map<String, List> cartMap = cartService.myCartList(cartVO);
        if (cartMap == null || cartMap.isEmpty()) {
            ModelAndView mav = new ModelAndView("cart/myCartList");
            mav.addObject("message", "��ٱ��ϰ� ��� �ֽ��ϴ�.");
            return mav;
        }

        // ���� ���
        double totalPrice = 0;
        List<CartVO> myCartList = cartMap.get("myCartList");
        List<GoodsVO> myGoodsList = cartMap.get("myGoodsList");

        for (int i = 0; i < myCartList.size(); i++) {
            CartVO cartItem = myCartList.get(i);
            GoodsVO goodsItem = myGoodsList.get(i);
            totalPrice += cartItem.getQuantity() * goodsItem.getPrice(); // ��ǰ ���� * ����
        }

        // totalPrice�� ���ǿ� ������ ����
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cartMap", cartMap); // ���ǿ� ��ٱ��� ������ ����
        
        System.out.println("[DEBUG] ��ٱ��� ��ȸ �Ϸ�. ���հ�: " + totalPrice);
        return new ModelAndView("cart/myCartList");
    }
    
    @RequestMapping(value = "/cart/addMyCart.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
    public @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
                                          @RequestParam("quantity") int quantity,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");

        if (memberVO == null) {
            System.out.println("[DEBUG] addMyCart: �α��� ���� ����");
            return "login_required"; // �α��� �ʿ�
        }

        int member_id = memberVO.getUser_id();
        cartVO.setUser_id(member_id);
        cartVO.setProduct_id(product_id);

        CartVO existingCartItem = cartService.findCartItem(cartVO);
        if (existingCartItem != null) {
            int updatedQuantity = existingCartItem.getQuantity() + quantity;
            cartVO.setQuantity(updatedQuantity);
            cartService.updateCartQuantity(cartVO);
            System.out.println("[DEBUG] addMyCart: ���� ��ǰ ���� ������Ʈ, �� ����: " + updatedQuantity);
            return "quantity_updated";
        } else {
            cartVO.setQuantity(quantity);
            cartService.addGoodsInCart(cartVO);
            System.out.println("[DEBUG] addMyCart: ���ο� ��ǰ �߰�, ����: " + quantity);
            return "add_success";
        }
    }

    @Override
    @RequestMapping(value = "/cart/updateCartQuantity.do", method = RequestMethod.POST)
    public ModelAndView updateCartQuantity(@RequestParam("cart_id") int cart_id, 
                                           @RequestParam("quantity") int quantity, 
                                           @RequestParam("product_id") int product_id, // product_id �߰�
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");

        if (memberVO == null) {
            System.out.println("[DEBUG] updateCartQuantity: �α��� ���� ����");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        // cartVO ����
        cartVO.setCart_id(cart_id);
        cartVO.setQuantity(quantity);
        cartVO.setUser_id(memberVO.getUser_id());
        cartVO.setProduct_id(product_id); // product_id ����

        System.out.println("[DEBUG] updateCartQuantity: Cart ID: " + cart_id + ", Quantity: " + quantity + ", Product ID: " + product_id);

        // ���� ������Ʈ
        cartService.updateCartQuantity(cartVO);

        // DB���� ���ŵ� ��ٱ��� �����͸� �ٽ� ��ȸ�Ͽ� ���ǿ� ������Ʈ
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
            System.out.println("[DEBUG] toOrderPage: ���� ����");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        @SuppressWarnings("unchecked")
        Map<String, List> cartMap = (Map<String, List>) session.getAttribute("cartMap");
        if (cartMap == null || cartMap.isEmpty()) {
            System.out.println("[DEBUG] toOrderPage: ��ٱ��� ������ ����");
            return new ModelAndView("redirect:/cart/myCartList.do");
        }

        return new ModelAndView("order/orderPage", "cartMap", cartMap);
    }
    
    /**
     * �ֹ� �Ϸ� ��(POST) �ֹ� Ȯ�� �������� �̵��ϱ� ����,
     * īī���� '������ ������' API�� ���� �ֹ� �Ϸ� �޽����� �����մϴ�.
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
        
        // �ֹ���ȣ ����(����: ���� �ð� ����� �ӽ� �ֹ���ȣ)
        String orderNumber = "ORDER" + System.currentTimeMillis();
        System.out.println("[DEBUG] �ֹ���ȣ ����: " + orderNumber);
        
        // �α����� ȸ�� ���� �� �׼��� ��ū
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO != null) {
            String accessToken = memberVO.getAccessToken(); // īī�� �α��� �� �߱޹��� �׼��� ��ū
            System.out.println("[DEBUG] �׼��� ��ū: " + accessToken);
            
            // īī���� �޽��� ���ø� JSON ���� (Unsplash�� ���� �̹��� ���)
            String templateObjectJson = "{"
                    + "\"object_type\": \"feed\","
                    + "\"content\": {"
                    + "    \"title\": \"�ֹ��� �Ϸ�Ǿ����ϴ�!\","
                    + "    \"description\": \"�ֹ���ȣ: " + orderNumber + "\\n�� �����ݾ�: " + totalPrice + "��\","
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
            
            System.out.println("[DEBUG] ������ ���ø� JSON: " + templateObjectJson);
            
            try {
                sendKakaoMemoMessage(accessToken, templateObjectJson);
            } catch (Exception e) {
                System.out.println("[DEBUG] īī���� �޽��� ���� �� ���� �߻�:");
                e.printStackTrace();
            }
        } else {
            System.out.println("[DEBUG] checkout: memberVO�� null �Դϴ�.");
        }
        
        mav.setViewName("order/myOrder");
        return mav;
    }

    /**
     * īī���� '������ ������' API�� ȣ���Ͽ� �޽����� �����ϴ� �޼���.
     * 
     * @param accessToken  īī�� �α��� �� �߱޹��� �׼��� ��ū
     * @param templateObjectJson  ������ �޽��� ���ø� JSON ���ڿ�
     * @throws Exception
     */
    private void sendKakaoMemoMessage(String accessToken, String templateObjectJson) throws Exception {
        String kakaoApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        
        // HTTP ��� ����
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // ��û �Ķ���� ���� : template_object �Ķ���Ϳ� JSON ���ڿ� ����
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("template_object", templateObjectJson);
        
        System.out.println("[DEBUG] API ȣ�� URL: " + kakaoApiUrl);
        System.out.println("[DEBUG] ��û ���: " + headers);
        System.out.println("[DEBUG] ��û �Ķ����: " + params);
        
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
