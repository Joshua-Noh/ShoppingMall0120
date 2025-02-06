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
        // ������Ʈ�� ���� update �޼��带 ����մϴ�.
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
    
    // īī�� ID�� ȸ�� ��ȸ �޼��� �߰�
    @Override
    public MemberVO selectMemberByKakaoId(Long kakaoId) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.selectMemberByKakaoId", kakaoId);
        return vo;
    }
    
    // �޴��� ��ȣ�� ȸ�� ��ȸ �޼��� �߰� (ID/��й�ȣ ã�� ��ɿ��� ���)
    @Override
    public MemberVO findMemberByPhone(String phone) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.findMemberByPhone", phone);
        return vo;
    }
    
    // �ű� �߰�: ȸ�� ID�� ȸ�� ���� ��ȸ (�Ϲ� ����ڰ� ���� ������ ��ȸ�� �� ���)
    @Override
    public MemberVO getMemberById(int userId) throws DataAccessException {
        MemberVO vo = sqlSession.selectOne("mapper.member.getMemberById", userId);
        return vo;
    }
}
