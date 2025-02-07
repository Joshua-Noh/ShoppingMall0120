package com.shop.ShoppingMall_TeamPrj.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

public interface CartService {
    public Map myCartList(CartVO cartVO) throws Exception;
    public CartVO findCartItem(CartVO cartVO) throws Exception;
    public void addGoodsInCart(CartVO cartVO) throws Exception;
    public void deleteCartItem(int cart_id) throws Exception;
    public void updateCartQuantity(CartVO cartVO) throws Exception;
    
    // 주문 완료 후 장바구니 삭제
    public void clearCartForUser(int user_id) throws Exception;
	/*
	 * public boolean modifyCartQty(CartVO cartVO) throws Exception; public void
	 * removeCartGoods(int cart_id) throws Exception;
	 */
}

