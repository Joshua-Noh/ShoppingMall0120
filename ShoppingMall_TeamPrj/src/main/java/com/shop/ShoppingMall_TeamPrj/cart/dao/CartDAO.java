package com.shop.ShoppingMall_TeamPrj.cart.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

public interface CartDAO {
	public List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException;
	public List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException;
	public boolean selectCountInCart(CartVO cartVO) throws DataAccessException;
	public void insertGoodsInCart(CartVO cartVO) throws DataAccessException;
	public void updateCartQuantity(CartVO cartVO) throws DataAccessException;
	public void deleteCartItem(int cart_id) throws DataAccessException;
	public CartVO selectCartItem(CartVO cartVO) throws DataAccessException;

	

}
