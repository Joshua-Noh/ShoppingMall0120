package com.shop.ShoppingMall_TeamPrj.member.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List selectAllMemberList() throws DataAccessException {
        List<MemberVO> membersList = null;
        membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
        return membersList;
    }

    @Override
    public int insertMember(MemberVO memberVO) throws DataAccessException {
        int result = sqlSession.insert("mapper.member.insertMember", memberVO);
        return result;
    }
    
    @Override
    public int updateMember(MemberVO memberVO) throws DataAccessException {
        // 업데이트는 보통 update 메서드를 사용합니다.
        int result = sqlSession.update("mapper.member.updateMember", memberVO);
        return result;
    }
    
    @Override
    public int deleteMember(String id) throws DataAccessException {
        int result = sqlSession.delete("mapper.member.deleteMember", id);
        return result;
    }
    
    @Override
    public MemberVO loginById(MemberVO memberVO) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.loginById", memberVO);
        return vo;
    }
    
    // 카카오 ID로 회원 조회 메서드 추가
    @Override
    public MemberVO selectMemberByKakaoId(Long kakaoId) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.selectMemberByKakaoId", kakaoId);
        return vo;
    }
    
    // 휴대폰 번호로 회원 조회 메서드 추가 (ID/비밀번호 찾기 기능에서 사용)
    @Override
    public MemberVO findMemberByPhone(String phone) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.findMemberByPhone", phone);
        return vo;
    }
    
    // 신규 추가: 회원 ID로 회원 정보 조회 (일반 사용자가 본인 정보만 조회할 때 사용)
    @Override
    public MemberVO getMemberById(int userId) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.getMemberById", userId);
        return vo;
    }
}
