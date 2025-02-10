package com.shop.ShoppingMall_TeamPrj.membership.service;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipLevelVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.util.List;

/**
 * MembershipService �������̽��� ����� ���� ����Ͻ� ������ �����մϴ�.
 */
public interface MembershipService {
    MembershipVO getMembershipByUserId(int userId);
    int saveMembership(MembershipVO membership);
    List<MembershipLevelVO> getAllMembershipLevels();
    MembershipLevelVO getMembershipLevel(String membershipLevel);
    int registerTransaction(MembershipTransactionVO transaction);
    List<MembershipTransactionVO> getTransactionsByUserId(int userId);
}
