package com.shop.ShoppingMall_TeamPrj.membership.vo;

/**
 * MembershipTransactionVO는 포인트 적립/사용 거래 내역을 관리합니다.
 */
public class MembershipTransactionVO {
    private int transactionId;        // 거래 고유 ID
    private int userId;               // 사용자 ID
    private int pointsChange;         // 적립(양수) 또는 사용(음수) 포인트
    private String transactionType;   // 거래 유형 ('Earned','Spent')
    private java.sql.Timestamp transactionDate;  // 거래 시각
    private String description;       // 거래 설명

    public MembershipTransactionVO() {
    }

    public MembershipTransactionVO(int transactionId, int userId, int pointsChange, String transactionType,
                                     java.sql.Timestamp transactionDate, String description) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.pointsChange = pointsChange;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    // Getter/Setter 메소드들
    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getPointsChange() {
        return pointsChange;
    }
    public void setPointsChange(int pointsChange) {
        this.pointsChange = pointsChange;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public java.sql.Timestamp getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(java.sql.Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MembershipTransactionVO{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", pointsChange=" + pointsChange +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                '}';
    }
}
