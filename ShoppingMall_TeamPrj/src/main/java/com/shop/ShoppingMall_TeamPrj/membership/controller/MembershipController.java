package com.shop.ShoppingMall_TeamPrj.membership.controller;

import com.shop.ShoppingMall_TeamPrj.membership.service.MembershipService;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    /**
     * 사용자의 멤버십 정보를 조회하여 뷰로 전달합니다.
     * URL 예: /membership/view.do?userId=번호
     */
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public String viewMembership(int userId, Model model) {
        MembershipVO membership = membershipService.getMembershipByUserId(userId);
        List<MembershipTransactionVO> transactions = membershipService.getTransactionsByUserId(userId);
        model.addAttribute("membership", membership);
        model.addAttribute("transactions", transactions);
        return "membership/membershipView";
    }

    /**
     * 멤버십 정보를 업데이트합니다.
     * URL 예: /membership/update.do (POST 방식)
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String updateMembership(MembershipVO membership) {
        membershipService.saveMembership(membership);
        return "redirect:/membership/view.do?userId=" + membership.getUserId();
    }

    /**
     * 새로운 포인트 거래(적립/사용)를 등록합니다.
     * URL 예: /membership/registerTransaction.do (POST 방식)
     */
    @RequestMapping(value = "/registerTransaction.do", method = RequestMethod.POST)
    public String registerTransaction(MembershipTransactionVO transaction) {
        membershipService.registerTransaction(transaction);
        return "redirect:/membership/view.do?userId=" + transaction.getUserId();
    }
}
