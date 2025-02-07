package com.shop.ShoppingMall_TeamPrj.order.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderDAO {
    // 사용자가 주문 내역을 조회할 때 사용
    public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws DataAccessException;
    
    // 결제 완료 후 주문 정보를 orders 테이블에 기록하는 메서드
    public void createOrder(OrderVO orderVO) throws DataAccessException;
}
//	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException;
//	public void insertOrderSequence() throws DataAccessException;
//	public OrderVO findMyOrder(String order_id) throws DataAccessException;
//	public void removeGoodsFromCart(List<OrderVO> myOrderList)throws DataAccessException;

