package com.shop.ShoppingMall_TeamPrj.events.service;

import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;

/**
 * EventService 인터페이스는 이벤트 관련 비즈니스 로직을 정의합니다.
 */
public interface EventService {
    /**
     * 이벤트 ID로 이벤트 조회
     */
    EventVO getEventById(int eventId);

    /**
     * 모든 이벤트 목록 조회
     */
    List<EventVO> getAllEvents();

    /**
     * 새로운 이벤트 등록
     */
    int registerEvent(EventVO event);

    /**
     * 이벤트 업데이트
     */
    int updateEvent(EventVO event);

    /**
     * 이벤트 삭제
     */
    int removeEvent(int eventId);
}
