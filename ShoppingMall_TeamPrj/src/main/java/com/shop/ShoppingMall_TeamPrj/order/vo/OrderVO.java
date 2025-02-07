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
    private double total_price; 
    
    // 새로 추가된 필드들
    private String receiver_name;
    private String receiver_hp;
    private String receiver_tel;
    private String delivery_address;
    private String delivery_message;
    private String pay_method;
    private int order_group_id;
    
    // getter, setter
    
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
    
    public double getTotal_price() {
        return total_price;
    }
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
    
    public String getReceiver_name() {
        return receiver_name;
    }
    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }
    
    public String getReceiver_hp() {
        return receiver_hp;
    }
    public void setReceiver_hp(String receiver_hp) {
        this.receiver_hp = receiver_hp;
    }
    
    public String getReceiver_tel() {
        return receiver_tel;
    }
    public void setReceiver_tel(String receiver_tel) {
        this.receiver_tel = receiver_tel;
    }
    
    public String getDelivery_address() {
        return delivery_address;
    }
    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }
    
    public String getDelivery_message() {
        return delivery_message;
    }
    public void setDelivery_message(String delivery_message) {
        this.delivery_message = delivery_message;
    }
    
    public String getPay_method() {
        return pay_method;
    }
    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }
    
    public int getOrder_group_id() {
        return order_group_id;
    }
    public void setOrder_group_id(int order_group_id) {
        this.order_group_id = order_group_id;
    }
}
