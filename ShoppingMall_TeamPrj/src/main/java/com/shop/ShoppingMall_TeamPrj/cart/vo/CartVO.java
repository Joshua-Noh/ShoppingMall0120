package com.shop.ShoppingMall_TeamPrj.cart.vo;

import org.springframework.stereotype.Component;

@Component("cartVO")
public class CartVO {
	private int cart_id;
	private int user_id;
	private int product_id;
	private int quantity;
	private String added_date;
	
	
	public int getCart_id() {
		return cart_id;
	}
	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	
	
	public int getquantity() {
		return quantity;
	}
	public void setquantity(int quantity) {
		this.quantity = quantity;
	}
	public String getadded_date() {
		return added_date;
	}
	public void setadded_date(String added_date) {
		this.added_date = added_date;
	}
	
	
	

}
