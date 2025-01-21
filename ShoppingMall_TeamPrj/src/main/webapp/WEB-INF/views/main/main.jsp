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
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f9f9f9;
	color: #333;
}

.header {
	display: flex;
	align-items: center;
	justify-content: space-between;
	background-color: #000;
	padding: 10px 20px;
	border-bottom: 2px solid #ffffff;
}

.logo {
	font-size: 20px;
	font-weight: bold;
	color: #ffffff;
}

.nav-buttons {
	flex: 1;
	text-align: center;
}

.nav-buttons a {
	margin: 0 15px;
	color: #ffffff;
	text-decoration: none;
	font-size: 14px;
}

.nav-buttons a:hover {
	color: #ff4500;
	text-decoration: underline;
}

.auth-buttons button {
	margin-left: 10px;
	padding: 5px 15px;
	border: 1px solid #fff;
	background-color: transparent;
	color: #fff;
	border-radius: 5px;
	font-size: 12px;
	cursor: pointer;
}

.auth-buttons button:hover {
	background-color: #ffffff;
	color: #000;
}

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

.footer {
	text-align: center;
	padding: 20px 0;
	background-color: #333;
	color: #fff;
	font-size: 12px;
}

.welcome-message {
	text-align: center;
	margin: 20px 0;
	font-size: 16px;
	color: green;
}
</style>
</head>
<body>

	<div class="header">
		<div class="logo">LOGO</div>
		<div class="nav-buttons">
			<a href="#">신상품</a> <a href="#">상의</a> <a href="#">하의</a> <a
				href="${contextPath}/myCartList.do">장바구니</a>
		</div>
		<div class="auth-buttons">
			<c:choose>
				<c:when test="${isLogOn}">
					<span style="color: white;">환영합니다, ${memberInfo.user_name}님!</span>
					<button onclick="location.href='${contextPath}/member/logout.do'">로그아웃</button>
				</c:when>
				<c:otherwise>
					<button
						onclick="location.href='${contextPath}/member/loginForm.do'">로그인</button>
					<button
						onclick="location.href='${contextPath}/member/addMemberForm.do'">회원가입</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

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

	<div class="event-banner">이벤트 배너</div>

	<div class="footer">footer</div>

</body>
</html>
