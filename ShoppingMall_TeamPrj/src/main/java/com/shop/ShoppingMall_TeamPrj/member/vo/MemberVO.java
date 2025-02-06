package com.shop.ShoppingMall_TeamPrj.member.vo;

import java.sql.Date;
import org.springframework.stereotype.Component;

@Component("memberVO")
public class MemberVO {
    private int user_id;            // 사용자 ID (Primary Key)
    private String user_name;       // 사용자 이름
    private String password;        // 비밀번호 (암호화된 형태)
    private String email;           // 이메일 주소
    private String phone_number;    // 연락처 번호
    private String address;         // 배송 주소
    private Date date_of_birth;     // 생년월일
    private Date join_date;         // 가입 날짜
    private String gender;          // 성별
    private String role;            // 사용자 역할 (예: admin, user 등)
    
    // ===== 카카오 소셜 로그인 관련 필드 추가 =====
    private Long kakaoId;           // 카카오에서 받은 고유 아이디
    private String joinType;        // 가입 유형 ("kakao" 또는 "normal" 등)
    
    // ===== 카카오 액세스 토큰 추가 =====
    private String accessToken;     // 카카오 로그인 후 발급받은 액세스 토큰

    // 기존 getter/setter
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getDate_of_birth() {
        return date_of_birth;
    }
    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    public Date getJoin_date() {
        return join_date;
    }
    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    // ===== 카카오 소셜 로그인 관련 getter/setter =====
    public Long getKakaoId() {
        return kakaoId;
    }
    public void setKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }
    public String getJoinType() {
        return joinType;
    }
    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }
    
    // ===== 카카오 액세스 토큰 getter/setter =====
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
