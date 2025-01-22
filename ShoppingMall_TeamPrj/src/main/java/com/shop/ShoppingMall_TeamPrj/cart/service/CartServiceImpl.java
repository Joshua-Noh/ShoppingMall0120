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

    // 제네릭을 사용하지 않고 Map과 List를 수정
    public Map myCartList(CartVO cartVO) throws Exception {
        Map cartMap = new HashMap();  // 제네릭 제거

        // 1. cartDAO를 통해 장바구니 상품 리스트 조회
        List myCartList = cartDAO.selectCartList(cartVO);  // List<CartVO> -> List로 변경
        System.out.println("조회된 장바구니 상품 수: " + myCartList.size());  // 디버깅 코드

        // 2. 장바구니가 비어있는지 확인
        if (myCartList.size() == 0) {
            System.out.println("장바구니가 비어 있습니다.");  // 디버깅 코드
            cartMap.put("myCartList", new ArrayList()); // 빈 리스트를 넣어서 반환
            cartMap.put("myGoodsList", new ArrayList()); // 빈 리스트를 넣어서 반환
            return cartMap; // 빈 맵을 반환
        }

        // 3. 장바구니 상품에 대한 추가 정보 조회 (product_id 목록 추출)
        List productIds = new ArrayList();  // List<Integer> -> List로 변경
        for (Object cartItem : myCartList) {
            productIds.add(((CartVO) cartItem).getProduct_id());  // 타입 변환
        }

        // 4. 상품 정보 조회
        List myGoodsList = cartDAO.selectGoodsList(productIds);  // List<GoodsVO> -> List로 변경
        System.out.println("조회된 상품 수: " + myGoodsList.size());  // 디버깅 코드

        // 5. cartMap에 cartList와 goodsList를 넣어 반환
        cartMap.put("myCartList", myCartList); // CartVO 객체들이 담긴 리스트
        cartMap.put("myGoodsList", myGoodsList); // GoodsVO 객체들이 담긴 리스트

        // 6. cartMap 내용 확인
        System.out.println("cartMap에 담긴 데이터 확인: " + cartMap);
        System.out.println("productIds: " + productIds);  // 확인용 로그 추가

        return cartMap;
    }

    // 기타 메서드들도 제네릭을 사용하지 않도록 수정되었습니다.
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
        // 로그 추가: DB 업데이트 전에 로그 출력
        System.out.println("Updating quantity for cart ID: " + cartVO.getCart_id() + " to quantity: " + cartVO.getQuantity());

        // 실제 DB 업데이트 실행
        cartDAO.updateCartQuantity(cartVO);

        // 결과 확인을 위한 로그 추가 (cartDAO에서 반환값을 받는다면 추가할 수 있음)
        // 예: 업데이트가 성공했는지 확인
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
