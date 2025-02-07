package com.shop.ShoppingMall_TeamPrj.order.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
    @Autowired
    private SqlSession sqlSession;
    
    // 주문 내역 조회 (마이페이지 등에서 사용)
    public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws DataAccessException {
        List<OrderVO> orderGoodsList = new ArrayList<OrderVO>();
        orderGoodsList = sqlSession.selectList("mapper.order.selectMyOrderList", orderVO);
        return orderGoodsList;
    }
    
    // 결제 완료 후 주문 정보를 orders 테이블에 삽입
    public void createOrder(OrderVO orderVO) throws DataAccessException {
        sqlSession.insert("mapper.order.createOrder", orderVO);
    }
}
