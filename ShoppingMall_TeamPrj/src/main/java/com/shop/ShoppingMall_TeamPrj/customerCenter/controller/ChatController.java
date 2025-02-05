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
        System.out.println("ChatController: setChatService 호출됨");
        this.chatService = chatService;
    }
    
    // 채팅 룸 화면 (실시간 채팅 인터페이스)
    @RequestMapping("/room.do")
    public String chatRoom(HttpServletRequest request, Model model) {
        System.out.println("ChatController: chatRoom 메서드 시작");
        
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("ChatController: 세션이 null입니다.");
        } else {
            System.out.println("ChatController: 세션 생성됨");
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("ChatController: 세션에 user_id가 없습니다.");
        } else {
            System.out.println("ChatController: user_id = " + userId);
        }
        
        List<ChatVO> chatHistory = chatService.getChatHistory(userId);
        if (chatHistory == null) {
            System.out.println("ChatController: chatHistory는 null입니다.");
        } else {
            System.out.println("ChatController: chatHistory 크기 = " + chatHistory.size());
        }
        
        model.addAttribute("chatHistory", chatHistory);
        System.out.println("ChatController: chatHistory 모델에 추가 완료");
        return "customerCenter/chat/chatRoom";
    }
    
    // 채팅 메시지 전송 처리 (AJAX 요청 또는 폼 전송)
    @RequestMapping("/send.do")
    public String sendChat(HttpServletRequest request, ChatVO chatVO) {
        System.out.println("ChatController: sendChat 메서드 시작");
        
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("ChatController: 세션이 null입니다.");
        } else {
            System.out.println("ChatController: 세션 생성됨");
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("ChatController: 세션에 user_id가 없습니다.");
        } else {
            System.out.println("ChatController: user_id = " + userId);
        }
        
        chatVO.setUserId(userId);
        System.out.println("ChatController: chatVO에 user_id 세팅 완료, 내용: " + chatVO.getMessage());
        
        chatService.sendChat(chatVO);
        System.out.println("ChatController: chatService.sendChat 호출 완료");
        
        return "redirect:room.do";
    }
}
