package com.shop.ShoppingMall_TeamPrj.order.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.shop.ShoppingMall_TeamPrj.order.dao.OrderDAO;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO orderDAO;
    
    public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception {
        return orderDAO.listMyOrderGoods(orderVO);
    }
    
    // ���� �Ϸ� ��, ��Ʈ�ѷ����� ���޹��� �ֹ� ����(OrderVO)�� orders ���̺� ���
    public void createOrder(OrderVO orderVO) throws Exception {
        orderDAO.createOrder(orderVO);
    }
}
