package com.shop.ShoppingMall_TeamPrj.membership.vo;

/**
 * MembershipTransactionVO�� ����Ʈ ����/��� �ŷ� ������ �����մϴ�.
 */
public class MembershipTransactionVO {
    private int transactionId;        // �ŷ� ���� ID
    private int userId;               // ����� ID
    private int pointsChange;         // ����(���) �Ǵ� ���(����) ����Ʈ
    private String transactionType;   // �ŷ� ���� ('Earned','Spent')
    private java.sql.Timestamp transactionDate;  // �ŷ� �ð�
    private String description;       // �ŷ� ����

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

    // Getter/Setter �޼ҵ��
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
