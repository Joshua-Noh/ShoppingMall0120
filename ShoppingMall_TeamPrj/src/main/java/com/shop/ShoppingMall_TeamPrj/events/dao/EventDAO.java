package com.shop.ShoppingMall_TeamPrj.events.dao;

import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;

/**
 * EventDAO 인터페이스는 이벤트 데이터에 대한 CRUD 작업을 정의합니다.
 */
public interface EventDAO {
    /**
     * 이벤트 ID로 이벤트 조회
     * @param eventId 이벤트 고유 ID
     * @return 이벤트 정보를 담은 EventVO 객체
     */
    EventVO getEventById(int eventId);

    /**
     * 모든 이벤트 목록 조회
     * @return EventVO 객체 목록
     */
    List<EventVO> getAllEvents();

    /**
     * 새로운 이벤트 삽입
     * @param event EventVO 객체 (이벤트 데이터)
     * @return 삽입된 행의 개수
     */
    int insertEvent(EventVO event);

    /**
     * 기존 이벤트 업데이트
     * @param event 업데이트할 이벤트 데이터가 담긴 EventVO 객체
     * @return 업데이트된 행의 개수
     */
    int updateEvent(EventVO event);

    /**
     * 이벤트 삭제
     * @param eventId 삭제할 이벤트의 고유 ID
     * @return 삭제된 행의 개수
     */
    int deleteEvent(int eventId);
}
