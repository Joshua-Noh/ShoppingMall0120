package com.shop.ShoppingMall_TeamPrj.goods.vo;

import java.sql.Date;
import org.springframework.stereotype.Component;

@Component("goodsVO")
public class GoodsVO {
    private int product_id;
    private String product_name;
    private int category_id;
    private int price;
    private String size;
    private String fileName;
    private Date created_at;
    // 하드코딩 처리: 나중에 DB에서 실제 값을 받아오도록 수정 예정
    private String description = "상품에 대한 상세 설명은 현재 하드코딩되어 있습니다. (추후 업데이트 예정)";

    public GoodsVO() {
        System.out.println("GoodsVO 생성자");
    }

    // 기존 getter/setter...
    public int getProduct_id() { return product_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id; }
    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }
    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public Date getCreated_at() { return created_at; }
    public void setCreated_at(Date created_at) { this.created_at = created_at; }

    // 추가: description 필드 getter/setter
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
