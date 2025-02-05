package com.shop.ShoppingMall_TeamPrj.customerCenter.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;

public interface ConsultationService {
    List getAllConsultations();
    List getConsultationsByUser(Integer userId);
    ConsultationVO getConsultation(int consultationId);
    void submitConsultation(ConsultationVO consultation);
    void updateConsultation(ConsultationVO consultation);
    void deleteConsultation(int consultationId);
}
