package com.shop.ShoppingMall_TeamPrj.member.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface MemberService {
    List<MemberVO> listMembers() throws Exception;
    int addMember(MemberVO member) throws Exception;
    int removeMember(String id) throws Exception;
    int updateMember(MemberVO member) throws Exception;
    MemberVO login(MemberVO member) throws Exception;
    
    // ���� �߰� �޼����
    MemberVO getMemberByKakaoId(Long kakaoId) throws Exception;
    MemberVO findMemberByPhone(String phone) throws Exception;
    
    // �ű� �߰�: ȸ�� ID�� ȸ�� ���� ��ȸ (�Ϲ� ����ڰ� ���� ������ ��ȸ�� �� ���)
    MemberVO getMemberById(int userId) throws Exception;
}
