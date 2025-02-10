package com.shop.ShoppingMall_TeamPrj.events.service;

import com.shop.ShoppingMall_TeamPrj.events.dao.EventDAO;
import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EventServiceImpl은 EventService 인터페이스를 구현하며,
 * EventDAO를 활용하여 이벤트 관련 비즈니스 로직을 처리합니다.
 */
@Service  // Spring 컨테이너가 이 클래스를 서비스 빈으로 등록합니다.
public class EventServiceImpl implements EventService {

    // 의존성 주입 (DI) - @Autowired를 통해 EventDAO 빈이 주입됩니다.
    @Autowired
    private EventDAO eventDAO;

    @Override
    public EventVO getEventById(int eventId) {
        return eventDAO.getEventById(eventId);
    }

    @Override
    public List<EventVO> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public int registerEvent(EventVO event) {
        // 추가적인 비즈니스 로직(유효성 검증 등)을 추가할 수 있습니다.
        return eventDAO.insertEvent(event);
    }

    @Override
    public int updateEvent(EventVO event) {
        return eventDAO.updateEvent(event);
    }

    @Override
    public int removeEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }
}
