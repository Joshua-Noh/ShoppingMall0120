package com.shop.ShoppingMall_TeamPrj.member.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface MemberDAO {
    List<MemberVO> selectAllMemberList() throws DataAccessException;
    
    int insertMember(MemberVO memberVO) throws DataAccessException;
    
    int updateMember(MemberVO memberVO) throws DataAccessException;
    
    int deleteMember(String id) throws DataAccessException;
    
    MemberVO loginById(MemberVO memberVO) throws DataAccessException;
    
    // 카카오 ID로 회원 조회
    MemberVO selectMemberByKakaoId(Long kakaoId) throws DataAccessException;
    
    // 휴대폰 번호로 회원 조회 (ID/비밀번호 찾기 기능)
    MemberVO findMemberByPhone(String phone) throws DataAccessException;
    
    // 신규 추가: 회원 ID로 회원 정보 조회 (일반 사용자가 본인 정보만 조회할 때 사용)
    MemberVO getMemberById(int userId) throws DataAccessException;
}
