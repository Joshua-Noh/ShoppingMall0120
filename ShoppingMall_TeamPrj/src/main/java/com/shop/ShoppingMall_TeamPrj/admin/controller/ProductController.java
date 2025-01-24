package com.shop.ShoppingMall_TeamPrj.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface ProductController {
    public ModelAndView listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView addProduct(@ModelAttribute("info") ProductVO productVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView removeProduct(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO productVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
