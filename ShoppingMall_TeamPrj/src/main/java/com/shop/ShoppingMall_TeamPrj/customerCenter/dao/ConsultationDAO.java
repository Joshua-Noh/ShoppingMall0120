package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;

public interface ConsultationDAO {
    List getAllConsultations();
    List getConsultationsByUser(Integer userId);
    ConsultationVO getConsultation(int consultationId);
    void insertConsultation(ConsultationVO consultation);
    void updateConsultation(ConsultationVO consultation);
    void deleteConsultation(int consultationId);
}
