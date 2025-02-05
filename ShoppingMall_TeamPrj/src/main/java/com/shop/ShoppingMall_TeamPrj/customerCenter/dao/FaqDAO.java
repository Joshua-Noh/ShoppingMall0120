package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.FaqVO;

public interface FaqDAO {
    List getAllFaq();
    FaqVO getFaq(int faqId);
    void insertFaq(FaqVO faq);
    void updateFaq(FaqVO faq);
    void deleteFaq(int faqId);
}
