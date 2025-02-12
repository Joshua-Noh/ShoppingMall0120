package com.shop.ShoppingMall_TeamPrj.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.admin.vo.DetailImageVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

@Repository("productDAO")
public class ProductDAOlmpl implements ProductDAO {
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<ProductVO> selectAllProductList() throws DataAccessException {
        List<ProductVO> ProductList = sqlSession.selectList("mapper.product.selectAllProductList");
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
        int result = sqlSession.update("mapper.product.updateProduct", productVO);
        return result;
    }
    
    @Override
    public ProductVO updateProductList(int id) throws DataAccessException {
        ProductVO product = sqlSession.selectOne("mapper.product.updateProductFormData", id);
        return product;
    }
    
    // detail_image 테이블에 이미지 정보를 기록하는 메서드 추가
    @Override
    public int insertDetailImage(int productId, String fileName, String fileType) throws DataAccessException {
        DetailImageVO detailImage = new DetailImageVO(productId, fileName, fileType);
        return sqlSession.insert("mapper.product.insertDetailImage", detailImage);
    }
}
