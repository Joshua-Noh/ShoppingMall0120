package com.shop.ShoppingMall_TeamPrj.coupons.vo;

/**
 * UserCouponVO�� ����ڿ� ������ ���踦 �����ϴ� VO Ŭ�����Դϴ�.
 */
public class UserCouponVO {
    private int id;                   // ���� ���ڵ� ID
    private int userId;               // ����� ID
    private int couponId;             // ���� ID
    private String status;            // ���� ��� ���� ('Unused','Used','Expired')
    private java.sql.Timestamp usedAt; // ���� ��� �ð�

    public UserCouponVO() {
    }

    public UserCouponVO(int id, int userId, int couponId, String status, java.sql.Timestamp usedAt) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.status = status;
        this.usedAt = usedAt;
    }

    // Getter/Setter �޼ҵ�� ...
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
