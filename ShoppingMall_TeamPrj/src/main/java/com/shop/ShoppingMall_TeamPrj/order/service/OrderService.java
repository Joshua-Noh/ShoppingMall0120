package com.shop.ShoppingMall_TeamPrj.order.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderService {
    // �ֹ� ���� ��ȸ (��: �������������� ���)
    public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception;
    
    // ���� �Ϸ� �� �ֹ� ������ DB�� ����ϴ� �޼���
    public void createOrder(OrderVO orderVO) throws Exception;
}
