package com.shop.ShoppingMall_TeamPrj.customerCenter.vo;

import java.sql.Timestamp;

public class ChatVO {
    
    private int chatId;
    private int userId;
    private String sender; // 'USER' �Ǵ� 'SUPPORT'
    private String message;
    private Timestamp sentAt;
    
    // �⺻ ������
    public ChatVO() {
    }
    
    // Getter / Setter �޼���
    public int getChatId() {
        return chatId;
    }
    
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Timestamp getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }
}
