package com.shop.ShoppingMall_TeamPrj.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

public interface CartService {
	public Map<String ,List> myCartList(CartVO cartVO) throws Exception;
	public boolean findCartGoods(CartVO cartVO) throws Exception;
	public void addGoodsInCart(CartVO cartVO) throws Exception;
	public void deleteCartItem(int cart_id) throws Exception;
	public void updateCartQuantity(CartVO cartVO) throws Exception;
	/*
	 * public boolean modifyCartQty(CartVO cartVO) throws Exception; public void
	 * removeCartGoods(int cart_id) throws Exception;
	 */
}
