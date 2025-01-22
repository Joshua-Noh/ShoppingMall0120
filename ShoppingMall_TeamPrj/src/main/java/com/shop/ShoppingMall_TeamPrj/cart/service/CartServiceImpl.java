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
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

@Service("cartService")
@Transactional(propagation=Propagation.REQUIRED)
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO;

    // ���׸��� ������� �ʰ� Map�� List�� ����
    public Map myCartList(CartVO cartVO) throws Exception {
        Map cartMap = new HashMap();  // ���׸� ����

        // 1. cartDAO�� ���� ��ٱ��� ��ǰ ����Ʈ ��ȸ
        List myCartList = cartDAO.selectCartList(cartVO);  // List<CartVO> -> List�� ����
        System.out.println("��ȸ�� ��ٱ��� ��ǰ ��: " + myCartList.size());  // ����� �ڵ�

        // 2. ��ٱ��ϰ� ����ִ��� Ȯ��
        if (myCartList.size() == 0) {
            System.out.println("��ٱ��ϰ� ��� �ֽ��ϴ�.");  // ����� �ڵ�
            cartMap.put("myCartList", new ArrayList()); // �� ����Ʈ�� �־ ��ȯ
            cartMap.put("myGoodsList", new ArrayList()); // �� ����Ʈ�� �־ ��ȯ
            return cartMap; // �� ���� ��ȯ
        }

        // 3. ��ٱ��� ��ǰ�� ���� �߰� ���� ��ȸ (product_id ��� ����)
        List productIds = new ArrayList();  // List<Integer> -> List�� ����
        for (Object cartItem : myCartList) {
            productIds.add(((CartVO) cartItem).getProduct_id());  // Ÿ�� ��ȯ
        }

        // 4. ��ǰ ���� ��ȸ
        List myGoodsList = cartDAO.selectGoodsList(productIds);  // List<GoodsVO> -> List�� ����
        System.out.println("��ȸ�� ��ǰ ��: " + myGoodsList.size());  // ����� �ڵ�

        // 5. cartMap�� cartList�� goodsList�� �־� ��ȯ
        cartMap.put("myCartList", myCartList); // CartVO ��ü���� ��� ����Ʈ
        cartMap.put("myGoodsList", myGoodsList); // GoodsVO ��ü���� ��� ����Ʈ

        // 6. cartMap ���� Ȯ��
        System.out.println("cartMap�� ��� ������ Ȯ��: " + cartMap);
        System.out.println("productIds: " + productIds);  // Ȯ�ο� �α� �߰�

        return cartMap;
    }

    // ��Ÿ �޼���鵵 ���׸��� ������� �ʵ��� �����Ǿ����ϴ�.
    public boolean findCartGoods(CartVO cartVO) throws Exception {
        return cartDAO.selectCountInCart(cartVO);
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
        // �α� �߰�: DB ������Ʈ ���� �α� ���
        System.out.println("Updating quantity for cart ID: " + cartVO.getCart_id() + " to quantity: " + cartVO.getQuantity());

        // ���� DB ������Ʈ ����
        cartDAO.updateCartQuantity(cartVO);

        // ��� Ȯ���� ���� �α� �߰� (cartDAO���� ��ȯ���� �޴´ٸ� �߰��� �� ����)
        // ��: ������Ʈ�� �����ߴ��� Ȯ��
        System.out.println("Successfully updated quantity for cart ID: " + cartVO.getCart_id());
    }


//	
//	public boolean modifyCartQty(CartVO cartVO) throws Exception{
//		boolean result=true;
//		cartDAO.updateCartGoodsQty(cartVO);
//		return result;
//	}
//	public void removeCartGoods(int cart_id) throws Exception{
//		cartDAO.deleteCartGoods(cart_id);
//	}
//	
}
