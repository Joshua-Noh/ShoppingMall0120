package com.shop.ShoppingMall_TeamPrj.admin.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

public interface ProductService {
	public List<ProductVO> listProducts() throws Exception;
	public int addproduct(ProductVO productVO) throws DataAccessException;
	public int removeProduct(int id) throws DataAccessException;
	public int updateProduct(ProductVO productVO);
	public ProductVO updateProductFormData(int id) throws DataAccessException;
	
}
