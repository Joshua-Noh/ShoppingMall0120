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

    // 장바구니 목록 조회
    public List selectCartList(CartVO cartVO) throws DataAccessException {
        List cartList = sqlSession.selectList("mapper.cart.selectCartList", cartVO);
        return cartList;
    }

    // 상품 정보 목록 조회 (상품 ID 목록을 파라미터로)
    public List selectGoodsList(List productIds) throws DataAccessException {
        List myGoodsList = sqlSession.selectList("mapper.cart.selectGoodsList", productIds);
        System.out.println("productIds: " + productIds);
        return myGoodsList;
    }

    // 장바구니에 해당 상품이 있는지 확인 (문자열 "true"/"false"를 파싱)
    public boolean selectCountInCart(CartVO cartVO) throws DataAccessException {
        String result = sqlSession.selectOne("mapper.cart.selectCountInCart", cartVO);
        return Boolean.parseBoolean(result);
    }

    // 장바구니에 상품 추가 (새로운 cart_id를 할당)
    public void insertGoodsInCart(CartVO cartVO) throws DataAccessException {
        int cart_id = selectMaxCartId();
        cartVO.setCart_id(cart_id);
        sqlSession.insert("mapper.cart.insertGoodsInCart", cartVO);
    }

    // 장바구니 상품 수량 업데이트 (update 사용)
    public void updateCartQuantity(CartVO cartVO) throws DataAccessException {
        sqlSession.update("mapper.cart.updateCartQuantity", cartVO);
    }

    // 장바구니 상품 삭제
    public void deleteCartItem(int cart_id) throws DataAccessException {
        sqlSession.delete("mapper.cart.deleteCartItem", cart_id);
    }

    // 가장 큰 cart_id 조회
    public int selectMaxCartId() throws DataAccessException {
        int cart_id = sqlSession.selectOne("mapper.cart.selectMaxCartId");
        return cart_id;
    }

    @Override
    public CartVO selectCartItem(CartVO cartVO) throws DataAccessException {
        return sqlSession.selectOne("mapper.cart.selectCartItem", cartVO);
    }
    
    // 주문 완료 후 해당 사용자의 장바구니 전체 삭제
    @Override
    public void clearCartForUser(int user_id) throws DataAccessException {
        sqlSession.delete("mapper.cart.clearCartForUser", user_id);
    }
}
