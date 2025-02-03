<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<html>
<head>
    <meta charset="UTF-8">
    <title>결제 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
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
            color: #333;
        }
        .status {
            text-align: center;
            font-size: 18px;
            color: #4CAF50;
        }
        .status.failed {
            color: #f44336;
        }
        .details {
            margin-top: 20px;
        }
        .details div {
            margin-bottom: 10px;
            font-size: 16px;
            color: #333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>결제 결과</h1>
        <div class="status ${status == 'success' ? 'success' : 'failed'}">
            <c:choose>
                <c:when test="${status == 'success'}">
                    결제가 성공적으로 완료되었습니다.
                </c:when>
                <c:otherwise>
                    결제에 실패하였습니다. 다시 시도해 주세요.
                </c:otherwise>
            </c:choose>
        </div>
        <div class="details">
            <div>주문 번호: ${order_id}</div>
            <div>결제 금액: <fmt:formatNumber value="${totalAmount}" type="currency" /></div>
            <div>결제 방법: ${paymentMethod}</div>
            <div>결제 상태: ${paymentStatus}</div>
        </div>
        <c:if test="${status == 'success'}">
            <div style="text-align: center;">
                <a href="${pageContext.request.contextPath}/order/orderHistory.do" style="color: #4CAF50;">주문 내역 보기</a>
            </div>
        </c:if>
    </div>
</body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
