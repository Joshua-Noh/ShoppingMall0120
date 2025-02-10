package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipLevelVO;
import java.util.List;

/**
 * MembershipLevelDAO�� ����� ��� ������ �����ϴ� ������ ���� �������̽��Դϴ�.
 */
public interface MembershipLevelDAO {
    MembershipLevelVO getMembershipLevel(String membershipLevel);
    List<MembershipLevelVO> getAllMembershipLevels();
    int insertMembershipLevel(MembershipLevelVO level);
    int updateMembershipLevel(MembershipLevelVO level);
    int deleteMembershipLevel(String membershipLevel);
}
