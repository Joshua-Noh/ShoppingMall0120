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
	@RequestMapping(value= "/cart/myCartList.do", method = {RequestMethod.GET})
	public ModelAndView myCartList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession(false); // ���� ���� ��������
	    if (session == null) {
	        System.out.println("������ �����ϴ�. �α��� �������� �̵��մϴ�.");
	        return new ModelAndView("redirect:/member/loginForm.do");
	    }

	    // ���ǿ� ����� memberInfo Ȯ��
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
	    if (memberVO == null) {
	        System.out.println("���ǿ� memberInfo�� �����ϴ�. �α��� �������� �̵��մϴ�.");
	        return new ModelAndView("redirect:/member/loginForm.do");
	    } else {
	        // Debugging: memberInfo Ȯ��
	        System.out.println("���ǿ��� ������ ����� ����: ID=" + memberVO.getUser_id());
	    }

	    // cartVO Ȯ��
	    if (cartVO == null) {
	        System.out.println("cartVO�� null�Դϴ�. �⺻ �����ڷ� �ʱ�ȭ�մϴ�.");
	        cartVO = new CartVO();
	    }
	    cartVO.setUser_id(memberVO.getUser_id());

	    // cartService Ȯ��
	    if (cartService == null) {
	        throw new NullPointerException("cartService�� ���Ե��� �ʾҽ��ϴ�.");
	    }

	    // ��ٱ��� ���� ��ȸ
	    Map<String, List> cartMap = cartService.myCartList(cartVO);
	    if (cartMap == null || cartMap.isEmpty()) {
	        System.out.println("��ٱ��ϰ� ��� �ֽ��ϴ�.");
	        ModelAndView mav = new ModelAndView("cart/myCartList");
	        mav.addObject("message", "��ٱ��ϰ� ��� �ֽ��ϴ�.");
	        return mav;
	    }

	 // Debugging: cartMap Ȯ��
	    System.out.println("��ٱ��� ������:");
	    for (Iterator<Map.Entry<String, List>> it = cartMap.entrySet().iterator(); it.hasNext(); ) {
	        Map.Entry<String, List> entry = it.next();
	        System.out.println(entry.getKey() + ": " + entry.getValue());
	    }

	    session.setAttribute("cartMap", cartMap); // ���ǿ� ��ٱ��� ������ ����
	    return new ModelAndView("cart/myCartList");

	}


	
	@RequestMapping(value="/cart/addMyCart.do" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		int member_id=memberVO.getUser_id();
		
		cartVO.setUser_id(member_id);
		//īƮ ������� �̹� ��ϵ� ��ǰ���� �Ǻ��Ѵ�.
		cartVO.setProduct_id(product_id);
		cartVO.setUser_id(member_id);
		boolean isAreadyExisted=cartService.findCartGoods(cartVO);
		System.out.println("isAreadyExisted:"+isAreadyExisted);
		if(isAreadyExisted==true){
			return "already_existed";
		}else{
			cartService.addGoodsInCart(cartVO);
			return "add_success";
		}
	}

	@Override
	@RequestMapping(value = "/cart/updateCartQuantity.do", method = RequestMethod.POST)
	public ModelAndView updateCartQuantity(@RequestParam("cart_id") int cart_id, @RequestParam("quantity") int quantity, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    memberVO = (MemberVO) session.getAttribute("memberInfo");

	    // cartVO ����
	    cartVO.setCart_id(cart_id);
	    cartVO.setQuantity(quantity);
	    cartVO.setUser_id(memberVO.getUser_id());

	    // ���� ������Ʈ (���� ������Ʈ�� �α׷� ���)
	    System.out.println("Updating cart quantity for Cart ID: " + cart_id + " with quantity: " + quantity);

	    // ���� ������Ʈ
	    cartService.updateCartQuantity(cartVO);

	    // DB���� ���ŵ� ��ٱ��� �����͸� �ٽ� ��ȸ�Ͽ� ���ǿ� ������Ʈ
	    Map<String, List> cartMap = cartService.myCartList(cartVO);  // ��ٱ��� �ٽ� ��ȸ
	    session.setAttribute("cartMap", cartMap);  // ���� ����

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


}
