package com.shop.ShoppingMall_TeamPrj.admin.vo;

public class DetailImageVO {
    // imageId�� �ڵ� ����(PK)�̹Ƿ�, insert �ÿ��� �ʿ����� ���� �� ����
    private int imageId;
    private int productId;
    private String fileName;
    private String fileType;

    // �⺻ ������
    public DetailImageVO() {}

    // ��� �ʵ带 �����ϴ� ������ (imageId�� ���� ������� ����)
    public DetailImageVO(int productId, String fileName, String fileType) {
        this.productId = productId;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    // Getter �� Setter
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
