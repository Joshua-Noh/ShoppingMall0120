package com.shop.ShoppingMall_TeamPrj.order.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderController {
    // 기존에 선언된 메서드들
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView initiatePayment(Map<String, String> paymentDetails, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    // 추가할 메서드 선언 (Phase 1, Phase 2, Success 처리)
    public ModelAndView myOrder(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView completeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView success(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
