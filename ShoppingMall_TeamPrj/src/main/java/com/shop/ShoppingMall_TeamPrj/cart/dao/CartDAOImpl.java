package com.shop.ShoppingMall_TeamPrj.cart.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

@Repository("cartDAO")
public class CartDAOImpl implements CartDAO {
    @Autowired
    private SqlSession sqlSession;

    // 1. selectCartList 메서드 수정
    public List selectCartList(CartVO cartVO) throws DataAccessException {  // List<CartVO> -> List로 변경
        List cartList = sqlSession.selectList("mapper.cart.selectCartList", cartVO);
        return cartList;
    }

    public List selectGoodsList(List productIds) throws DataAccessException {
        List myGoodsList = sqlSession.selectList("mapper.cart.selectGoodsList", productIds);
        System.out.println("productIds 4시30분 : " + productIds);  // productIds 값 확인
        return myGoodsList;
        
    }

    // 3. selectCountInCart 메서드 수정
    public boolean selectCountInCart(CartVO cartVO) throws DataAccessException {
        String result = sqlSession.selectOne("mapper.cart.selectCountInCart", cartVO);
        return Boolean.parseBoolean(result);
    }

    // 4. insertGoodsInCart 메서드 수정
    public void insertGoodsInCart(CartVO cartVO) throws DataAccessException {
        int cart_id = selectMaxCartId();
        cartVO.setCart_id(cart_id);
        sqlSession.insert("mapper.cart.insertGoodsInCart", cartVO);
    }

    // 5. updateCartGoodsQty 메서드 수정
    public void updateCartQuantity(CartVO cartVO) throws DataAccessException {
        sqlSession.insert("mapper.cart.updateCartQuantity", cartVO);
    }

    // 6. deleteCartGoods 메서드 수정
    public void deleteCartItem(int cart_id) throws DataAccessException {
        sqlSession.delete("mapper.cart.deleteCartItem", cart_id);
    }

    // 7. selectMaxCartId 메서드 수정
    private int selectMaxCartId() throws DataAccessException {
        int cart_id = sqlSession.selectOne("mapper.cart.selectMaxCartId");
        return cart_id;
    }
    @Override
    public CartVO selectCartItem(CartVO cartVO) throws DataAccessException {
        return sqlSession.selectOne("mapper.cart.selectCartItem", cartVO);
    }

}
