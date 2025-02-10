package com.shop.ShoppingMall_TeamPrj.coupons.service;

import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
import java.util.List;

/**
 * CouponService �������̽��� ���� ���� ����Ͻ� ������ �����մϴ�.
 */
public interface CouponService {
    CouponVO getCouponById(int couponId);
    List<CouponVO> getAllCoupons();
    int registerCoupon(CouponVO coupon);
    int updateCoupon(CouponVO coupon);
    int removeCoupon(int couponId);
    
    List<UserCouponVO> getUserCouponsByUserId(int userId);
    int registerUserCoupon(UserCouponVO userCoupon);
}
