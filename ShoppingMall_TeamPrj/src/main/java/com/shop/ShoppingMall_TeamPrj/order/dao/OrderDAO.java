package com.shop.ShoppingMall_TeamPrj.order.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

public interface OrderDAO {
    // ����ڰ� �ֹ� ������ ��ȸ�� �� ���
    public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws DataAccessException;
    
    // ���� �Ϸ� �� �ֹ� ������ orders ���̺� ����ϴ� �޼���
    public void createOrder(OrderVO orderVO) throws DataAccessException;
}
//	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException;
//	public void insertOrderSequence() throws DataAccessException;
//	public OrderVO findMyOrder(String order_id) throws DataAccessException;
//	public void removeGoodsFromCart(List<OrderVO> myOrderList)throws DataAccessException;

