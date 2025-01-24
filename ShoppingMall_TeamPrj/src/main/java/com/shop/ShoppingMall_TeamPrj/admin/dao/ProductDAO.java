package com.shop.ShoppingMall_TeamPrj.admin.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;



public interface ProductDAO {
	public List selectAllProductList() throws DataAccessException;
	public int insertProduct(ProductVO productVO) throws DataAccessException ;
	public int deleteProduct(int id) throws DataAccessException;
	public int updateProduct(ProductVO productVO)throws DataAccessException;
	public ProductVO updateProductList(int id) throws DataAccessException;
	
}
