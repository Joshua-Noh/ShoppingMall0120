package com.shop.ShoppingMall_TeamPrj.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shop.ShoppingMall_TeamPrj.goods.dao.GoodsDAO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;



@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsDAO goodsDAO;

	@Override
	public List<GoodsVO> goodsList(int category_id) throws Exception {
		List goodsList = null;
		goodsList = goodsDAO.selectGoodsList(category_id);
		return goodsList;
	}
	@Override
	public List<GoodsVO> goodsNewList() throws Exception {
		List goodsList = null;
		goodsList = goodsDAO.selectGoodsNewList();
		return goodsList;
	}
	
	@Override
	public GoodsVO detailInfo(int product_id) throws Exception {
		GoodsVO goodsVO = goodsDAO.selectGoods(product_id);
		return goodsVO;
	}
	public List<GoodsVO> searchGoods(String searchWord) throws Exception{
		List goodsList=goodsDAO.selectGoodsBySearchWord(searchWord);
		return goodsList;
	}

	/*
	 * @Override public int addMember(MemberVO member) throws DataAccessException {
	 * return memberDAO.insertMember(member); }
	 * 
	 * @Override public int removeMember(String id) throws DataAccessException {
	 * return memberDAO.deleteMember(id); }
	 * 
	 * @Override public MemberVO login(MemberVO memberVO) throws Exception{ return
	 * memberDAO.loginById(memberVO); }
	 */

}
