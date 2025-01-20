package com.shop.ShoppingMall_TeamPrj.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.ShoppingMall_TeamPrj.admin.dao.AdminDAO;
import com.shop.ShoppingMall_TeamPrj.admin.vo.AdminVO;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public Object getDashboardData() {
        return adminDAO.getDashboardData();
    }

    @Override
    public Object getUserData(String userId) {
        return adminDAO.getUserData(userId);
    }

    @Override
    public void updateSettings(AdminVO adminVO) {
        adminDAO.updateSettings(adminVO);
    }
}
