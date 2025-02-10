package com.shop.ShoppingMall_TeamPrj.events.vo;

/**
 * EventVO�� �̺�Ʈ ���̺��� �����͸� ĸ��ȭ�ϴ� Value Object Ŭ�����Դϴ�.
 * �ʵ�: eventId, eventName, eventDescription, eventType, startDate, endDate, createdAt.
 */
public class EventVO {
    private int eventId;                     // �̺�Ʈ ���� ID (Primary Key)
    private String eventName;                // �̺�Ʈ �̸� (�ʼ�)
    private String eventDescription;         // �̺�Ʈ ����
    private String eventType;                // �̺�Ʈ ���� ('Discount','BOGO','FlashSale','Others')
    private java.sql.Timestamp startDate;    // �̺�Ʈ ���� ��¥/�ð�
    private java.sql.Timestamp endDate;      // �̺�Ʈ ���� ��¥/�ð�
    private java.sql.Timestamp createdAt;    // �̺�Ʈ ���� �ð�

    // �⺻ ������
    public EventVO() {
    }

    // ��ü �ʵ带 �ʱ�ȭ�ϴ� ������
    public EventVO(int eventId, String eventName, String eventDescription, String eventType, 
                   java.sql.Timestamp startDate, java.sql.Timestamp endDate, java.sql.Timestamp createdAt) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventType = eventType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

    // Getter/Setter �޼ҵ�
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }
    public java.sql.Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "EventVO{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventType='" + eventType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
