package com.shop.ShoppingMall_TeamPrj.coupons.dao;

import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
import java.util.List;

/**
 * CouponDAO 인터페이스는 쿠폰 관련 데이터 접근 작업을 정의합니다.
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
