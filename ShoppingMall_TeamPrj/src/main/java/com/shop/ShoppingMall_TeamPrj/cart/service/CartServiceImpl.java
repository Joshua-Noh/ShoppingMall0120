package com.shop.ShoppingMall_TeamPrj.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.shop.ShoppingMall_TeamPrj.cart.dao.CartDAO;
import com.shop.ShoppingMall_TeamPrj.cart.vo.CartVO;

@Service("cartService")
@Transactional(propagation=Propagation.REQUIRED)
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO;

    // ��ٱ��� ��� ��ȸ �� ���� ��ǰ ���� ��ȸ
    public Map myCartList(CartVO cartVO) throws Exception {
        Map cartMap = new HashMap();
        List myCartList = cartDAO.selectCartList(cartVO);
        System.out.println("��ȸ�� ��ٱ��� ��ǰ ��: " + myCartList.size());
        if (myCartList.size() == 0) {
            System.out.println("��ٱ��ϰ� ��� �ֽ��ϴ�.");
            cartMap.put("myCartList", new ArrayList());
            cartMap.put("myGoodsList", new ArrayList());
            return cartMap;
        }
        List productIds = new ArrayList();
        for (Object cartItem : myCartList) {
            productIds.add(((CartVO) cartItem).getProduct_id());
        }
        List myGoodsList = cartDAO.selectGoodsList(productIds);
        System.out.println("��ȸ�� ��ǰ ��: " + myGoodsList.size());
        cartMap.put("myCartList", myCartList);
        cartMap.put("myGoodsList", myGoodsList);
        System.out.println("cartMap�� ��� ������ Ȯ��: " + cartMap);
        System.out.println("productIds: " + productIds);
        return cartMap;
    }

    @Override
    public CartVO findCartItem(CartVO cartVO) throws Exception {
        return cartDAO.selectCartItem(cartVO);
    }

    public void addGoodsInCart(CartVO cartVO) throws Exception {
        cartDAO.insertGoodsInCart(cartVO);
    }
    
    @Override
    public void deleteCartItem(int cart_id) throws Exception {
        cartDAO.deleteCartItem(cart_id);
    }
    
    @Override
    public void updateCartQuantity(CartVO cartVO) throws Exception {
        System.out.println("Updating quantity for cart ID: " + cartVO.getCart_id() + " to quantity: " + cartVO.getQuantity());
        cartDAO.updateCartQuantity(cartVO);
        System.out.println("Successfully updated quantity for cart ID: " + cartVO.getCart_id());
    }
    
    // �ֹ� �Ϸ� ��, ������� ��� ��ٱ��� �׸� ����
    @Override
    public void clearCartForUser(int user_id) throws Exception {
        cartDAO.clearCartForUser(user_id);
    }
}
