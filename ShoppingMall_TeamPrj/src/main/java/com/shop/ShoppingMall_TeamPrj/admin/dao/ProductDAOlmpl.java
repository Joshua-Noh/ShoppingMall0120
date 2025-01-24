package com.shop.ShoppingMall_TeamPrj.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;


@Repository("productDAO")
public class ProductDAOlmpl implements ProductDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllProductList() throws DataAccessException {
		List<ProductVO> ProductList = sqlSession.selectList("mapper.product.selectAllProductList");
		//List<ProductVO> ProductList = sqlSession.selectOne("mapper.product.selectAllProductList");
		return ProductList;
	}

	@Override
	public int insertProduct(ProductVO productVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.product.insertProduct", productVO);
		return result;
	}
	
	@Override
	public int deleteProduct(int id) throws DataAccessException {
		int result = sqlSession.delete("mapper.product.deleteProduct", id);
		return result;
	}
	@Override
	public int updateProduct(ProductVO productVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.product.updateProduct", productVO);
		return result;
	}
	@Override
	public ProductVO updateProductList(int id) throws DataAccessException {
	    ProductVO product = sqlSession.selectOne("mapper.product.updateProductFormData", id);
	    return product;
	}

}
