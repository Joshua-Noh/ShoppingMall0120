package com.shop.ShoppingMall_TeamPrj.coupons.vo;

/**
 * CouponVO�� ���� ������ ĸ��ȭ�ϴ� Ŭ�����Դϴ�.
 */
public class CouponVO {
    private int couponId;             // ���� ���� ID (Auto-increment)
    private String couponCode;        // ���� ���� �ڵ�
    private String description;       // ���� ����
    private String discountType;      // ���� ��� ('percentage' �Ǵ� 'fixed')
    private double discountValue;     // ���� �� (�ݾ� �Ǵ� ����)
    private double minPurchase;       // �ּ� ���� �ݾ�
    private java.sql.Timestamp validFrom;  // ���� ��ȿ ������
    private java.sql.Timestamp validTo;    // ���� ��ȿ ������
    private Integer usageLimit;       // ��� ���� Ƚ�� (NULL�̸� ������)
    private int usedCount;            // ���� Ƚ��
    private String status;            // ���� ���� ('Active', 'Expired', 'Disabled')

    public CouponVO() {
    }

    // ��ü �ʵ带 �ʱ�ȭ�ϴ� ������
    public CouponVO(int couponId, String couponCode, String description, String discountType, double discountValue,
                    double minPurchase, java.sql.Timestamp validFrom, java.sql.Timestamp validTo,
                    Integer usageLimit, int usedCount, String status) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPurchase = minPurchase;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.usageLimit = usageLimit;
        this.usedCount = usedCount;
        this.status = status;
    }

    // Getter/Setter �޼ҵ�� ...
    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDiscountType() {
        return discountType;
    }
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
    public double getDiscountValue() {
        return discountValue;
    }
    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
    public double getMinPurchase() {
        return minPurchase;
    }
    public void setMinPurchase(double minPurchase) {
        this.minPurchase = minPurchase;
    }
    public java.sql.Timestamp getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(java.sql.Timestamp validFrom) {
        this.validFrom = validFrom;
    }
    public java.sql.Timestamp getValidTo() {
        return validTo;
    }
    public void setValidTo(java.sql.Timestamp validTo) {
        this.validTo = validTo;
    }
    public Integer getUsageLimit() {
        return usageLimit;
    }
    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }
    public int getUsedCount() {
        return usedCount;
    }
    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CouponVO{" +
                "couponId=" + couponId +
                ", couponCode='" + couponCode + '\'' +
                ", description='" + description + '\'' +
                ", discountType='" + discountType + '\'' +
                ", discountValue=" + discountValue +
                ", minPurchase=" + minPurchase +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", usageLimit=" + usageLimit +
                ", usedCount=" + usedCount +
                ", status='" + status + '\'' +
                '}';
    }
}
