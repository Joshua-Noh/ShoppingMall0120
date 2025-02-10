package com.shop.ShoppingMall_TeamPrj.membership.vo;

/**
 * MembershipLevelVO�� �� ����� ��޿� ���� ������ ����ϴ�.
 */
public class MembershipLevelVO {
    private String membershipLevel;   // ��� �̸� (��: Standard, Gold, Platinum)
    private double discountRate;      // ��޺� ������
    private int requiredPoints;       // �ش� ������� �±��ϱ� ���� ����Ʈ

    public MembershipLevelVO() {
    }

    public MembershipLevelVO(String membershipLevel, double discountRate, int requiredPoints) {
        this.membershipLevel = membershipLevel;
        this.discountRate = discountRate;
        this.requiredPoints = requiredPoints;
    }

    // Getter/Setter �޼ҵ��
    public String getMembershipLevel() {
        return membershipLevel;
    }
    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }
    public double getDiscountRate() {
        return discountRate;
    }
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
    public int getRequiredPoints() {
        return requiredPoints;
    }
    public void setRequiredPoints(int requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    @Override
    public String toString() {
        return "MembershipLevelVO{" +
                "membershipLevel='" + membershipLevel + '\'' +
                ", discountRate=" + discountRate +
                ", requiredPoints=" + requiredPoints +
                '}';
    }
}
