package com.shop.ShoppingMall_TeamPrj.events.service;

import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;

/**
 * EventService �������̽��� �̺�Ʈ ���� ����Ͻ� ������ �����մϴ�.
 */
public interface EventService {
    /**
     * �̺�Ʈ ID�� �̺�Ʈ ��ȸ
     */
    EventVO getEventById(int eventId);

    /**
     * ��� �̺�Ʈ ��� ��ȸ
     */
    List<EventVO> getAllEvents();

    /**
     * ���ο� �̺�Ʈ ���
     */
    int registerEvent(EventVO event);

    /**
     * �̺�Ʈ ������Ʈ
     */
    int updateEvent(EventVO event);

    /**
     * �̺�Ʈ ����
     */
    int removeEvent(int eventId);
}
