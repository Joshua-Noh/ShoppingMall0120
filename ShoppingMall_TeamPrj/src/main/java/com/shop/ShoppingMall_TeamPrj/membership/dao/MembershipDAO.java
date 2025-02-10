package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;

/**
 * MembershipDAO�� ����� ����� ������ �����ϴ� ������ ���� �������̽��Դϴ�.
 */
public interface MembershipDAO {
    MembershipVO getMembershipByUserId(int userId);
    int insertMembership(MembershipVO membership);
    int updateMembership(MembershipVO membership);
}
