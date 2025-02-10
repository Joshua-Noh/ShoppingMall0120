package com.shop.ShoppingMall_TeamPrj.membership.service;

import com.shop.ShoppingMall_TeamPrj.membership.dao.MembershipDAO;
import com.shop.ShoppingMall_TeamPrj.membership.dao.MembershipLevelDAO;
import com.shop.ShoppingMall_TeamPrj.membership.dao.MembershipTransactionDAO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipLevelVO;
import com.shop.ShoppingMall_TeamPrj.membership.vo.MembershipTransactionVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MembershipServiceImpl�� MembershipService�� �����Ͽ� ����� ���� ����Ͻ� ������ ó���մϴ�.
 */
@Service  // �� Ŭ������ Spring�� ���� ������ ����մϴ�.
public class MembershipServiceImpl implements MembershipService {

    @Autowired  // Spring �����̳ʰ� �ڵ����� �����մϴ�.
    private MembershipDAO membershipDAO;

    @Autowired
    private MembershipLevelDAO membershipLevelDAO;

    @Autowired
    private MembershipTransactionDAO membershipTransactionDAO;

    @Override
    public MembershipVO getMembershipByUserId(int userId) {
        return membershipDAO.getMembershipByUserId(userId);
    }

    @Override
    public int saveMembership(MembershipVO membership) {
        MembershipVO existing = membershipDAO.getMembershipByUserId(membership.getUserId());
        if(existing == null) {
            return membershipDAO.insertMembership(membership);
        } else {
            return membershipDAO.updateMembership(membership);
        }
    }

    @Override
    public List<MembershipLevelVO> getAllMembershipLevels() {
        return membershipLevelDAO.getAllMembershipLevels();
    }

    @Override
    public MembershipLevelVO getMembershipLevel(String membershipLevel) {
        return membershipLevelDAO.getMembershipLevel(membershipLevel);
    }

    @Override
    public int registerTransaction(MembershipTransactionVO transaction) {
        return membershipTransactionDAO.insertTransaction(transaction);
    }

    @Override
    public List<MembershipTransactionVO> getTransactionsByUserId(int userId) {
        return membershipTransactionDAO.getTransactionsByUserId(userId);
    }
}
