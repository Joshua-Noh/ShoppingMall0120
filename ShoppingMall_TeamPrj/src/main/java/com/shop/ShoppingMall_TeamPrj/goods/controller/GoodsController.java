package com.shop.ShoppingMall_TeamPrj.goods.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


public interface GoodsController {
	
	public ModelAndView goodsList(@RequestParam("category_id") int category_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView detailInfo(@RequestParam("product_id") int product_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,HttpServletRequest request, HttpServletResponse response) throws Exception;
	/*
	 * public ResponseEntity addNewArticle(MultipartHttpServletRequest
	 * multipartRequest,HttpServletResponse response) throws Exception;
	 * 
	 * public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception;
	 * //public ResponseEntity modArticle(MultipartHttpServletRequest
	 * multipartRequest, HttpServletResponse response) throws Exception; public
	 * ResponseEntity removeArticle(@RequestParam("articleNO") int articleNO,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception;
	 */
}
