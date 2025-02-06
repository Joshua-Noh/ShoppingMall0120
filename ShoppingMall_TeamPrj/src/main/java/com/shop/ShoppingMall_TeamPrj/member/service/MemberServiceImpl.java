package com.shop.ShoppingMall_TeamPrj.member.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.shop.ShoppingMall_TeamPrj.member.dao.MemberDAO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Override
    public List<MemberVO> listMembers() throws DataAccessException {
        List<MemberVO> membersList = memberDAO.selectAllMemberList();
        return membersList;
    }

    @Override
    public int addMember(MemberVO member) throws DataAccessException {
        return memberDAO.insertMember(member);
    }

    @Override
    public int removeMember(String id) throws DataAccessException {
        return memberDAO.deleteMember(id);
    }
   
    @Override
    public int updateMember(MemberVO member) throws DataAccessException {
        // 영향받은 행 수 반환 (예: 1이면 성공)
        return memberDAO.updateMember(member);
    }
    
    @Override
    public MemberVO login(MemberVO memberVO) throws Exception {
        return memberDAO.loginById(memberVO);
    }
    
    // 추가된 메서드: 카카오 ID로 회원 조회 (소셜 로그인용)
    @Override
    public MemberVO getMemberByKakaoId(Long kakaoId) throws DataAccessException {
        return memberDAO.selectMemberByKakaoId(kakaoId);
    }
    
    // 추가된 메서드: 휴대폰 번호로 회원 조회 (ID 및 비밀번호 찾기 기능)
    @Override
    public MemberVO findMemberByPhone(String phone) throws DataAccessException {
        return memberDAO.findMemberByPhone(phone);
    }
    
    // 신규 추가: 회원 ID로 회원 정보 조회 (일반 사용자가 본인 정보만 조회할 때 사용)
    @Override
    public MemberVO getMemberById(int userId) throws DataAccessException {
        return memberDAO.getMemberById(userId);
    }
}
