package com.shop.ShoppingMall_TeamPrj.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderController {
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO,HttpServletRequest request, HttpServletResponse response)  throws Exception;
//	public ModelAndView orderAllCartGoods(@RequestParam  String[] cart_goods_qty,HttpServletRequest request, HttpServletResponse response)  throws Exception;
//	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> orderMap,HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
