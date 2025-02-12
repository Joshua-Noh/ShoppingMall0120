package com.shop.ShoppingMall_TeamPrj.admin.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;

public interface ProductDAO {
    public List<ProductVO> selectAllProductList() throws DataAccessException;
    public int insertProduct(ProductVO productVO) throws DataAccessException;
    public int deleteProduct(int id) throws DataAccessException;
    public int updateProduct(ProductVO productVO) throws DataAccessException;
    public ProductVO updateProductList(int id) throws DataAccessException;
    
    // DetailImage ���̺� �̹��� ������ ����ϱ� ���� �޼��� �߰�
    public int insertDetailImage(int productId, String fileName, String fileType) throws DataAccessException;
}
