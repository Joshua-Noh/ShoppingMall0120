<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<html>
<head>
    <meta charset="UTF-8">
    <title>결제 시작</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
        }
        .order-details {
            margin-bottom: 30px;
        }
        .order-details div {
            margin-bottom: 15px;
            font-size: 16px;
            color: #333;
        }
        .payment-methods {
            margin-top: 20px;
        }
        .payment-methods label {
            font-size: 16px;
        }
        .payment-methods select {
            padding: 10px;
            font-size: 16px;
            margin-top: 10px;
        }
        .button {
            display: block;
            width: 100%;
            padding: 15px;
            font-size: 18px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>결제 정보</h1>
        
        <div class="order-details">
            <div><strong>주문자:</strong> ${memberName}</div>
            <div><strong>주문 번호:</strong> ${orderId}</div>
            <div><strong>상품명:</strong> ${productName}</div>
            <div><strong>수량:</strong> ${quantity}</div>
            <div><strong>총 금액:</strong> <fmt:formatNumber value="${totalAmount}" type="currency" /></div>
        </div>

        <form action="${pageContext.request.contextPath}/order/initiatePayment.do" method="post">
            <div class="payment-methods">
                <label for="paymentMethod">결제 방법 선택:</label>
                <select name="paymentMethod" id="paymentMethod">
                    <option value="credit_card">신용카드</option>
                    <option value="bank_transfer">계좌이체</option>
                    <option value="kakaopay">카카오페이</option>
                </select>
            </div>

            <div class="payment-methods">
                <label for="amount">결제 금액:</label>
                <input type="text" id="amount" name="amount" value="${totalAmount}" readonly />
            </div>

            <input type="hidden" name="order_id" value="${orderId}" />
            <input type="hidden" name="product_name" value="${productName}" />
            <input type="hidden" name="quantity" value="${quantity}" />
            
            <button type="submit" class="button">결제하기</button>
        </form>
    </div>
</body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
