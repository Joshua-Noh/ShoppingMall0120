package com.shop.ShoppingMall_TeamPrj.member.service;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface MemberService {
    public List listMembers() throws DataAccessException;
    public int addMember(MemberVO memberVO) throws DataAccessException;
    public int removeMember(String id) throws DataAccessException;
    public MemberVO login(MemberVO memberVO) throws Exception;
    public int updateMember(MemberVO member);
    
    // 카카오 ID로 회원 조회하기 위한 메서드 추가
    public MemberVO getMemberByKakaoId(Long kakaoId) throws DataAccessException;
    
    // 휴대폰 번호를 통한 회원 조회 (ID 및 비밀번호 찾기 기능에 사용)
    public MemberVO findMemberByPhone(String phone) throws DataAccessException;
}
