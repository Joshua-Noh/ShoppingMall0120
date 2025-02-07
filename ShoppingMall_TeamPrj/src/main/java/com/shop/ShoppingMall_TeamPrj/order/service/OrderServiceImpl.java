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
    
    // 결제 완료 후, 컨트롤러에서 전달받은 주문 정보(OrderVO)를 orders 테이블에 기록
    public void createOrder(OrderVO orderVO) throws Exception {
        orderDAO.createOrder(orderVO);
    }
}
