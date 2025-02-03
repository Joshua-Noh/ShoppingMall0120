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

    // ���� ��û �޼��� (�佺 ���� ����)
    public ModelAndView initiatePayment(Map<String, String> paymentDetails, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // �佺 ���� API URL
        String apiUrl = "https://api.toss.im/v1/transactions";  // ���� �佺 ���� API URL�� �ٲ��ּ���.

        // ��� ����
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ������ Ű�� ���� ��� ����
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");  // Ŭ���̾�Ʈ Ű
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");  // ��ũ�� Ű

        // ���� ��û ������
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("order_id", paymentDetails.get("order_id"));
        body.add("amount", paymentDetails.get("amount"));
        body.add("currency", "KRW");
        body.add("payment_method", paymentDetails.get("payment_method"));
        body.add("return_url", paymentDetails.get("return_url"));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        // RestTemplate�� �̿��� API ȣ��
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        // ���� ó�� (�������� ���� ��û ��, ��� ó��)
        String paymentResponse = responseEntity.getBody();
        ModelAndView mav = new ModelAndView("paymentConfirmation");  // paymentConfirmation.jsp�� �̵�
        mav.addObject("paymentResponse", paymentResponse);
        return mav;
    }

    // ���� ��� Ȯ�� �޼��� (���� �Ϸ� ��)
    public ModelAndView checkPaymentStatus(String paymentId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // �佺 ���� ��� Ȯ�� API URL
        String apiUrl = "https://api.toss.im/v1/transactions/" + paymentId; // ���� API URL�� �ٲ��ּ���.

        // ��� ����
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "test_ck_ALnQvDd2VJPDjX40XlGY8Mj7X41m");  // Ŭ���̾�Ʈ Ű
        headers.set("X-TOSS-SECRET-KEY", "test_sk_DLJOpm5Qrl12v9DZ9Qee8PNdxbWn");  // ��ũ�� Ű

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate�� �̿��� API ȣ��
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // ���� ó�� (���� ��� Ȯ�� �� ó��)
        String paymentStatus = responseEntity.getBody();
        ModelAndView mav = new ModelAndView("paymentResult");  // paymentResult.jsp�� �̵�
        mav.addObject("paymentStatus", paymentStatus);
        return mav;
    }

    // ���� ��ǰ �ֹ� �޼��� (���� �ֹ� ����)
    public ModelAndView orderEachGoods(OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ��ǰ ���� �ֹ� ó�� ���� ����
        ModelAndView mav = new ModelAndView("orderDetailPage");  // �ֹ� �� �������� �̵�
        mav.addObject("orderDetails", orderVO);
        return mav;
    }
}
