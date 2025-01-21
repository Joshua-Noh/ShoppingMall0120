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
<style>

.main-image {
	text-align: center;
	background-color: #333;
	padding: 50px 0;
	color: #fff;
	font-size: 20px;
	font-weight: bold;
}

.best-seller {
	text-align: center;
	padding: 20px 0;
	background-color: #f9f9f9;
}

.best-seller h2 {
	margin-bottom: 20px;
	font-size: 18px;
	color: #333;
}

.product-list {
	display: flex;
	justify-content: center;
	gap: 15px;
	flex-wrap: wrap;
}

.product {
	width: 180px;
	text-align: center;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 15px;
	box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.product .image {
	background-color: #ccc;
	height: 120px;
	margin-bottom: 10px;
	border-radius: 5px;
}

.product .name {
	font-size: 14px;
	margin: 5px 0;
	color: #333;
}

.product .price {
	font-size: 12px;
	color: #666;
}

.event-banner {
	text-align: center;
	padding: 15px 0;
	background-color: #000;
	color: #fff;
	font-size: 14px;
	font-weight: bold;
}

</style>
</head>
<body>

	<!-- 환영 메시지 출력 -->
	<c:if test="${not empty welcomeMessage}">
		<div class="welcome-message">${welcomeMessage}</div>
	</c:if>

	<div class="main-image">큰 이미지</div>

	<div class="best-seller">
		<h2>BEST SELLER</h2>
		<div class="product-list">
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
		</div>
	</div>

		<div class="product-list">
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
		</div>
	</div>

		<div class="product-list">
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
		</div>
	</div>

		<div class="product-list">
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
		</div>
	</div>

		<div class="product-list">
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
		</div>
	</div>

		<div class="product-list">
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
			<div class="product">
				<div class="image"></div>
				<div class="name">상품이름</div>
				<div class="price">상품가격</div>
			</div>
		</div>
	</div>

	<div class="event-banner">이벤트 배너</div>

	<div class="footer">footer</div>

</body>
</html>
