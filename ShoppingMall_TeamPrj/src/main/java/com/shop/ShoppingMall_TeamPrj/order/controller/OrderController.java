package com.shop.ShoppingMall_TeamPrj.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderController {
    // 상품 개별 주문 처리
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 결제 요청 처리 (토스 결제 연동)
    public ModelAndView initiatePayment(Map<String, String> paymentDetails, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 결제 결과 확인 처리
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
