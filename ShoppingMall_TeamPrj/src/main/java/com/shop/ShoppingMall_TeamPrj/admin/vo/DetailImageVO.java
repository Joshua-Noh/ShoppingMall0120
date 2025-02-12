package com.shop.ShoppingMall_TeamPrj.admin.vo;

public class DetailImageVO {
    // imageId는 자동 증가(PK)이므로, insert 시에는 필요하지 않을 수 있음
    private int imageId;
    private int productId;
    private String fileName;
    private String fileType;

    // 기본 생성자
    public DetailImageVO() {}

    // 모든 필드를 포함하는 생성자 (imageId는 보통 사용하지 않음)
    public DetailImageVO(int productId, String fileName, String fileType) {
        this.productId = productId;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    // Getter 및 Setter
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
