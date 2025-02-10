package com.shop.ShoppingMall_TeamPrj.membership.vo;

/**
 * MembershipVO�� ����� ����� ������ ĸ��ȭ�ϴ� Ŭ�����Դϴ�.
 */
public class MembershipVO {
    private int userId;                   // ����� ID
    private String membershipLevel;       // ����� ��� (��: Standard, Gold, Platinum)
    private int membershipPoints;         // ���� ����Ʈ

    public MembershipVO() {
    }

    public MembershipVO(int userId, String membershipLevel, int membershipPoints) {
        this.userId = userId;
        this.membershipLevel = membershipLevel;
        this.membershipPoints = membershipPoints;
    }

    // Getter/Setter �޼ҵ��
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getMembershipLevel() {
        return membershipLevel;
    }
    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }
    public int getMembershipPoints() {
        return membershipPoints;
    }
    public void setMembershipPoints(int membershipPoints) {
        this.membershipPoints = membershipPoints;
    }

    @Override
    public String toString() {
        return "MembershipVO{" +
                "userId=" + userId +
                ", membershipLevel='" + membershipLevel + '\'' +
                ", membershipPoints=" + membershipPoints +
                '}';
    }
}
