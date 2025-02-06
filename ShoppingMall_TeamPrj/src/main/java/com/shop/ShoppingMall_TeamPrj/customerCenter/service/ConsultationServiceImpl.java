package com.shop.ShoppingMall_TeamPrj.customerCenter.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.dao.ConsultationDAO;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("consultationService")
public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationDAO consultationDAO;
    
    @Autowired
    public void setConsultationDAO(ConsultationDAO consultationDAO) {
        this.consultationDAO = consultationDAO;
    }
    
    public List getAllConsultations() {
        List<ConsultationVO> list = consultationDAO.getAllConsultations();
        System.out.println("[DEBUG] getAllConsultations() - 조회된 상담 개수: " + list.size());
        
        for (ConsultationVO vo : list) {
            System.out.println("[DEBUG] 상담 데이터: " + vo.getConsultationId() + ", " + vo.getSubject() + ", " + vo.getMessage());
        }
        return list;
    }

    
    public List getConsultationsByUser(Integer userId) {
        return consultationDAO.getConsultationsByUser(userId);
    }
    
    public ConsultationVO getConsultation(int consultationId) {
        return consultationDAO.getConsultation(consultationId);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void submitConsultation(ConsultationVO consultationVO) {
        try {
            consultationDAO.insertConsultation(consultationVO);
        } catch (Exception e) {
            System.out.println("[ERROR] 트랜잭션 롤백 발생: " + e.getMessage());
        }
    }


    
    public void updateConsultation(ConsultationVO consultation) {
        consultationDAO.updateConsultation(consultation);
    }
    
    public void deleteConsultation(int consultationId) {
        consultationDAO.deleteConsultation(consultationId);
    }
}
