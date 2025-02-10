package com.shop.ShoppingMall_TeamPrj.coupons.vo;

/**
 * CouponVO는 쿠폰 정보를 캡슐화하는 클래스입니다.
 */
public class CouponVO {
    private int couponId;             // 쿠폰 고유 ID (Auto-increment)
    private String couponCode;        // 고유 쿠폰 코드
    private String description;       // 쿠폰 설명
    private String discountType;      // 할인 방식 ('percentage' 또는 'fixed')
    private double discountValue;     // 할인 값 (금액 또는 비율)
    private double minPurchase;       // 최소 구매 금액
    private java.sql.Timestamp validFrom;  // 쿠폰 유효 시작일
    private java.sql.Timestamp validTo;    // 쿠폰 유효 종료일
    private Integer usageLimit;       // 사용 제한 횟수 (NULL이면 무제한)
    private int usedCount;            // 사용된 횟수
    private String status;            // 쿠폰 상태 ('Active', 'Expired', 'Disabled')

    public CouponVO() {
    }

    // 전체 필드를 초기화하는 생성자
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

    // Getter/Setter 메소드들 ...
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
