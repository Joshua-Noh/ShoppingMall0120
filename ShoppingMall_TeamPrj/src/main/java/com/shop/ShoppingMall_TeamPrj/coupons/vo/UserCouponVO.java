package com.shop.ShoppingMall_TeamPrj.coupons.vo;

/**
 * UserCouponVO는 사용자와 쿠폰의 관계를 관리하는 VO 클래스입니다.
 */
public class UserCouponVO {
    private int id;                   // 고유 레코드 ID
    private int userId;               // 사용자 ID
    private int couponId;             // 쿠폰 ID
    private String status;            // 쿠폰 사용 상태 ('Unused','Used','Expired')
    private java.sql.Timestamp usedAt; // 쿠폰 사용 시각

    public UserCouponVO() {
    }

    public UserCouponVO(int id, int userId, int couponId, String status, java.sql.Timestamp usedAt) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.status = status;
        this.usedAt = usedAt;
    }

    // Getter/Setter 메소드들 ...
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public java.sql.Timestamp getUsedAt() {
        return usedAt;
    }
    public void setUsedAt(java.sql.Timestamp usedAt) {
        this.usedAt = usedAt;
    }

    @Override
    public String toString() {
        return "UserCouponVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", couponId=" + couponId +
                ", status='" + status + '\'' +
                ", usedAt=" + usedAt +
                '}';
    }
}
