package com.shop.ShoppingMall_TeamPrj.customerCenter.dao;

import java.util.List;
import com.shop.ShoppingMall_TeamPrj.customerCenter.vo.ChatVO;

public interface ChatDAO {
    List getChatHistory(Integer userId);
    void insertChat(ChatVO chat);
}
