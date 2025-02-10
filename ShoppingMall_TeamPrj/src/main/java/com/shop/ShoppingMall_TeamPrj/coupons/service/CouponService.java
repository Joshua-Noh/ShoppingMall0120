package com.shop.ShoppingMall_TeamPrj.coupons.service;

import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
import java.util.List;

/**
 * CouponService 인터페이스는 쿠폰 관련 비즈니스 로직을 정의합니다.
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
