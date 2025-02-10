package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipLevelVO;
import java.util.List;

/**
 * MembershipLevelDAO는 멤버십 등급 정보를 관리하는 데이터 접근 인터페이스입니다.
 */
public interface MembershipLevelDAO {
    MembershipLevelVO getMembershipLevel(String membershipLevel);
    List<MembershipLevelVO> getAllMembershipLevels();
    int insertMembershipLevel(MembershipLevelVO level);
    int updateMembershipLevel(MembershipLevelVO level);
    int deleteMembershipLevel(String membershipLevel);
}
