package com.shop.ShoppingMall_TeamPrj.main.search.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface SearchController {
    public ModelAndView searchItems(@RequestParam("query") String query,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ModelAndView advancedSearch(@RequestParam("criteria") String criteria,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception;
}
