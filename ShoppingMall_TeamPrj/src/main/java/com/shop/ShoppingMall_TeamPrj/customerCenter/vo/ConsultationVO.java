package com.shop.ShoppingMall_TeamPrj.customerCenter.vo;

import java.sql.Timestamp;

public class ConsultationVO {
    
    private int consultationId;
    private int userId;
    private String subject;
    private String message;
    private String reply;
    private String status; // 'Pending', 'Answered', 'Closed'
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // 기본 생성자
    public ConsultationVO() {
    }
    
    // Getter / Setter 메서드
    public int getConsultationId() {
        return consultationId;
    }
    
    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getReply() {
        return reply;
    }
    
    public void setReply(String reply) {
        this.reply = reply;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
