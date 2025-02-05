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
/* 전체 페이지 페이드인 효과 */
body {
    margin: 0;
    background-color: #f8f9fa;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #333;
    animation: fadeIn 1s ease-in;
}

/* 페이드인 애니메이션 */
@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
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
	font-size: 30px;
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
.slider {
  position: relative; /* 부모 요소에 상대적 위치 설정 */
  width: 100%;
  overflow: hidden;
}

.slides {
  display: flex;
  transition: transform 0.5s ease-in-out;
}

.slide {
  flex: 0 0 20%; /* 5개씩 보이도록 설정 */
  box-sizing: border-box;
  position: relative; /* 텍스트를 슬라이드 위에 올리기 위해 필요 */
}

.slide img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

/* 텍스트 영역 스타일 추가 */
.slide-caption {
  position: absolute; /* 슬라이드 내부에서 위치 고정 */
  bottom: 20px; /* 슬라이드 하단에서 20px 위 */
  left: 20px; /* 슬라이드 왼쪽에서 20px 간격 */
  color: white; /* 텍스트 색상 */
  background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
  padding: 10px 15px; /* 텍스트 영역 내부 여백 */
  border-radius: 5px; /* 텍스트 영역 모서리 둥글게 */
  z-index: 100; /* 이미지보다 위에 표시되도록 설정 */
}

.slide-caption h2 {
  font-size: 18px; /* 제목 크기 */
  margin: 0 0 5px;
}

.slide-caption p {
  font-size: 14px; /* 설명 크기 */
  margin: 0;
}

/* 화살표 버튼 스타일 */
.prev-btn, .next-btn {
  position: absolute; /* 부모인 .slider에 고정 */
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 20px;
  cursor: pointer;
  z-index: 1000; /* 슬라이드보다 위에 위치 */
}

.prev-btn {
  left: 10px; /* 슬라이더 왼쪽 끝 */
}

.next-btn {
  right: 10px; /* 슬라이더 오른쪽 끝 */
}

/* 버튼 호버 효과 */
.prev-btn:hover, .next-btn:hover {
  background-color: rgba(0, 0, 0, 0.8);
}

.best-ranking-buttons {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    margin: 20px 0;
}

.category-row {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 10px;
}

.best-ranking-buttons a {
    display: inline-block;
    padding: 12px 20px;
    font-size: 14px;
    font-weight: bold;
    color: #333;
    background-color: #f8f8f8;
    border-radius: 20px;
    text-decoration: none;
    transition: all 0.3s ease-in-out;
}

.best-ranking-buttons a:hover {
    background-color: #ddd;
}

.best-ranking-buttons a.active {
    background-color: black;
    color: white;
}



</style>
</head>
<body>

	<!-- 환영 메시지 출력 -->
	<c:if test="${not empty welcomeMessage}">
		<div class="welcome-message">${welcomeMessage}</div>
	</c:if>
<div class="slider">
  <!-- 이전 버튼 -->
  <button class="prev-btn">〈</button>
  
<!-- 슬라이드 컨테이너 -->
<div class="slides">
    <%
        String[] images = { "main1.jpg", "main2.jpg", "main3.jpg", "main4.jpg", "main5.jpg" };
        for (int j = 0; j < 2; j++) { // 2번 반복
            for (int i = 0; i < images.length; i++) {
    %>
                <div class="slide">
                    <img src="${pageContext.request.contextPath}/resources/image/<%= images[i] %>" alt="이미지 <%= i + 1 %>">
                </div>
    <%
            }
        }
    %>
</div>

  <!-- 다음 버튼 -->
  <button class="next-btn">〉</button>
</div>

	<div class="best-seller">
		<h2>지금 눈 여겨볼 만한 베스트 셀러!</h2>
		<div class="product-list">
			<div class="product">
				<img alt="상품이미지"
				src="${contextPath}/common/thumbnails.do?product_id=2&fileName=포토샵 무작정 따라하기_메인.jpg">
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

<%
    // 현재 URL의 category_id 값을 가져옴
    String categoryId = request.getParameter("category_id");
    if (categoryId == null) categoryId = "0"; // 기본값: 전체

    // 카테고리 목록을 배열로 저장
    String[] categories = { "전체", "신상품", "티셔츠", "셔츠/블라우스", "니트/스웨터", "팬츠", "스커트", "재킷", "코트", "원피스", "정장", "스포츠웨어" };
%>

<!-- BEST 랭킹 카테고리 버튼 -->
<div class="best-ranking-buttons">
    <div class="category-row">
        <% for (int i = 0; i <= 5; i++) { %>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=<%= i %>"
               class="<%= categoryId.equals(String.valueOf(i)) ? "active" : "" %>">
                <%= categories[i] %>
            </a>
        <% } %>
    </div>

    <div class="category-row">
        <% for (int i = 6; i < categories.length; i++) { %>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=<%= i %>"
               class="<%= categoryId.equals(String.valueOf(i)) ? "active" : "" %>">
                <%= categories[i] %>
            </a>
        <% } %>
    </div>
</div>

<%
    // 현재 URL의 category_id 값을 가져옴
    categoryId = request.getParameter("category_id");

    // category_id가 없거나 0이면 기본값을 "전체"로 설정
    if (categoryId == null || categoryId.equals("0")) {
        categoryId = "0"; // 기본값: 전체
    }
%>

<!-- 상품 리스트 출력 -->
<div class="product-list">
    <c:forEach var="product" items="${productList}">
        <c:if test="${categoryId == '0'}">
            <div class="product">
                <!-- 상품 이미지 -->
                <img alt="상품이미지"
                     src="${pageContext.request.contextPath}/common/thumbnails.do?product_id=${product.productId}">
                <!-- 상품 정보 -->
                <div class="name">${product.productName}</div>
                <div class="price">${product.price}원</div>
                <div class="size">사이즈: ${product.size}</div>
            </div>
        </c:if>
    </c:forEach>
</div>




<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 상품 리스트 출력 -->
<div class="product-list">

</div>


<script src="${pageContext.request.contextPath}/resources/js/slider.js"></script>
</body>
</html>
