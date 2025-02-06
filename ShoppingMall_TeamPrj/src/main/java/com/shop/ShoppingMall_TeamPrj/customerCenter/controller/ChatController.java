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
    
    // 채팅룸 화면 (ChannelIO SDK를 통한 채팅 인터페이스)
    @RequestMapping(value="/room.do", method=RequestMethod.GET)
    public String chatRoom(HttpServletRequest request, Model model) {
        System.out.println("ChatController: chatRoom 메서드 시작");
        
        HttpSession session = request.getSession();
        if (session == null) {
            System.out.println("ChatController: 세션이 null입니다.");
        } else {
            System.out.println("ChatController: 세션 생성됨");
        }
        
        // 세션에서 사용자 아이디를 가져옵니다.
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("ChatController: 세션에 user_id가 없습니다.");
            // 익명 사용자일 경우 빈 문자열로 설정합니다.
            model.addAttribute("memberId", "");
        } else {
            System.out.println("ChatController: user_id = " + userId);
            model.addAttribute("memberId", userId);
        }
        
        // ChannelIO 플러그인 키 (ChannelIO에서 발급받은 실제 키로 변경)
        model.addAttribute("pluginKey", "YOUR_PLUGIN_KEY");
        
        return "customerCenter/chat/chatRoom"; // /WEB-INF/views/customerCenter/chat/chatRoom.jsp
    }
}
