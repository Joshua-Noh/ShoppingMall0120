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
     * ������� ����� ������ ��ȸ�Ͽ� ��� �����մϴ�.
     * URL ��: /membership/view.do?userId=��ȣ
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
     * ����� ������ ������Ʈ�մϴ�.
     * URL ��: /membership/update.do (POST ���)
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String updateMembership(MembershipVO membership) {
        membershipService.saveMembership(membership);
        return "redirect:/membership/view.do?userId=" + membership.getUserId();
    }

    /**
     * ���ο� ����Ʈ �ŷ�(����/���)�� ����մϴ�.
     * URL ��: /membership/registerTransaction.do (POST ���)
     */
    @RequestMapping(value = "/registerTransaction.do", method = RequestMethod.POST)
    public String registerTransaction(MembershipTransactionVO transaction) {
        membershipService.registerTransaction(transaction);
        return "redirect:/membership/view.do?userId=" + transaction.getUserId();
    }
}
