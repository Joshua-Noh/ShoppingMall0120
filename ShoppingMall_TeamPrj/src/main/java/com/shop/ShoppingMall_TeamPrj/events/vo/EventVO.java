package com.shop.ShoppingMall_TeamPrj.events.vo;

/**
 * EventVO는 이벤트 테이블의 데이터를 캡슐화하는 Value Object 클래스입니다.
 * 필드: eventId, eventName, eventDescription, eventType, startDate, endDate, createdAt.
 */
public class EventVO {
    private int eventId;                     // 이벤트 고유 ID (Primary Key)
    private String eventName;                // 이벤트 이름 (필수)
    private String eventDescription;         // 이벤트 설명
    private String eventType;                // 이벤트 유형 ('Discount','BOGO','FlashSale','Others')
    private java.sql.Timestamp startDate;    // 이벤트 시작 날짜/시간
    private java.sql.Timestamp endDate;      // 이벤트 종료 날짜/시간
    private java.sql.Timestamp createdAt;    // 이벤트 생성 시간

    // 기본 생성자
    public EventVO() {
    }

    // 전체 필드를 초기화하는 생성자
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

    // Getter/Setter 메소드
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
