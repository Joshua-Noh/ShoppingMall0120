package com.shop.ShoppingMall_TeamPrj.events.service;

import com.shop.ShoppingMall_TeamPrj.events.dao.EventDAO;
import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EventServiceImpl�� EventService �������̽��� �����ϸ�,
 * EventDAO�� Ȱ���Ͽ� �̺�Ʈ ���� ����Ͻ� ������ ó���մϴ�.
 */
@Service  // Spring �����̳ʰ� �� Ŭ������ ���� ������ ����մϴ�.
public class EventServiceImpl implements EventService {

    // ������ ���� (DI) - @Autowired�� ���� EventDAO ���� ���Ե˴ϴ�.
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
        // �߰����� ����Ͻ� ����(��ȿ�� ���� ��)�� �߰��� �� �ֽ��ϴ�.
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
