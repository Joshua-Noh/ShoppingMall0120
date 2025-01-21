<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
            <c:forEach var="item" items="${cartMap.cartList}">
                <tr>
                    <td>${item.product_name}</td>
                    <td><fmt:formatNumber value="${item.price}" type="currency" /></td>
                    <td>
                        <form action="updateCartQuantity.do" method="post">
                            <input type="hidden" name="cart_id" value="${item.cart_id}" />
                            <input type="number" name="quantity" value="${item.quantity}" min="1" />
                            <button type="submit">수정</button>
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${item.total_price}" type="currency" /></td>
                    <td>
                        <form action="deleteCartItem.do" method="post">
                            <input type="hidden" name="cart_id" value="${item.cart_id}" />
                            <button type="submit">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h2>장바구니 합계: <fmt:formatNumber value="${cartMap.totalPrice}" type="currency" /></h2>
    <form action="checkout.do" method="post">
        <button type="submit">결제하기</button>
    </form>
</body>
</html>
