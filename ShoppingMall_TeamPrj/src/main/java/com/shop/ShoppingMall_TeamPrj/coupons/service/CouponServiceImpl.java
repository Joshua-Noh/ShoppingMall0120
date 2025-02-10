package com.shop.ShoppingMall_TeamPrj.coupons.service;

import com.shop.ShoppingMall_TeamPrj.coupons.dao.CouponDAO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.UserCouponVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CouponServiceImpl�� CouponService �������̽��� �����ϸ�,
 * CouponDAO�� �̿��� ���� ���� ����Ͻ� ������ ó���մϴ�.
 */
@Service  // �� Ŭ������ ���� ������ Spring�� �˸��ϴ�.
public class CouponServiceImpl implements CouponService {

    @Autowired  // CouponDAO ���� �ڵ����� ���Ե˴ϴ�.
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
