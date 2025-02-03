package com.shop.ShoppingMall_TeamPrj.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.order.service.OrderService;
import com.shop.ShoppingMall_TeamPrj.order.vo.OrderVO;

import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Controller
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderService orderService;

    // 결제 요청 메서드 (토스 결제 연동)
    public ModelAndView initiatePayment(Map<String, String> paymentDetails, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 토스 결제 API URL
        String apiUrl = "https://api.toss.im/v1/transactions";  // 실제 토스 결제 API URL로 바꿔주세요.

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 제공된 키로 인증 헤더 설정
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");  // 클라이언트 키
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");  // 시크릿 키

        // 결제 요청 데이터
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("order_id", paymentDetails.get("order_id"));
        body.add("amount", paymentDetails.get("amount"));
        body.add("currency", "KRW");
        body.add("payment_method", paymentDetails.get("payment_method"));
        body.add("return_url", paymentDetails.get("return_url"));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        // RestTemplate을 이용한 API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        // 응답 처리 (성공적인 결제 요청 후, 결과 처리)
        String paymentResponse = responseEntity.getBody();
        ModelAndView mav = new ModelAndView("paymentConfirmation");  // paymentConfirmation.jsp로 이동
        mav.addObject("paymentResponse", paymentResponse);
        return mav;
    }

    // 결제 결과 확인 메서드 (결제 완료 후)
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 토스 결제 결과 확인 API URL
        String apiUrl = "https://api.toss.im/v1/transactions/" + paymentId; // 실제 API URL로 바꿔주세요.

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");  // 클라이언트 키
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");  // 시크릿 키

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate을 이용한 API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // 응답 처리 (결제 결과 확인 후 처리)
        String paymentStatus = responseEntity.getBody();
        ModelAndView mav = new ModelAndView("paymentResult");  // paymentResult.jsp로 이동
        mav.addObject("paymentStatus", paymentStatus);
        return mav;
    }

    // 개별 상품 주문 메서드 (기존 주문 로직)
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 상품 개별 주문 처리 로직 구현
        ModelAndView mav = new ModelAndView("orderDetailPage");  // 주문 상세 페이지로 이동
        mav.addObject("orderDetails", orderVO);
        return mav;
    }
}
