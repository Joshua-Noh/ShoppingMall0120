package com.shop.ShoppingMall_TeamPrj.cart.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.cart.service.CartService;
import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;
//import com.shop.ShoppingMall_TeamPrj.goods.vo.ImageVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;


@Controller("cartController")
public class CartControllerImpl  implements CartController{

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
	        return new ModelAndView("redirect:/member/loginForm.do");
	    }

	    // ���ǿ� ����� memberInfo Ȯ��
	    memberVO = (MemberVO) session.getAttribute("memberInfo");
	    if (memberVO == null) {
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
	    return new ModelAndView("cart/myCartList");
	}





	
	@RequestMapping(value = "/cart/addMyCart.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
	                                      @RequestParam("quantity") int quantity,
	                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    memberVO = (MemberVO) session.getAttribute("memberInfo");

	    if (memberVO == null) {
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
	        return "quantity_updated";
	    } else {
	        cartVO.setQuantity(quantity);
	        cartService.addGoodsInCart(cartVO);
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
	        // ���ǿ� ����� ������ ���� ���
	        return new ModelAndView("redirect:/member/loginForm.do");
	    }

	    // cartVO ����
	    cartVO.setCart_id(cart_id);
	    cartVO.setQuantity(quantity);
	    cartVO.setUser_id(memberVO.getUser_id());
	    cartVO.setProduct_id(product_id); // product_id ����

	    // �α׷� Ȯ��
	    System.out.println("Cart ID: " + cart_id + ", Quantity: " + quantity + ", Product ID: " + product_id);

	    // ���� ������Ʈ
	    cartService.updateCartQuantity(cartVO);

	    // DB���� ���ŵ� ��ٱ��� �����͸� �ٽ� ��ȸ�Ͽ� ���ǿ� ������Ʈ
	    Map<String, List> cartMap = cartService.myCartList(cartVO);
	    session.setAttribute("cartMap", cartMap);

	    // ��ٱ��� �������� �����̷�Ʈ
	    return new ModelAndView("redirect:/cart/myCartList.do");
	}




	@Override
	@RequestMapping(value = "/cart/deleteCartItem.do", method = RequestMethod.POST)
	public ModelAndView deleteCartItem(@RequestParam("cart_id") int cart_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    memberVO = (MemberVO) session.getAttribute("memberInfo");

	    cartVO.setCart_id(cart_id);
	    cartVO.setUser_id(memberVO.getUser_id());

	    cartService.deleteCartItem(cart_id);

	    return new ModelAndView("redirect:/myCartList.do");
	}

	@RequestMapping(value = "/cart/checkout.do", method = RequestMethod.GET)
	public ModelAndView toOrderPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession(false);
	    
	    if (session == null) {
	        return new ModelAndView("redirect:/member/loginForm.do");
	    }
	    
	    // ���ǿ��� ��ٱ��� ���� ��������
	    Map<String, List> cartMap = (Map<String, List>) session.getAttribute("cartMap");
	    if (cartMap == null || cartMap.isEmpty()) {
	        return new ModelAndView("redirect:/cart/myCartList.do");
	    }

	    // �ֹ� Ȯ�� �������� �̵��ϸ鼭 ��ٱ��� ���� ����
	    return new ModelAndView("order/orderPage", "cartMap", cartMap);
	}

}
