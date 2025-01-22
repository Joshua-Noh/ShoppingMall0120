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
	    HttpSession session = request.getSession(false); // 기존 세션 가져오기
	    if (session == null) {
	        System.out.println("세션이 없습니다. 로그인 페이지로 이동합니다.");
	        return new ModelAndView("redirect:/member/loginForm.do");
	    }

	    // 세션에 저장된 memberInfo 확인
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
	    if (memberVO == null) {
	        System.out.println("세션에 memberInfo가 없습니다. 로그인 페이지로 이동합니다.");
	        return new ModelAndView("redirect:/member/loginForm.do");
	    } else {
	        // Debugging: memberInfo 확인
	        System.out.println("세션에서 가져온 사용자 정보: ID=" + memberVO.getUser_id());
	    }

	    // cartVO 확인
	    if (cartVO == null) {
	        System.out.println("cartVO가 null입니다. 기본 생성자로 초기화합니다.");
	        cartVO = new CartVO();
	    }
	    cartVO.setUser_id(memberVO.getUser_id());

	    // cartService 확인
	    if (cartService == null) {
	        throw new NullPointerException("cartService가 주입되지 않았습니다.");
	    }

	    // 장바구니 정보 조회
	    Map<String, List> cartMap = cartService.myCartList(cartVO);
	    if (cartMap == null || cartMap.isEmpty()) {
	        System.out.println("장바구니가 비어 있습니다.");
	        ModelAndView mav = new ModelAndView("cart/myCartList");
	        mav.addObject("message", "장바구니가 비어 있습니다.");
	        return mav;
	    }

	 // Debugging: cartMap 확인
	    System.out.println("장바구니 데이터:");
	    for (Iterator<Map.Entry<String, List>> it = cartMap.entrySet().iterator(); it.hasNext(); ) {
	        Map.Entry<String, List> entry = it.next();
	        System.out.println(entry.getKey() + ": " + entry.getValue());
	    }

	    session.setAttribute("cartMap", cartMap); // 세션에 장바구니 데이터 저장
	    return new ModelAndView("cart/myCartList");

	}


	
	@RequestMapping(value="/cart/addMyCart.do" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		int member_id=memberVO.getUser_id();
		
		cartVO.setUser_id(member_id);
		//카트 등록전에 이미 등록된 제품인지 판별한다.
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

	    // cartVO 설정
	    cartVO.setCart_id(cart_id);
	    cartVO.setQuantity(quantity);
	    cartVO.setUser_id(memberVO.getUser_id());

	    // 수량 업데이트 (수량 업데이트를 로그로 출력)
	    System.out.println("Updating cart quantity for Cart ID: " + cart_id + " with quantity: " + quantity);

	    // 수량 업데이트
	    cartService.updateCartQuantity(cartVO);

	    // DB에서 갱신된 장바구니 데이터를 다시 조회하여 세션에 업데이트
	    Map<String, List> cartMap = cartService.myCartList(cartVO);  // 장바구니 다시 조회
	    session.setAttribute("cartMap", cartMap);  // 세션 갱신

	    // 장바구니 페이지로 리다이렉트
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
