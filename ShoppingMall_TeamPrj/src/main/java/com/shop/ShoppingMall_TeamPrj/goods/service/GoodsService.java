package com.shop.ShoppingMall_TeamPrj.goods.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

public interface GoodsService {
	 public List<GoodsVO> goodsList(int category_id) throws Exception;
	 
	 public List<GoodsVO> goodsNewList() throws Exception;
	 
	 public GoodsVO detailInfo(int product_id) throws Exception;
	 
	 public List<GoodsVO> searchGoods(String searchWord) throws Exception;
		/*
		 * public int addMember(MemberVO memberVO) throws DataAccessException; public
		 * int removeMember(String id) throws DataAccessException; public MemberVO
		 * login(MemberVO memberVO) throws Exception;
		 */
}
