package com.shop.ShoppingMall_TeamPrj.cart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public interface CartController {

    // 장바구니 목록 조회
    public ModelAndView myCartList(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // 장바구니에 상품 추가
    public @ResponseBody String addMyCart(@RequestParam("product_id") int product_id,
            @RequestParam("quantity") int quantity,
            HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 장바구니 상품 수량 수정
    public ModelAndView updateCartQuantity(int cart_id, int quantity, int product_id,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // 장바구니 상품 삭제
    public ModelAndView deleteCartItem(@RequestParam("cart_id") int cart_id,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // 장바구니 내역 주문 페이지로 전달
    public ModelAndView checkout(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 주문 완료 후, 해당 사용자의 장바구니 항목 전체 삭제
    public ModelAndView clearCartAfterOrder(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
