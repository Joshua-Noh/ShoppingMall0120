package com.shop.ShoppingMall_TeamPrj.admin.service;

import com.shop.ShoppingMall_TeamPrj.admin.vo.AdminVO;

public interface AdminService {
    Object getDashboardData();
    Object getUserData(String userId);
    void updateSettings(AdminVO adminVO);
}
