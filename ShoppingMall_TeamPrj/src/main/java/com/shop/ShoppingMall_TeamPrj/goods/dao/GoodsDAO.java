package com.shop.ShoppingMall_TeamPrj.goods.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;


public interface GoodsDAO {
	public List selectGoodsList(int category_id) throws DataAccessException;
	
	public List selectGoodsNewList() throws DataAccessException;
	
	public GoodsVO selectGoods(int product_id) throws DataAccessException;

	// 1�� 22�� �߰����� Ǯ ���� �ּ� �����ϼŵ� �����մϴ�.
	public List<String> selectImageFiles(int product_id) throws DataAccessException;

	/*
	 * public int insertNewArticle(Map articleMap) throws DataAccessException;
	 * public void insertReplyArticle(Map articleMap) throws DataAccessException;
	 * //public void insertNewImage(Map articleMap) throws DataAccessException;
	 * 
	 * public GoodsVO selectArticle(int articleNO) throws DataAccessException;
	 * public void updateArticle(Map articleMap) throws DataAccessException; public
	 * void deleteArticle(int articleNO) throws DataAccessException; public List
	 * selectImageFileList(int articleNO) throws DataAccessException;
	 */
	
}
