package com.shop.ShoppingMall_TeamPrj.member.vo;

import java.sql.Date;
import org.springframework.stereotype.Component;

@Component("memberVO")
public class MemberVO {
    private int user_id;            // ����� ID (Primary Key)
    private String user_name;       // ����� �̸�
    private String password;        // ��й�ȣ (��ȣȭ�� ����)
    private String email;           // �̸��� �ּ�
    private String phone_number;    // ����ó ��ȣ
    private String address;         // ��� �ּ�
    private Date date_of_birth;     // �������
    private Date join_date;         // ���� ��¥
    private String gender;          // ���� (Male, Female, Other)
    
    // ===== īī�� �Ҽ� �α��� ���� �ʵ� �߰� =====
    private Long kakaoId;           // īī������ ���� ���� ���̵�
    private String joinType;        // ���� ���� ("kakao" �Ǵ� "normal" ��)
    
    // ���� getter/setter
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
    
    // ===== īī�� �Ҽ� �α��� ���� getter/setter =====
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
}
