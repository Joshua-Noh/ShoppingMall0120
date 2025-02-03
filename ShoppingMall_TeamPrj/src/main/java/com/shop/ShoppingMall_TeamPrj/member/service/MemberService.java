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
    
    // īī�� ID�� ȸ�� ��ȸ�ϱ� ���� �޼��� �߰�
    public MemberVO getMemberByKakaoId(Long kakaoId) throws DataAccessException;
    
    // �޴��� ��ȣ�� ���� ȸ�� ��ȸ (ID �� ��й�ȣ ã�� ��ɿ� ���)
    public MemberVO findMemberByPhone(String phone) throws DataAccessException;
}
