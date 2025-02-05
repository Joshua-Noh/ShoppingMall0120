package com.shop.ShoppingMall_TeamPrj.customerCenter.controller;

import com.shop.ShoppingMall_TeamPrj.customerCenter.service.ChatService;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ChatVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customerCenter/chat")
public class ChatController {
    
    private ChatService chatService;
    
    @Autowired
    public void setChatService(ChatService chatService) {
        System.out.println("ChatController: setChatService ȣ���");
        this.chatService = chatService;
    }
    
    // ä�� �� ȭ�� (�ǽð� ä�� �������̽�)
    @RequestMapping("/room.do")
    public String chatRoom(HttpServletRequest request, Model model) {
        System.out.println("ChatController: chatRoom �޼��� ����");
        
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("ChatController: ������ null�Դϴ�.");
        } else {
            System.out.println("ChatController: ���� ������");
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("ChatController: ���ǿ� user_id�� �����ϴ�.");
        } else {
            System.out.println("ChatController: user_id = " + userId);
        }
        
        List<ChatVO> chatHistory = chatService.getChatHistory(userId);
        if (chatHistory == null) {
            System.out.println("ChatController: chatHistory�� null�Դϴ�.");
        } else {
            System.out.println("ChatController: chatHistory ũ�� = " + chatHistory.size());
        }
        
        model.addAttribute("chatHistory", chatHistory);
        System.out.println("ChatController: chatHistory �𵨿� �߰� �Ϸ�");
        return "customerCenter/chat/chatRoom";
    }
    
    // ä�� �޽��� ���� ó�� (AJAX ��û �Ǵ� �� ����)
    @RequestMapping("/send.do")
    public String sendChat(HttpServletRequest request, ChatVO chatVO) {
        System.out.println("ChatController: sendChat �޼��� ����");
        
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("ChatController: ������ null�Դϴ�.");
        } else {
            System.out.println("ChatController: ���� ������");
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("ChatController: ���ǿ� user_id�� �����ϴ�.");
        } else {
            System.out.println("ChatController: user_id = " + userId);
        }
        
        chatVO.setUserId(userId);
        System.out.println("ChatController: chatVO�� user_id ���� �Ϸ�, ����: " + chatVO.getMessage());
        
        chatService.sendChat(chatVO);
        System.out.println("ChatController: chatService.sendChat ȣ�� �Ϸ�");
        
        return "redirect:room.do";
    }
}
