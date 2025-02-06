package com.shop.ShoppingMall_TeamPrj.customerCenter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customerCenter/chat")
public class ChatController {
    
    // ä�÷� ȭ�� (ChannelIO SDK�� ���� ä�� �������̽�)
    @RequestMapping(value="/room.do", method=RequestMethod.GET)
    public String chatRoom(HttpServletRequest request, Model model) {
        System.out.println("ChatController: chatRoom �޼��� ����");
        
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("ChatController: ������ null�Դϴ�.");
        } else {
            System.out.println("ChatController: ���� ������");
        }
        
        // ���ǿ��� ����� ���̵� �����ɴϴ�.
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("ChatController: ���ǿ� user_id�� �����ϴ�.");
            // �͸� ������� ��� �� ���ڿ��� �����մϴ�.
            model.addAttribute("memberId", "");
        } else {
            System.out.println("ChatController: user_id = " + userId);
            model.addAttribute("memberId", userId);
        }
        
        // ChannelIO �÷����� Ű (ChannelIO���� �߱޹��� ���� Ű�� ����)
        model.addAttribute("pluginKey", "YOUR_PLUGIN_KEY");
        
        return "customerCenter/chat/chatRoom"; // /WEB-INF/views/customerCenter/chat/chatRoom.jsp
    }
}
