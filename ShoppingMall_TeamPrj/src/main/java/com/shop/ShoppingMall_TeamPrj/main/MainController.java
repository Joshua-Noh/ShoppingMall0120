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
     * /main/main.do ��û �� main.jsp�� �����ָ�, 
     * �Ż�ǰ ����Ʈ(ī�װ� 0)�� ��ȸ�Ͽ� �� �Ӽ� "productList"�� �����մϴ�.
     */
    @RequestMapping(value="/main/main.do", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // �⺻������ ���� ������������ category_id�� 0 (�Ż�ǰ Ȥ�� ��ü)�� ���� ó��
        int category_id = 0;
        
        // GoodsService���� category_id�� 0�� ��� �Ż�ǰ ����Ʈ�� ��ȸ�մϴ�.
        List<GoodsVO> productList = goodsService.goodsNewList();
        
        // ModelAndView ��ü ���� �� �� �̸� ���� (�� �������� "/WEB-INF/views/main/main.jsp" ������ ���εǵ���)
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/main/main");
        
        // �𵨿� ��ȸ�� ��ǰ ����Ʈ�� categoryId�� ����ϴ�.
        mav.addObject("productList", productList);
        mav.addObject("categoryId", String.valueOf(category_id));
        
        // ������ ���
        System.out.println("MainController - productList : " + productList);
        
        return mav;
    }
}
