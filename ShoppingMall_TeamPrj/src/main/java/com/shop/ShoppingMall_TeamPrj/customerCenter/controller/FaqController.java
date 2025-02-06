package com.shop.ShoppingMall_TeamPrj.customerCenter.controller;

import com.shop.ShoppingMall_TeamPrj.customerCenter.service.FaqService;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.FaqVO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customerCenter/faq")
public class FaqController {
    
    private FaqService faqService;
    
    @Autowired
    public void setFaqService(FaqService faqService) {
        System.out.println("FaqController: setFaqService ȣ���");
        this.faqService = faqService;
    }
    
    // FAQ ��� ��ȸ (�Ϲ� ����ڴ� FAQ ����� �� �� ����)
    @RequestMapping("/list.do")
    public String listFaq(Model model) {
        System.out.println("FaqController: listFaq �޼��� ����");
        model.addAttribute("faqList", faqService.getAllFaq());
        System.out.println("FaqController: faqList �𵨿� �߰� �Ϸ�");
        return "customerCenter/faq/listFaq";
    }
    
    // FAQ �󼼺���
    @RequestMapping("/detail.do")
    public String faqDetail(HttpServletRequest request, Model model) {
        System.out.println("FaqController: faqDetail �޼��� ����");
        String faqIdStr = request.getParameter("faqId");
        System.out.println("FaqController: ��û �Ķ���� faqId = " + faqIdStr);
        
        int faqId = 0;
        try {
            faqId = Integer.parseInt(faqIdStr);
        } catch (NumberFormatException e) {
            System.out.println("FaqController: faqId �Ľ� ����");
            // �ʿ� �� ���� ó�� ���� �߰�
        }
        
        FaqVO faq = faqService.getFaq(faqId);
        if (faq == null) {
            System.out.println("FaqController: faq ��ü�� null�Դϴ�.");
        } else {
            System.out.println("FaqController: faq ��ȸ ����, ���� = " + faq.getQuestion());
        }
        
        model.addAttribute("faq", faq);
        System.out.println("FaqController: faq �𵨿� �߰� �Ϸ�");
        return "customerCenter/faq/faqDetail";
    }
    
    // FAQ �ۼ� �� ǥ�� (GET ��û)
    @RequestMapping(value="/form.do", method=RequestMethod.GET)
    public String showFaqForm(Model model) {
        System.out.println("FaqController: showFaqForm �޼��� ȣ���");
        // �ű� FAQ �ۼ��� ���� �� FaqVO ��ü�� �𵨿� �߰�
        model.addAttribute("faq", new FaqVO());
        return "customerCenter/faq/faqForm";
    }
    
    // FAQ ��� ó�� (POST ��û)
    @RequestMapping(value="/form.do", method=RequestMethod.POST)
    public String submitFaqForm(FaqVO faq) {
        System.out.println("FaqController: submitFaqForm �޼��� ����");
        faqService.insertFaq(faq);
        System.out.println("FaqController: FAQ ��� �Ϸ�");
        return "redirect:list.do";
    }
    
    // �߰����� FAQ ����/���� ����� ������ �������� ���� ���� ����
}
