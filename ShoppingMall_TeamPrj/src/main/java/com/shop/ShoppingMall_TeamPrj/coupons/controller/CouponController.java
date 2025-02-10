package com.shop.ShoppingMall_TeamPrj.coupons.controller;

import com.shop.ShoppingMall_TeamPrj.coupons.service.CouponService;
import com.shop.ShoppingMall_TeamPrj.coupons.vo.CouponVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * ��� ���� ����� ��ȸ�մϴ�.
     * URL ��: /coupon/list.do
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String listCoupons(Model model) {
        List<CouponVO> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "coupons/couponView";
    }

    /**
     * Ư�� ������ �� ������ ��ȸ�մϴ�.
     * URL ��: /coupon/view.do?couponId=��ȣ
     */
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public String viewCoupon(int couponId, Model model) {
        CouponVO coupon = couponService.getCouponById(couponId);
        model.addAttribute("coupon", coupon);
        return "coupons/couponView";
    }

    /**
     * ���ο� ������ �����մϴ�.
     * URL ��: /coupon/create.do (POST ���)
     */
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String createCoupon(CouponVO coupon) {
        // ���� Ƚ���� �⺻������ 0���� ����
        coupon.setUsedCount(0);
        couponService.registerCoupon(coupon);
        return "redirect:/coupon/list.do";
    }

    /**
     * ���� ������ ������Ʈ�մϴ�.
     * URL ��: /coupon/update.do (POST ���)
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String updateCoupon(CouponVO coupon) {
        couponService.updateCoupon(coupon);
        return "redirect:/coupon/view.do?couponId=" + coupon.getCouponId();
    }

    /**
     * ������ �����մϴ�.
     * URL ��: /coupon/delete.do (POST ���)
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String deleteCoupon(int couponId) {
        couponService.removeCoupon(couponId);
        return "redirect:/coupon/list.do";
    }
}
