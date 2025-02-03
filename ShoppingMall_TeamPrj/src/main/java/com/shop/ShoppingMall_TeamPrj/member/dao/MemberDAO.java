package com.shop.ShoppingMall_TeamPrj.member.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface MemberDAO {
    public List selectAllMemberList() throws DataAccessException;
    public int insertMember(MemberVO memberVO) throws DataAccessException;
    public int deleteMember(String id) throws DataAccessException;
    public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
    public int updateMember(MemberVO memberVO) throws DataAccessException;
    
    // 카카오 소셜 로그인 관련: 카카오 ID로 회원 조회
    public MemberVO selectMemberByKakaoId(Long kakaoId) throws DataAccessException;
    
    // 휴대폰 번호로 회원 조회 (ID/비밀번호 찾기 기능에 필요)
    public MemberVO findMemberByPhone(String phone) throws DataAccessException;
}
