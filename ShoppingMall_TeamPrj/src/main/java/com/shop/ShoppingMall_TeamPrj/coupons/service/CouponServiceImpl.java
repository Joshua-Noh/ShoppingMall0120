package com.shop.ShoppingMall_TeamPrj.coupons.service;

import com.shop.ShoppingMall_TeamPrj.coupons.dao.CouponDAO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CouponServiceImpl은 CouponService 인터페이스를 구현하며,
 * CouponDAO를 이용해 쿠폰 관련 비즈니스 로직을 처리합니다.
 */
@Service  // 이 클래스가 서비스 빈임을 Spring에 알립니다.
public class CouponServiceImpl implements CouponService {

    @Autowired  // CouponDAO 빈이 자동으로 주입됩니다.
    private CouponDAO couponDAO;

    @Override
    public CouponVO getCouponById(int couponId) {
        return couponDAO.getCouponById(couponId);
    }

    @Override
    public List<CouponVO> getAllCoupons() {
        return couponDAO.getAllCoupons();
    }

    @Override
    public int registerCoupon(CouponVO coupon) {
        return couponDAO.insertCoupon(coupon);
    }

    @Override
    public int updateCoupon(CouponVO coupon) {
        return couponDAO.updateCoupon(coupon);
    }

    @Override
    public int removeCoupon(int couponId) {
        return couponDAO.deleteCoupon(couponId);
    }

    @Override
    public List<UserCouponVO> getUserCouponsByUserId(int userId) {
        return couponDAO.getUserCouponsByUserId(userId);
    }

    @Override
    public int registerUserCoupon(UserCouponVO userCoupon) {
        return couponDAO.insertUserCoupon(userCoupon);
    }
}
