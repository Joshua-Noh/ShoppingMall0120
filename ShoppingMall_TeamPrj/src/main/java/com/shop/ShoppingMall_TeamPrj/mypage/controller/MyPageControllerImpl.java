package com.shop.ShoppingMall_TeamPrj.mypage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;
import com.shop.ShoppingMall_TeamPrj.mypage.service.MyPageService;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

@Controller("myPageController")
@RequestMapping(value="/mypage")
public class MyPageControllerImpl implements MyPageController {

    @Autowired
    private MyPageService myPageService;
    
    @Autowired
    private MemberVO memberVO;
    
    @Override
    @RequestMapping(value="/myPageMain.do" ,method = RequestMethod.GET)
    public ModelAndView myPageMain(@RequestParam(required = false, value="message") String message,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("side_menu", "my_page"); // 마이페이지 사이드 메뉴 설정

        // 뷰 네임 가져오기
        String viewName = (String) request.getAttribute("viewName");
        if (viewName == null || viewName.trim().isEmpty()) {
            System.out.println("[DEBUG] myPageMain: viewName이 유효하지 않음. main.do로 리디렉트");
            return new ModelAndView("redirect:/main.do");
        }
        
        // 로그인 체크
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            System.out.println("[DEBUG] myPageMain: memberInfo가 없음. 로그인 페이지로 리디렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        ModelAndView mav = new ModelAndView(viewName);
        int member_id = memberVO.getUser_id();
        List<OrderVO> myOrderList = myPageService.listMyOrderGoods(member_id);
        
        mav.addObject("message", message);
        mav.addObject("myOrderList", myOrderList);
        return mav;
    }
    
    private String calcSearchPeriod(String fixedSearchPeriod) {
        String beginDate, endDate;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        if ("1week".equals(fixedSearchPeriod)) {
            cal.add(java.util.Calendar.DATE, -7);
        } else if ("1month".equals(fixedSearchPeriod)) {
            cal.add(java.util.Calendar.MONTH, -1);
        } else if ("3months".equals(fixedSearchPeriod)) {
            cal.add(java.util.Calendar.MONTH, -3);
        } else if ("6months".equals(fixedSearchPeriod)) {
            cal.add(java.util.Calendar.MONTH, -6);
        } else if ("1year".equals(fixedSearchPeriod)) {
            cal.add(java.util.Calendar.YEAR, -1);
        } else {
            cal.add(java.util.Calendar.DATE, -30); // 기본적으로 30일 전 조회
        }
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        beginDate = sdf.format(cal.getTime());
        endDate = sdf.format(new java.util.Date()); // 오늘 날짜
        
        return beginDate + "," + endDate;
    }
    
    @Override
    @RequestMapping(value="/myOrderDetail.do", method = RequestMethod.GET)
    public ModelAndView myOrderDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO orderer = (MemberVO) session.getAttribute("memberInfo");
        if (orderer == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        // viewName을 직접 지정 (InternalResourceViewResolver에 의해 prefix와 suffix가 붙습니다)
        ModelAndView mav = new ModelAndView("mypage/listMyOrderHistory");
        int member_id = orderer.getUser_id();
        List<OrderVO> myOrderList = myPageService.listMyOrderGoods(member_id);
        
        mav.addObject("orderer", orderer);
        mav.addObject("myOrderList", myOrderList);
        return mav;
    }
    
    @Override
    @RequestMapping(value="/listMyOrderHistory.do", method = RequestMethod.GET)
    public ModelAndView listMyOrderHistory(@RequestParam Map<String, String> dateMap,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        memberVO = (MemberVO) session.getAttribute("memberInfo");
        if (memberVO == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView(viewName);
        int member_id = memberVO.getUser_id();
        
        String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
        String beginDate = null, endDate = null;
        
        String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
        beginDate = tempDate[0];
        endDate = tempDate[1];
        dateMap.put("beginDate", beginDate);
        dateMap.put("endDate", endDate);
        dateMap.put("member_id", String.valueOf(member_id));
        List<OrderVO> myOrderHistList = myPageService.listMyOrderHistory(dateMap);
        
        String[] beginDate1 = beginDate.split("-"); // 검색일자를 년,월,일로 분리해서 화면에 전달합니다.
        String[] endDate1 = endDate.split("-");
        mav.addObject("beginYear", beginDate1[0]);
        mav.addObject("beginMonth", beginDate1[1]);
        mav.addObject("beginDay", beginDate1[2]);
        mav.addObject("endYear", endDate1[0]);
        mav.addObject("endMonth", endDate1[1]);
        mav.addObject("endDay", endDate1[2]);
        mav.addObject("myOrderHistList", myOrderHistList);
        return mav;
    }
    
    @Override
    @RequestMapping(value="/cancelMyOrder.do", method = RequestMethod.POST)
    public ModelAndView cancelMyOrder(@RequestParam("order_id") int order_id,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO member = (MemberVO) session.getAttribute("memberInfo");
        if (member == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        ModelAndView mav = new ModelAndView();
        myPageService.cancelOrder(order_id);
        mav.addObject("message", "cancel_order");
        mav.setViewName("redirect:/mypage/myPageMain.do");
        return mav;
    }
    
    @Override
    @RequestMapping(value="/myDetailInfo.do", method = RequestMethod.GET)
    public ModelAndView myDetailInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO member = (MemberVO) session.getAttribute("memberInfo");
        if (member == null) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
}
