<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Map" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<html>
<head>
    <meta charset="UTF-8">
    <title>주문 확인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 900px;
            margin: 50px auto;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        th, td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f1f1f1;
        }
        .total-price {
            font-size: 18px;
            font-weight: bold;
            color: #d9534f;
            text-align: right;
            margin-bottom: 30px;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            font-size: 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .message {
            color: red;
            text-align: center;
            font-size: 16px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>주문 내역 확인</h1>

        <!-- 주문 내역 테이블 -->
        <table>
            <thead>
                <tr>
                    <th>상품명</th>
                    <th>가격</th>
                    <th>수량</th>
                    <th>총 가격</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cartItem" items="${cartMap.myCartList}">
                    <c:forEach var="goods" items="${cartMap.myGoodsList}">
                        <c:if test="${goods.product_id == cartItem.product_id}">
                            <tr>
                                <td>${goods.product_name}</td>
                                <td><fmt:formatNumber value="${goods.price}" type="currency" /></td>
                                <td>${cartItem.quantity}</td>
                                <td><fmt:formatNumber value="${cartItem.quantity * goods.price}" type="currency" /></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>

        <!-- 총 합계 -->
        <div class="total-price">
            총 합계: <fmt:formatNumber value="${totalPrice}" type="currency" />
        </div>

        <!-- 결제 버튼 -->
        <form action="${pageContext.request.contextPath}/order/initiatePayment.do" method="post">
            <input type="hidden" name="order_id" value="${order_id}" />
            <input type="hidden" name="amount" value="${totalPrice}" />
            <input type="hidden" name="payment_method" value="card" />  <!-- 결제 방법 -->
            <input type="hidden" name="return_url" value="${pageContext.request.contextPath}/order/paymentResult.do" />  <!-- 결제 후 리턴 URL -->
            <button type="submit" class="btn">결제 진행</button>
        </form>

        <!-- 결제 실패 시 메시지 -->
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
    </div>
</body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
