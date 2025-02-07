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

    // 장바구니 목록 조회 및 관련 상품 정보 조회
    public Map myCartList(CartVO cartVO) throws Exception {
        Map cartMap = new HashMap();
        List myCartList = cartDAO.selectCartList(cartVO);
        System.out.println("조회된 장바구니 상품 수: " + myCartList.size());
        if (myCartList.size() == 0) {
            System.out.println("장바구니가 비어 있습니다.");
            cartMap.put("myCartList", new ArrayList());
            cartMap.put("myGoodsList", new ArrayList());
            return cartMap;
        }
        List productIds = new ArrayList();
        for (Object cartItem : myCartList) {
            productIds.add(((CartVO) cartItem).getProduct_id());
        }
        List myGoodsList = cartDAO.selectGoodsList(productIds);
        System.out.println("조회된 상품 수: " + myGoodsList.size());
        cartMap.put("myCartList", myCartList);
        cartMap.put("myGoodsList", myGoodsList);
        System.out.println("cartMap에 담긴 데이터 확인: " + cartMap);
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
    
    // 주문 완료 후, 사용자의 모든 장바구니 항목 삭제
    @Override
    public void clearCartForUser(int user_id) throws Exception {
        cartDAO.clearCartForUser(user_id);
    }
}
