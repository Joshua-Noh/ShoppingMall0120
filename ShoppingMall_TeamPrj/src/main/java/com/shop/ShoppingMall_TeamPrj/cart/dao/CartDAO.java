package com.shop.ShoppingMall_TeamPrj.cart.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;

public interface CartDAO {
    public List selectCartList(CartVO cartVO) throws DataAccessException;
    public List selectGoodsList(List productIds) throws DataAccessException;
    public boolean selectCountInCart(CartVO cartVO) throws DataAccessException;
    public void insertGoodsInCart(CartVO cartVO) throws DataAccessException;
    public void updateCartQuantity(CartVO cartVO) throws DataAccessException;
    public void deleteCartItem(int cart_id) throws DataAccessException;
    public int selectMaxCartId() throws DataAccessException;
    public CartVO selectCartItem(CartVO cartVO) throws DataAccessException;
    
    // �ֹ� �Ϸ� �� ��ٱ��� ��ü ������ ���� �޼���
    public void clearCartForUser(int user_id) throws DataAccessException;
}
