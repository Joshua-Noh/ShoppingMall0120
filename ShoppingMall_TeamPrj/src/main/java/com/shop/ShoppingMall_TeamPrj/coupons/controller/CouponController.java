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
     * 모든 쿠폰 목록을 조회합니다.
     * URL 예: /coupon/list.do
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String listCoupons(Model model) {
        List<CouponVO> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "coupons/couponView";
    }

    /**
     * 특정 쿠폰의 상세 정보를 조회합니다.
     * URL 예: /coupon/view.do?couponId=번호
     */
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public String viewCoupon(int couponId, Model model) {
        CouponVO coupon = couponService.getCouponById(couponId);
        model.addAttribute("coupon", coupon);
        return "coupons/couponView";
    }

    /**
     * 새로운 쿠폰을 생성합니다.
     * URL 예: /coupon/create.do (POST 방식)
     */
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String createCoupon(CouponVO coupon) {
        // 사용된 횟수는 기본적으로 0으로 설정
        coupon.setUsedCount(0);
        couponService.registerCoupon(coupon);
        return "redirect:/coupon/list.do";
    }

    /**
     * 기존 쿠폰을 업데이트합니다.
     * URL 예: /coupon/update.do (POST 방식)
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String updateCoupon(CouponVO coupon) {
        couponService.updateCoupon(coupon);
        return "redirect:/coupon/view.do?couponId=" + coupon.getCouponId();
    }

    /**
     * 쿠폰을 삭제합니다.
     * URL 예: /coupon/delete.do (POST 방식)
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String deleteCoupon(int couponId) {
        couponService.removeCoupon(couponId);
        return "redirect:/coupon/list.do";
    }
}
