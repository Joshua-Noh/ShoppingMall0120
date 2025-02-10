package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;

/**
 * MembershipDAO는 사용자 멤버십 정보를 관리하는 데이터 접근 인터페이스입니다.
 */
public interface MembershipDAO {
    MembershipVO getMembershipByUserId(int userId);
    int insertMembership(MembershipVO membership);
    int updateMembership(MembershipVO membership);
}
