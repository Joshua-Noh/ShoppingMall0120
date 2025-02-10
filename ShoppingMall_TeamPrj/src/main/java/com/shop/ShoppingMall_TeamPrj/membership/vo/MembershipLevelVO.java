package com.shop.ShoppingMall_TeamPrj.membership.vo;

/**
 * MembershipLevelVO는 각 멤버십 등급에 대한 정보를 담습니다.
 */
public class MembershipLevelVO {
    private String membershipLevel;   // 등급 이름 (예: Standard, Gold, Platinum)
    private double discountRate;      // 등급별 할인율
    private int requiredPoints;       // 해당 등급으로 승급하기 위한 포인트

    public MembershipLevelVO() {
    }

    public MembershipLevelVO(String membershipLevel, double discountRate, int requiredPoints) {
        this.membershipLevel = membershipLevel;
        this.discountRate = discountRate;
        this.requiredPoints = requiredPoints;
    }

    // Getter/Setter 메소드들
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
