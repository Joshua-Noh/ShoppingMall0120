package com.shop.ShoppingMall_TeamPrj.membership.dao;

import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.util.List;

/**
 * MembershipTransactionDAO�� ����Ʈ �ŷ� ������ ���� ������ ������ �����մϴ�.
 */
public interface MembershipTransactionDAO {
    List<MembershipTransactionVO> getTransactionsByUserId(int userId);
    int insertTransaction(MembershipTransactionVO transaction);
}
