package com.shop.ShoppingMall_TeamPrj.main.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;


import com.shop.ShoppingMall_TeamPrj.main.member.vo.MemberVO;


public interface MemberService {
	 public List listMembers() throws DataAccessException;
	 public int addMember(MemberVO memberVO) throws DataAccessException;
	 public int removeMember(String id) throws DataAccessException;
	 public MemberVO login(MemberVO memberVO) throws Exception;
	 public int updateMember(MemberVO member);
}
