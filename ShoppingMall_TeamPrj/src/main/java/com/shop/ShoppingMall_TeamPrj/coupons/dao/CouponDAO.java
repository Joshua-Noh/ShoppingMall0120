package com.shop.ShoppingMall_TeamPrj.coupons.dao;

import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
import java.util.List;

/**
 * CouponDAO �������̽��� ���� ���� ������ ���� �۾��� �����մϴ�.
 */
public interface CouponDAO {
    CouponVO getCouponById(int couponId);
    List<CouponVO> getAllCoupons();
    int insertCoupon(CouponVO coupon);
    int updateCoupon(CouponVO coupon);
    int deleteCoupon(int couponId);
    
    List<UserCouponVO> getUserCouponsByUserId(int userId);
    int insertUserCoupon(UserCouponVO userCoupon);
}
