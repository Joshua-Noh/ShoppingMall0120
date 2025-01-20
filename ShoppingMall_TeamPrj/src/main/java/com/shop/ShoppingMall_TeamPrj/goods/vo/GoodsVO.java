package com.shop.ShoppingMall_TeamPrj.goods.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("goodsVO")
public class GoodsVO {
	private int product_id;
	private String product_name ;
	private int category_id ;
	private int price ;
	private String size ;
	private String imageFileName;
	private String created_at;
	
	
	public GoodsVO() {
		System.out.println("GoodsVO »ý¼ºÀÚ");
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

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}


	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getImageFileName() {
		try {
			if (imageFileName != null && imageFileName.length() != 0) {
				imageFileName = URLDecoder.decode(imageFileName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if(imageFileName!= null && imageFileName.length()!=0) {
				this.imageFileName = URLEncoder.encode(imageFileName,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}



	
	
}
