<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<html>
<head>
    <meta charset="UTF-8">
    <title>주문 내역</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 900px;
            margin: 50px auto;
            padding: 20px;
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
        <h1>주문 내역</h1>

        <c:if test="${not empty orderList}">
            <table>
                <thead>
                    <tr>
                        <th>주문 번호</th>
                        <th>상품명</th>
                        <th>수량</th>
                        <th>총 가격</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.order_id}</td>
                            <td>${order.product_name}</td>
                            <td>${order.quantity}</td>
                            <td><fmt:formatNumber value="${order.total_price}" type="currency" /></td>
                            <td>${order.delivery_state}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty orderList}">
            <div class="message">현재 주문 내역이 없습니다.</div>
        </c:if>
    </div>
</body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
