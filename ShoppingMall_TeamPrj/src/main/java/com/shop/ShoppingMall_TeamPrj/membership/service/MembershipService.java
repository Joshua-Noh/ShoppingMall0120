package com.shop.ShoppingMall_TeamPrj.membership.service;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipLevelVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.util.List;

/**
 * MembershipService 인터페이스는 멤버십 관련 비즈니스 로직을 정의합니다.
 */
public interface MembershipService {
    MembershipVO getMembershipByUserId(int userId);
    int saveMembership(MembershipVO membership);
    List<MembershipLevelVO> getAllMembershipLevels();
    MembershipLevelVO getMembershipLevel(String membershipLevel);
    int registerTransaction(MembershipTransactionVO transaction);
    List<MembershipTransactionVO> getTransactionsByUserId(int userId);
}
