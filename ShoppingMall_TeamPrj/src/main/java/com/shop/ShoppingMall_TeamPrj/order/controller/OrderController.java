package com.shop.ShoppingMall_TeamPrj.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderController {
    // ��ǰ ���� �ֹ� ó��
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // ���� ��û ó�� (�佺 ���� ����)
    public ModelAndView initiatePayment(Map<String, String> paymentDetails, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // ���� ��� Ȯ�� ó��
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
