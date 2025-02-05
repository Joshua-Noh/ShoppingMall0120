package com.shop.ShoppingMall_TeamPrj.customerCenter.service;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ChatVO;

public interface ChatService {
    List getChatHistory(Integer userId);
    void sendChat(ChatVO chat);
}
