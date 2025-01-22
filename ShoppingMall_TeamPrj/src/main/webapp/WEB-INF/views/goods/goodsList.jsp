<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${not empty sessionScope.welcomeMessage}">
	<div style="text-align: center; color: green; margin: 20px 0;">
		${sessionScope.welcomeMessage}</div>
	<c:remove var="welcomeMessage" scope="session" />
</c:if>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쇼핑몰 메인 페이지</title>
</head>
<body>
	<c:forEach var="item" items="${goodsList }">
	   			<div class="image"></div>
				<div class="name"><a href="${contextPath}/goods/detailInfo.do?product_id=${item.product_id}">상품이름</a></div>
				<div class="price">상품가격</div>
  </c:forEach>
	

</body>
</html>
