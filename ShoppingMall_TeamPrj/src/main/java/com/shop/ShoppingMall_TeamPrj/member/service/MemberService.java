package com.shop.ShoppingMall_TeamPrj.member.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface MemberService {
    List<MemberVO> listMembers() throws Exception;
    int addMember(MemberVO member) throws Exception;
    int removeMember(String id) throws Exception;
    int updateMember(MemberVO member) throws Exception;
    MemberVO login(MemberVO member) throws Exception;
    
    // 기존 추가 메서드들
    MemberVO getMemberByKakaoId(Long kakaoId) throws Exception;
    MemberVO findMemberByPhone(String phone) throws Exception;
    
    // 신규 추가: 회원 ID로 회원 정보 조회 (일반 사용자가 본인 정보만 조회할 때 사용)
    MemberVO getMemberById(int userId) throws Exception;
}
