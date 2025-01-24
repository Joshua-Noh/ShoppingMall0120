package com.shop.ShoppingMall_TeamPrj.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shop.ShoppingMall_TeamPrj.admin.dao.ProductDAO;
import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;


@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServicelmpl  implements ProductService{
	@Autowired
	ProductDAO productDAO;
	
	public List<ProductVO> listProducts() throws Exception{
		List<ProductVO> articlesList =  productDAO.selectAllProductList();
        return articlesList;
	}
	
	@Override
	public int addproduct(ProductVO product) throws DataAccessException {
		return productDAO.insertProduct(product);
	}
	
	@Override
	public int removeProduct(int id) throws DataAccessException {
		
		return productDAO.deleteProduct(id);
	}
	@Override
	
    public int updateProduct(ProductVO product) {
        return productDAO.updateProduct(product); // 영향받은 행 수 반환
    }
	
	@Override
	public ProductVO updateProductFormData(int id) throws DataAccessException {
		ProductVO productList = null;
		productList = productDAO.updateProductList(id);
		return productList;
	}

	

	

	
}
