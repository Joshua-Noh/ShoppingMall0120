package com.shop.ShoppingMall_TeamPrj.membership.vo;

/**
 * MembershipVO는 사용자 멤버십 정보를 캡슐화하는 클래스입니다.
 */
public class MembershipVO {
    private int userId;                   // 사용자 ID
    private String membershipLevel;       // 멤버십 등급 (예: Standard, Gold, Platinum)
    private int membershipPoints;         // 누적 포인트

    public MembershipVO() {
    }

    public MembershipVO(int userId, String membershipLevel, int membershipPoints) {
        this.userId = userId;
        this.membershipLevel = membershipLevel;
        this.membershipPoints = membershipPoints;
    }

    // Getter/Setter 메소드들
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
