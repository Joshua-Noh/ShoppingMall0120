package com.shop.ShoppingMall_TeamPrj.cart.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;

@Repository("cartDAO")
public class CartDAOImpl implements CartDAO {
    @Autowired
    private SqlSession sqlSession;

    // ��ٱ��� ��� ��ȸ
    public List selectCartList(CartVO cartVO) throws DataAccessException {
        List cartList = sqlSession.selectList("mapper.cart.selectCartList", cartVO);
        return cartList;
    }

    // ��ǰ ���� ��� ��ȸ (��ǰ ID ����� �Ķ���ͷ�)
    public List selectGoodsList(List productIds) throws DataAccessException {
        List myGoodsList = sqlSession.selectList("mapper.cart.selectGoodsList", productIds);
        System.out.println("productIds: " + productIds);
        return myGoodsList;
    }

    // ��ٱ��Ͽ� �ش� ��ǰ�� �ִ��� Ȯ�� (���ڿ� "true"/"false"�� �Ľ�)
    public boolean selectCountInCart(CartVO cartVO) throws DataAccessException {
        String result = sqlSession.selectOne("mapper.cart.selectCountInCart", cartVO);
        return Boolean.parseBoolean(result);
    }

    // ��ٱ��Ͽ� ��ǰ �߰� (���ο� cart_id�� �Ҵ�)
    public void insertGoodsInCart(CartVO cartVO) throws DataAccessException {
        int cart_id = selectMaxCartId();
        cartVO.setCart_id(cart_id);
        sqlSession.insert("mapper.cart.insertGoodsInCart", cartVO);
    }

    // ��ٱ��� ��ǰ ���� ������Ʈ (update ���)
    public void updateCartQuantity(CartVO cartVO) throws DataAccessException {
        sqlSession.update("mapper.cart.updateCartQuantity", cartVO);
    }

    // ��ٱ��� ��ǰ ����
    public void deleteCartItem(int cart_id) throws DataAccessException {
        sqlSession.delete("mapper.cart.deleteCartItem", cart_id);
    }

    // ���� ū cart_id ��ȸ
    public int selectMaxCartId() throws DataAccessException {
        int cart_id = sqlSession.selectOne("mapper.cart.selectMaxCartId");
        return cart_id;
    }

    @Override
    public CartVO selectCartItem(CartVO cartVO) throws DataAccessException {
        return sqlSession.selectOne("mapper.cart.selectCartItem", cartVO);
    }
    
    // �ֹ� �Ϸ� �� �ش� ������� ��ٱ��� ��ü ����
    @Override
    public void clearCartForUser(int user_id) throws DataAccessException {
        sqlSession.delete("mapper.cart.clearCartForUser", user_id);
    }
}
