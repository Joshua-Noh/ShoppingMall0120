package com.shop.ShoppingMall_TeamPrj.main.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.shop.ShoppingMall_TeamPrj.main.member.dao.MemberDAO;
import com.shop.ShoppingMall_TeamPrj.main.member.vo.MemberVO;



@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;

	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		membersList = memberDAO.selectAllMemberList();
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
    public int updateMember(MemberVO member) {
        return memberDAO.updateMember(member); // 영향받은 행 수 반환
    }
	
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception{
		return memberDAO.loginById(memberVO);
	}

}
