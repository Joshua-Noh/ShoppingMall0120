package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.util.List;

/**
 * MembershipTransactionDAO는 포인트 거래 내역에 대한 데이터 접근을 정의합니다.
 */
public interface MembershipTransactionDAO {
    List<MembershipTransactionVO> getTransactionsByUserId(int userId);
    int insertTransaction(MembershipTransactionVO transaction);
}
