package com.shop.ShoppingMall_TeamPrj.events.dao;

import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;

/**
 * EventDAO �������̽��� �̺�Ʈ �����Ϳ� ���� CRUD �۾��� �����մϴ�.
 */
public interface EventDAO {
    /**
     * �̺�Ʈ ID�� �̺�Ʈ ��ȸ
     * @param eventId �̺�Ʈ ���� ID
     * @return �̺�Ʈ ������ ���� EventVO ��ü
     */
    EventVO getEventById(int eventId);

    /**
     * ��� �̺�Ʈ ��� ��ȸ
     * @return EventVO ��ü ���
     */
    List<EventVO> getAllEvents();

    /**
     * ���ο� �̺�Ʈ ����
     * @param event EventVO ��ü (�̺�Ʈ ������)
     * @return ���Ե� ���� ����
     */
    int insertEvent(EventVO event);

    /**
     * ���� �̺�Ʈ ������Ʈ
     * @param event ������Ʈ�� �̺�Ʈ �����Ͱ� ��� EventVO ��ü
     * @return ������Ʈ�� ���� ����
     */
    int updateEvent(EventVO event);

    /**
     * �̺�Ʈ ����
     * @param eventId ������ �̺�Ʈ�� ���� ID
     * @return ������ ���� ����
     */
    int deleteEvent(int eventId);
}
