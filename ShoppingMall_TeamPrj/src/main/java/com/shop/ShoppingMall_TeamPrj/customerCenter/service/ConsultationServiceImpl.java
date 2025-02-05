package com.shop.ShoppingMall_TeamPrj.customerCenter.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.dao.ConsultationDAO;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("consultationService")
public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationDAO consultationDAO;
    
    @Autowired
    public void setConsultationDAO(ConsultationDAO consultationDAO) {
        this.consultationDAO = consultationDAO;
    }
    
    public List getAllConsultations() {
        return consultationDAO.getAllConsultations();
    }
    
    public List getConsultationsByUser(Integer userId) {
        return consultationDAO.getConsultationsByUser(userId);
    }
    
    public ConsultationVO getConsultation(int consultationId) {
        return consultationDAO.getConsultation(consultationId);
    }
    
    public void submitConsultation(ConsultationVO consultation) {
        consultationDAO.insertConsultation(consultation);
    }
    
    public void updateConsultation(ConsultationVO consultation) {
        consultationDAO.updateConsultation(consultation);
    }
    
    public void deleteConsultation(int consultationId) {
        consultationDAO.deleteConsultation(consultationId);
    }
}
