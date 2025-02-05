package com.shop.ShoppingMall_TeamPrj.customerCenter.controller;

import com.shop.ShoppingMall_TeamPrj.customerCenter.service.FaqService;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.FaqVO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customerCenter/faq")
public class FaqController {
    
    private FaqService faqService;
    
    @Autowired
    public void setFaqService(FaqService faqService) {
        System.out.println("FaqController: setFaqService 호출됨");
        this.faqService = faqService;
    }
    
    // FAQ 목록 조회 (일반 사용자는 FAQ 목록을 볼 수 있음)
    @RequestMapping("/list.do")
    public String listFaq(Model model) {
        System.out.println("FaqController: listFaq 메서드 시작");
        // FAQ 목록을 조회하고 모델에 추가합니다.
        model.addAttribute("faqList", faqService.getAllFaq());
        System.out.println("FaqController: faqList 모델에 추가 완료");
        return "customerCenter/faq/listFaq";
    }
    
    // FAQ 상세보기
    @RequestMapping("/detail.do")
    public String faqDetail(HttpServletRequest request, Model model) {
        System.out.println("FaqController: faqDetail 메서드 시작");
        String faqIdStr = request.getParameter("faqId");
        System.out.println("FaqController: 요청 파라미터 faqId = " + faqIdStr);
        
        int faqId = 0;
        try {
            faqId = Integer.parseInt(faqIdStr);
        } catch (NumberFormatException e) {
            System.out.println("FaqController: faqId 파싱 오류");
            // 필요 시 예외 처리 로직 추가
        }
        
        FaqVO faq = faqService.getFaq(faqId);
        if (faq == null) {
            System.out.println("FaqController: faq 객체가 null입니다.");
        } else {
            System.out.println("FaqController: faq 조회 성공, 질문 = " + faq.getQuestion());
        }
        
        model.addAttribute("faq", faq);
        System.out.println("FaqController: faq 모델에 추가 완료");
        return "customerCenter/faq/faqDetail";
    }
    
    // 추가적인 FAQ 등록/수정/삭제 기능은 관리자 전용으로 별도 구현 가능
}
