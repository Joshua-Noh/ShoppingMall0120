package com.shop.ShoppingMall_TeamPrj.customerCenter.vo;

import java.sql.Timestamp;

public class FaqVO {
    
    private int faqId;
    private String question;
    private String answer;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // �⺻ ������
    public FaqVO() {
    }
    
    // Getter / Setter �޼���
    public int getFaqId() {
        return faqId;
    }
    
    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
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
