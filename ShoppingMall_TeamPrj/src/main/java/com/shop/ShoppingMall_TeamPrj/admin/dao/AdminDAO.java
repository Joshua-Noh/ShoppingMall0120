package com.shop.ShoppingMall_TeamPrj.admin.dao;

import com.shop.ShoppingMall_TeamPrj.admin.vo.AdminVO;

public interface AdminDAO {
    Object getDashboardData();
    Object getUserData(String userId);
    void updateSettings(AdminVO adminVO);
}
