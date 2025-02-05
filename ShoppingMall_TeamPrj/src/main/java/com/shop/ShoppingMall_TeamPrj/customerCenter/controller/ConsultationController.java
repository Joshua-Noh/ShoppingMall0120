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
        System.out.println("ConsultationController: setConsultationService 호출됨");
        this.consultationService = consultationService;
    }
    
    // 상담 목록 조회 (일반 사용자는 자신의 게시글, 관리자(ADMIN)는 전체 게시글)
    @RequestMapping("/list.do")
    public String listConsultations(HttpServletRequest request, Model model) {
        System.out.println("ConsultationController: listConsultations 메서드 시작");
        HttpSession session = request.getSession();
        
        if (session == null) {
            System.out.println("ConsultationController: 세션이 없습니다.");
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        String role = (String) session.getAttribute("role");
        System.out.println("ConsultationController: user_id = " + userId + ", role = " + role);
        
        if ("ADMIN".equals(role)) {
            System.out.println("ConsultationController: 관리자 모드, 전체 상담 조회");
            model.addAttribute("consultationList", consultationService.getAllConsultations());
        } else {
            System.out.println("ConsultationController: 일반 사용자 모드, 본인 상담 조회");
            model.addAttribute("consultationList", consultationService.getConsultationsByUser(userId));
        }
        System.out.println("ConsultationController: 상담 목록 모델에 추가 완료");
        return "customerCenter/consultation/listConsultations";
    }
    
    // 상담 작성 폼 표시
    @RequestMapping("/form.do")
    public String showConsultationForm() {
        System.out.println("ConsultationController: showConsultationForm 호출됨");
        return "customerCenter/consultation/consultationForm";
    }
    
 // 상담 문의 제출 처리
    @RequestMapping(value="/submit.do", method=RequestMethod.POST)
    public String submitConsultation(HttpServletRequest request, ConsultationVO consultationVO) {
        System.out.println("ConsultationController: submitConsultation 메서드 시작");

        // 세션이 없으면 null을 반환하도록 getSession(false) 사용
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("ConsultationController: 세션 자체가 존재하지 않습니다.");
            return "redirect:list.do?error=sessionNotFound";
        }
        
        // 세션에 저장된 모든 속성을 출력해봅니다.
        System.out.println("ConsultationController: 세션에 저장된 속성 목록:");
        java.util.Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attrName = attributeNames.nextElement();
            Object attrValue = session.getAttribute(attrName);
            System.out.println("  - " + attrName + " = " + attrValue);
        }
        
        // 필요한 속성을 개별적으로 꺼내 디버그 출력
        Integer userId = (Integer) session.getAttribute("user_id");
        String role = (String) session.getAttribute("role");
        
        if (userId == null) {
            System.out.println("ConsultationController: 세션에 user_id가 없습니다. 로그인 후 이용해 주세요.");
            return "redirect:list.do?error=loginRequired";
        }
        
        System.out.println("ConsultationController: session user_id = " + userId + ", role = " + role);
        System.out.println("ConsultationController: 상담 문의 제목 = " + consultationVO.getSubject());
        
        // 상담VO에 세션에서 가져온 userId 설정
        consultationVO.setUserId(userId);
        
        // 실제 상담 문의 제출
        consultationService.submitConsultation(consultationVO);
        System.out.println("ConsultationController: 상담 문의 제출 완료");
        
        return "redirect:list.do";
    }



    
    // 상담 상세보기
    @RequestMapping("/detail.do")
    public String consultationDetail(HttpServletRequest request, Model model) {
        System.out.println("ConsultationController: consultationDetail 메서드 시작");
        String consultationIdStr = request.getParameter("consultationId");
        System.out.println("ConsultationController: 요청 파라미터 consultationId = " + consultationIdStr);
        
        int consultationId = 0;
        try {
            consultationId = Integer.parseInt(consultationIdStr);
        } catch (NumberFormatException e) {
            System.out.println("ConsultationController: consultationId 파싱 오류");
            // 필요시 예외 처리 로직 추가
        }
        
        ConsultationVO consultation = consultationService.getConsultation(consultationId);
        if (consultation == null) {
            System.out.println("ConsultationController: 상담 내역을 찾을 수 없습니다.");
        } else {
            System.out.println("ConsultationController: 상담 내역 조회됨, 제목 = " + consultation.getSubject());
        }
        
        model.addAttribute("consultation", consultation);
        return "customerCenter/consultation/consultationDetail";
    }
}
