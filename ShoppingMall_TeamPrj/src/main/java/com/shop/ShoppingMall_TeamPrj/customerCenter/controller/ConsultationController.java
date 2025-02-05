package com.shop.ShoppingMall_TeamPrj.customerCenter.controller;

import com.shop.ShoppingMall_TeamPrj.customerCenter.service.ConsultationService;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ConsultationVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customerCenter/consultation")
public class ConsultationController {
    
    private ConsultationService consultationService;
    
    @Autowired
    public void setConsultationService(ConsultationService consultationService) {
        System.out.println("ConsultationController: setConsultationService ȣ���");
        this.consultationService = consultationService;
    }
    
    // ��� ��� ��ȸ (�Ϲ� ����ڴ� �ڽ��� �Խñ�, ������(ADMIN)�� ��ü �Խñ�)
    @RequestMapping("/list.do")
    public String listConsultations(HttpServletRequest request, Model model) {
        System.out.println("ConsultationController: listConsultations �޼��� ����");
        HttpSession session = request.getSession();
        
        if (session == null) {
            System.out.println("ConsultationController: ������ �����ϴ�.");
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        String role = (String) session.getAttribute("role");
        System.out.println("ConsultationController: user_id = " + userId + ", role = " + role);
        
        if ("ADMIN".equals(role)) {
            System.out.println("ConsultationController: ������ ���, ��ü ��� ��ȸ");
            model.addAttribute("consultationList", consultationService.getAllConsultations());
        } else {
            System.out.println("ConsultationController: �Ϲ� ����� ���, ���� ��� ��ȸ");
            model.addAttribute("consultationList", consultationService.getConsultationsByUser(userId));
        }
        System.out.println("ConsultationController: ��� ��� �𵨿� �߰� �Ϸ�");
        return "customerCenter/consultation/listConsultations";
    }
    
    // ��� �ۼ� �� ǥ��
    @RequestMapping("/form.do")
    public String showConsultationForm() {
        System.out.println("ConsultationController: showConsultationForm ȣ���");
        return "customerCenter/consultation/consultationForm";
    }
    
 // ��� ���� ���� ó��
    @RequestMapping(value="/submit.do", method=RequestMethod.POST)
    public String submitConsultation(HttpServletRequest request, ConsultationVO consultationVO) {
        System.out.println("ConsultationController: submitConsultation �޼��� ����");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id"); 
       
        if (userId == null) {
            System.out.println("ConsultationController: ���ǿ� user_id�� �����ϴ�. �α��� �� �̿��� �ּ���.");
            // �α������� ���� ��� ��� �������� �����̷�Ʈ�ϸ�, ���� �Ķ���� error=loginRequired ����
            return "redirect:list.do?error=loginRequired";
        }
        
        System.out.println("ConsultationController: session user_id = " + userId);
        System.out.println("ConsultationController: ��� ���� ���� = " + consultationVO.getSubject());
        consultationVO.setUserId(userId);
        consultationService.submitConsultation(consultationVO);
        System.out.println("ConsultationController: ��� ���� ���� �Ϸ�");
        return "redirect:list.do";
    }


    
    // ��� �󼼺���
    @RequestMapping("/detail.do")
    public String consultationDetail(HttpServletRequest request, Model model) {
        System.out.println("ConsultationController: consultationDetail �޼��� ����");
        String consultationIdStr = request.getParameter("consultationId");
        System.out.println("ConsultationController: ��û �Ķ���� consultationId = " + consultationIdStr);
        
        int consultationId = 0;
        try {
            consultationId = Integer.parseInt(consultationIdStr);
        } catch (NumberFormatException e) {
            System.out.println("ConsultationController: consultationId �Ľ� ����");
            // �ʿ�� ���� ó�� ���� �߰�
        }
        
        ConsultationVO consultation = consultationService.getConsultation(consultationId);
        if (consultation == null) {
            System.out.println("ConsultationController: ��� ������ ã�� �� �����ϴ�.");
        } else {
            System.out.println("ConsultationController: ��� ���� ��ȸ��, ���� = " + consultation.getSubject());
        }
        
        model.addAttribute("consultation", consultation);
        return "customerCenter/consultation/consultationDetail";
    }
}
