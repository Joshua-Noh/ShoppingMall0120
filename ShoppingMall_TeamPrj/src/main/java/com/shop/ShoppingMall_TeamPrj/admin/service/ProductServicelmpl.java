package com.shop.ShoppingMall_TeamPrj.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shop.ShoppingMall_TeamPrj.admin.dao.ProductDAO;
import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServicelmpl implements ProductService {
    @Autowired
    ProductDAO productDAO;
    
    @Override
    public List<ProductVO> listProducts() throws Exception {
        List<ProductVO> articlesList = productDAO.selectAllProductList();
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
        return productDAO.updateProduct(product);
    }
    
    @Override
    public ProductVO updateProductFormData(int id) throws DataAccessException {
        return productDAO.updateProductList(id);
    }
    
    // detail_image 테이블에 이미지 정보를 기록하는 메서드 추가
    @Override
    public int insertDetailImage(int productId, String fileName, String fileType) throws DataAccessException {
        return productDAO.insertDetailImage(productId, fileName, fileType);
    }
}
