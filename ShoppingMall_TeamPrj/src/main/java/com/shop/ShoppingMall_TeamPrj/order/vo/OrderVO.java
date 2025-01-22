package com.shop.ShoppingMall_TeamPrj.order.vo;

import org.springframework.stereotype.Component;
import java.sql.Date;

@Component("orderVO")
public class OrderVO {
	private int order_id;
	private int user_id;
	private int product_id;
	private String product_name;
	private int quantity;
	private Date order_date; 
	private String delivery_state; 
	private int total_price; 
	
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getDelivery_state() {
		return delivery_state;
	}
	public void setDelivery_state(String delivery_state) {
		this.delivery_state = delivery_state;
	}
	public int gettotal_price() {
		return total_price;
	}
	public void settotal_price(int total_price) {
		this.total_price = total_price;
	}
	

}
