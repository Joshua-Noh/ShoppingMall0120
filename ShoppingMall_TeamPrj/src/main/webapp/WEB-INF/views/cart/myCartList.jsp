<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.Map, java.util.List" %>

<html>
<head>
    <title>장바구니</title>
</head>
<body>
    <h1>${sessionScope.memberInfo.user_name}님 장바구니입니다.</h1>

    <table border="1">
        <thead>
            <tr>
                <th>상품명</th>
                <th>가격</th>
                <th>수량</th>
                <th>총 가격</th>
                <th>작업</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cartItem" items="${cartMap.myCartList}">
                <!-- 상품 정보 찾기 -->
                <c:set var="goodsItem" value="${null}" />
                <c:forEach var="goods" items="${cartMap.myGoodsList}">
                    <c:if test="${goods.product_id == cartItem.product_id}">
                        <c:set var="goodsItem" value="${goods}" />
                    </c:if>
                </c:forEach>
                
                <!-- 상품 정보 출력 -->
                <tr>
                    <td>
                        <c:if test="${empty goodsItem}">
                            상품 정보 없음
                        </c:if>
                        <c:if test="${!empty goodsItem}">
                            ${goodsItem.product_name}
                        </c:if>
                    </td> <!-- 상품명 출력 -->

                    <td>
                        <c:if test="${empty goodsItem}">
                            가격 정보 없음
                        </c:if>
                        <c:if test="${!empty goodsItem}">
                            <fmt:formatNumber value="${goodsItem.price}" type="currency" />
                        </c:if>
                    </td> <!-- 가격 출력 -->

                    <td>
                        <form action="updateCartQuantity.do" method="post">
                            <input type="hidden" name="cart_id" value="${cartItem.cart_id}" />
                            <input type="number" name="quantity" value="${cartItem.quantity}" min="1" />
                            <input type="hidden" name="product_id" value="${cartItem.product_id}" />
                            <button type="submit">수정</button>
                        </form>
                    </td> <!-- 수량 수정 -->

                    <td>
                        <fmt:formatNumber value="${cartItem.quantity * goodsItem.price}" type="currency" />
                    </td> <!-- 총 가격 -->

                    <td>
                        <form action="deleteCartItem.do" method="post">
                            <input type="hidden" name="cart_id" value="${cartItem.cart_id}" />
                            <button type="submit">삭제</button>
                        </form>
                    </td> <!-- 삭제 버튼 -->
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>장바구니 합계: 
        <fmt:formatNumber value="${totalPrice}" type="currency" />
    </h2>

    <form action="checkout.do" method="post">
        <button type="submit">주문하기</button>
    </form>
</body>
</html>
