package com.shop.ShoppingMall_TeamPrj.customerCenter.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.dao.ChatDAO;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ChatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

    private ChatDAO chatDAO;
    
    @Autowired
    public void setChatDAO(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
    
    public List getChatHistory(Integer userId) {
        return chatDAO.getChatHistory(userId);
    }
    
    public void sendChat(ChatVO chat) {
        chatDAO.insertChat(chat);
    }
}
