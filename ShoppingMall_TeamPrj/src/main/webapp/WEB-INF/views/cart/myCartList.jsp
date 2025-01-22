<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    // 세션에서 memberInfo 가져오기
    Object memberInfo = session.getAttribute("memberInfo");
    if (memberInfo == null) {
        // 세션이 없으면 로그인 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/member/loginForm.do");
        return;
    }
%>

<html>
<head>
    <title>장바구니</title>
</head>
<body>
    <!-- 사용자 이름 출력 -->
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
                <c:set var="goodsItem" value="${cartMap.myGoodsList[fn:indexOf(cartMap.myGoodsList, cartItem.product_id)]}" />
                <tr>
                    <td>${goodsItem.product_name}</td> <!-- 상품명 출력 -->
                    <td><fmt:formatNumber value="${goodsItem.price}" type="currency" /></td> <!-- 가격 출력 -->
                    <td>
                        <form action="updateCartQuantity.do" method="post">
                            <input type="hidden" name="cart_id" value="${cartItem.cart_id}" />
                            <input type="number" name="quantity" value="${cartItem.quantity}" min="1" />
                            <button type="submit">수정</button>
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${cartItem.quantity * goodsItem.price}" type="currency" /></td> <!-- 총 가격 -->
                    <td>
                        <form action="deleteCartItem.do" method="post">
                            <input type="hidden" name="cart_id" value="${cartItem.cart_id}" />
                            <button type="submit">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h2>
        장바구니 합계:
        <fmt:formatNumber value="${cartMap.totalPrice}" type="currency" />
    </h2>
    <form action="checkout.do" method="post">
        <button type="submit">결제하기</button>
    </form>
</body>
</html>
