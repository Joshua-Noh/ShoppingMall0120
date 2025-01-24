package com.shop.ShoppingMall_TeamPrj.admin.vo;
import java.sql.Date;
import java.sql.Timestamp;

public class ProductVO {
    private int productId;
    private String productName;
    private int categoryId;
    private double price;
    private String size; // 'S', 'M', 'L', 'XL' �� �ϳ�
    private Date createdAt;

    // �⺻ ������
    public ProductVO() {}

    // ��� �ʵ带 �����ϴ� ������
    public ProductVO(String productName, int categoryId, double price, String size) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.price = price;
        this.size = size;
    }

    // Getter �� Setter
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
