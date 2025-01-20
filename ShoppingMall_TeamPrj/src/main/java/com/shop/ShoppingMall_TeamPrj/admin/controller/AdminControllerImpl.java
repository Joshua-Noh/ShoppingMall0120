package com.shop.ShoppingMall_TeamPrj.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.admin.service.AdminService;
import com.shop.ShoppingMall_TeamPrj.admin.vo.AdminVO;

@Component
public class AdminControllerImpl {

    @Autowired
    private AdminService adminService;

    public ModelAndView viewDashboard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("adminDashboard");
        mav.addObject("dashboardData", adminService.getDashboardData());
        return mav;
    }

    public ModelAndView manageUser(String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("userManagement");
        mav.addObject("userData", adminService.getUserData(userId));
        return mav;
    }

    public ModelAndView updateSettings(AdminVO adminVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        adminService.updateSettings(adminVO);
        return new ModelAndView("redirect:/admin");
    }
}
