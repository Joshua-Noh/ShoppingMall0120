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
    
    // īī�� ID�� ȸ�� ��ȸ
    MemberVO selectMemberByKakaoId(Long kakaoId) throws DataAccessException;
    
    // �޴��� ��ȣ�� ȸ�� ��ȸ (ID/��й�ȣ ã�� ���)
    MemberVO findMemberByPhone(String phone) throws DataAccessException;
    
    // �ű� �߰�: ȸ�� ID�� ȸ�� ���� ��ȸ (�Ϲ� ����ڰ� ���� ������ ��ȸ�� �� ���)
    MemberVO getMemberById(int userId) throws DataAccessException;
}
