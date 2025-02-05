package com.shop.ShoppingMall_TeamPrj.customerCenter.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.dao.FaqDAO;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.FaqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("faqService")
public class FaqServiceImpl implements FaqService {

    private FaqDAO faqDAO;
    
    @Autowired
    public void setFaqDAO(FaqDAO faqDAO) {
        this.faqDAO = faqDAO;
    }
    
    public List getAllFaq() {
        return faqDAO.getAllFaq();
    }
    
    public FaqVO getFaq(int faqId) {
        return faqDAO.getFaq(faqId);
    }
    
    public void insertFaq(FaqVO faq) {
        faqDAO.insertFaq(faq);
    }
    
    public void updateFaq(FaqVO faq) {
        faqDAO.updateFaq(faq);
    }
    
    public void deleteFaq(int faqId) {
        faqDAO.deleteFaq(faqId);
    }
}
