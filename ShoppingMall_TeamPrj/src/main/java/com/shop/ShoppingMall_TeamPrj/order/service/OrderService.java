package com.shop.ShoppingMall_TeamPrj.order.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderService {
    // 주문 내역 조회 (예: 마이페이지에서 사용)
    public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception;
    
    // 결제 완료 후 주문 정보를 DB에 기록하는 메서드
    public void createOrder(OrderVO orderVO) throws Exception;
}
