package com.shop.ShoppingMall_TeamPrj.main;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.shop.ShoppingMall_TeamPrj.goods.service.GoodsService;
import com.shop.ShoppingMall_TeamPrj.goods.vo.GoodsVO;

@Controller("mainController")
public class MainController {

    @Autowired
    private GoodsService goodsService;
    
    /**
     * /main/main.do 요청 시 main.jsp를 보여주며, 
     * 신상품 리스트(카테고리 0)를 조회하여 모델 속성 "productList"로 전달합니다.
     */
    @RequestMapping(value="/main/main.do", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 기본적으로 메인 페이지에서는 category_id가 0 (신상품 혹은 전체)인 경우로 처리
        int category_id = 0;
        
        // GoodsService에서 category_id가 0인 경우 신상품 리스트를 조회합니다.
        List<GoodsVO> productList = goodsService.goodsNewList();
        
        // ModelAndView 객체 생성 및 뷰 이름 설정 (뷰 리졸버가 "/WEB-INF/views/main/main.jsp" 등으로 매핑되도록)
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/main/main");
        
        // 모델에 조회된 상품 리스트와 categoryId를 담습니다.
        mav.addObject("productList", productList);
        mav.addObject("categoryId", String.valueOf(category_id));
        
        // 디버깅용 출력
        System.out.println("MainController - productList : " + productList);
        
        return mav;
    }
}
