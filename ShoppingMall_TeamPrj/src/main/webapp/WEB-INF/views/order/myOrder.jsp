<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>주문 확인</title>
</head>
<body>
    <h1>주문 내역 확인</h1>

    <table border="1">
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

    <h2>총 합계: <fmt:formatNumber value="${totalPrice}" type="currency" /></h2>
</body>
</html>
