<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Google Fonts: Roboto (본문) & Lora (제목) -->
<link href="https://fonts.googleapis.com/css2?family=Lora:wght@700&family=Roboto:wght@400;500&display=swap" rel="stylesheet">

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- 환영 메시지 -->
<c:if test="${not empty sessionScope.welcomeMessage}">
    <div class="welcome-message">
        ${sessionScope.welcomeMessage}
    </div>
    <c:remove var="welcomeMessage" scope="session" />
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>최신 트렌드 쇼핑몰 메인 페이지</title>
  <style>
    /* CSS Variables for Theme */
    :root {
      --primary-color: #2c3e50;       /* 다크 블루 */
      --secondary-color: #e67e22;     /* 오렌지 */
      --background-color: #ecf0f1;    /* 라이트 그레이 */
      --accent-color: #3498db;        /* 블루 */
      --font-main: 'Roboto', sans-serif;
      --heading-font: 'Lora', serif;
      --transition-speed: 0.3s;
    }
    
    /* Global Reset & Base Styles */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    body {
      font-family: var(--font-main);
      background-color: var(--background-color);
      color: var(--primary-color);
      line-height: 1.6;
      animation: fadeIn 1s ease-in;
    }
    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }
    
    /* Optional Sticky Header */
    header {
      position: sticky;
      top: 0;
      background: #fff;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      padding: 15px 20px;
      z-index: 100;
    }
    
    /* 풀-폭 래퍼 */
    .full-width {
      width: 100%;
    }
    
    /* Container: 제품 리스트 영역에만 적용 (중앙 정렬) */
    .container {
      max-width: 1200px;
      margin: 20px auto;
      padding: 20px;
    }
    
    /* Welcome Message */
    .welcome-message {
      text-align: center;
      color: green;
      font-size: 1.2em;
      margin: 20px 0;
    }
    
    /* Slider Styles (풀-폭) */
    .slider {
      position: relative;
      overflow: hidden;
      margin-bottom: 30px;
    }
    .slides {
      display: flex;
      transition: transform 0.5s ease-in-out;
    }
    .slide {
      flex: 0 0 20%;
      position: relative;
    }
    .slide img {
      width: 100%;
      display: block;
      object-fit: cover;
    }
    .prev-btn, .next-btn {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      background-color: rgba(0, 0, 0, 0.5);
      color: #fff;
      border: none;
      padding: 10px 20px;
      cursor: pointer;
      font-size: 20px;
      z-index: 10;
      transition: background-color var(--transition-speed);
    }
    .prev-btn { left: 10px; }
    .next-btn { right: 10px; }
    .prev-btn:hover, .next-btn:hover {
      background-color: rgba(0, 0, 0, 0.8);
    }
    
    /* Category Buttons (풀-폭) */
    .best-ranking-buttons {
      text-align: center;
      margin: 20px 0;
    }
    .best-ranking-buttons a {
      display: inline-block;
      padding: 10px 15px;
      margin: 0 5px;
      background-color: #f8f8f8;
      color: var(--primary-color);
      text-decoration: none;
      border-radius: 20px;
      font-size: 14px;
      transition: background-color var(--transition-speed), color var(--transition-speed);
    }
    .best-ranking-buttons a:hover {
      background-color: #ddd;
    }
    .best-ranking-buttons a.active {
      background-color: black;
      color: #fff;
    }
    
    /* Product List (컨테이너 내부) */
    .product-list {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }
    .product {
      background: #fff;
      /* 테두리 없이, 둥근 모서리 및 화려한 그림자 효과 */
      border: none;
      border-radius: 32px;  /* 더 둥근 모서리 */
      overflow: hidden;
      box-shadow: 0 6px 12px rgba(0,0,0,0.15);
      transition: transform var(--transition-speed), box-shadow var(--transition-speed);
      display: flex;
      flex-direction: column;
      height: 400px;
    }
    .product:hover {
      transform: translateY(-5px);
      box-shadow: 0 10px 20px rgba(0,0,0,0.25);
    }
    .product img {
      width: 100%;
      height: 250px;
      object-fit: cover;
      transition: transform var(--transition-speed);
    }
    .product:hover img {
      transform: scale(1.05);
    }
    .product .info {
      flex: 1;
      padding: 10px;
      text-align: center;
      display: flex;
      flex-direction: column;
      justify-content: center;
      background-color: #ffffff;
    }
    .product .info .name {
      font-size: 1.1em;
      font-weight: bold;
      margin-bottom: 8px;
      font-family: var(--heading-font);
    }
    .product .info .name a {
      text-decoration: none;
      color: var(--primary-color);
      transition: color var(--transition-speed);
    }
    .product .info .name a:hover {
      color: var(--accent-color);
    }
    .product .info .price {
      font-size: 1.1em;
      color: var(--secondary-color);
      font-weight: bold;
    }
    
    /* Brand Intro (풀-폭) */
    .brand-intro {
      text-align: center;
      padding: 60px 20px;
      background-color: #111;
      color: #fff;
      margin-top: 40px;
    }
    .brand-intro h1 {
      font-size: 48px;
      font-family: var(--heading-font);
      margin-bottom: 10px;
      animation: fadeIn 1.5s ease-in-out;
    }
    .brand-intro p {
      font-size: 20px;
      font-family: 'Poppins', sans-serif;
      opacity: 0.8;
      animation: fadeIn 2s ease-in-out;
    }
    
    /* Main Banner & Shop Info (풀-폭) */
    .main-banner-container {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      justify-content: center;
      margin: 40px 0;
      width: 100%;
    }
    .main-banner {
      flex: 1 1 400px;
      text-align: center;
      padding: 20px;
    }
    .main-banner img {
      width: 90%;
      max-width: 500px;
      object-fit: contain;
    }
    .shop-info {
      flex: 1 1 300px;
      padding: 20px;
    }
    .shop-info h1 {
      font-size: 32px;
      font-weight: bold;
      margin-bottom: 10px;
      font-family: var(--heading-font);
    }
    .shop-info p {
      font-size: 18px;
      color: #666;
      margin-bottom: 10px;
    }
    .shop-info ul {
      list-style: none;
      padding: 0;
    }
    .shop-info li {
      font-size: 16px;
      margin: 5px 0;
    }
    .shop-info button {
      background-color: black;
      color: #fff;
      border: none;
      padding: 10px 20px;
      margin-top: 20px;
      cursor: pointer;
      font-size: 16px;
      border-radius: 5px;
      transition: background-color var(--transition-speed);
    }
    .shop-info button:hover {
      background-color: #333;
    }
    
    /* Responsive Design */
    @media (max-width: 768px) {
      .container {
        width: 95%;
        padding: 10px;
      }
      .product-list {
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      }
      .main-banner-container {
        flex-direction: column;
      }
    }
  </style>
</head>
<body>

  <!-- 풀-폭 영역: 슬라이더 -->
  <div class="full-width">
    <div class="slider">
      <button class="prev-btn">〈</button>
      <div class="slides">
        <%
          String[] images = { "main1.jpg", "main2.jpg", "main3.jpg", "main4.jpg", "main5.jpg" };
          for (int j = 0; j < 2; j++) {
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
      <button class="next-btn">〉</button>
    </div>
  </div>

  <!-- 풀-폭 영역: 카테고리 버튼 -->
  <div class="full-width">
    <%
        String categoryId = request.getParameter("category_id");
        if (categoryId == null) categoryId = "0";
        String[] categories = { "전체", "신상품", "티셔츠", "셔츠/블라우스", "니트/스웨터", "팬츠", "스커트", "재킷", "코트", "원피스", "정장", "스포츠웨어" };
    %>
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
  </div>

<!-- 컨테이너 영역: 동적 제품 리스트 (제품 카드) -->
<div class="container">
  <div class="product-list">
    <c:if test="${not empty productList}">
      <c:forEach var="product" items="${productList}" varStatus="status" begin="0" end="19">
        <!-- categoryId가 '0'인 경우 (신상품/전체) 출력 -->
        <c:if test="${categoryId == '0'}">
          <div class="product">
            <img alt="상품이미지"
                 src="${contextPath}/common/largeImage.do?product_id=${product.product_id}&fileName=${product.fileName}">
            <div class="info">
              <div class="name">
                <a href="${contextPath}/goods/detailInfo.do?product_id=${product.product_id}">
                  ${product.product_name}
                </a>
              </div>
              <div class="price">${product.price}원</div>
            </div>
          </div>
        </c:if>
      </c:forEach>
    </c:if>
    <c:if test="${empty productList}">
      <p>상품 목록이 없습니다.</p>
    </c:if>
  </div>
</div>


  <!-- 풀-폭 영역: 브랜드 아이덴티티 -->
  <div class="full-width">
    <div class="brand-intro">
      <h1>Timeless Elegance, NOIR</h1>
      <p>미니멀하고 세련된 감성을 담은 특별한 패션 브랜드.</p>
    </div>
  </div>

  <!-- 풀-폭 영역: 메인 배너 및 쇼핑몰 소개 -->
  <div class="full-width">
    <div class="main-banner-container">
      <div class="main-banner">
        <img src="${pageContext.request.contextPath}/resources/image/메인베너2.jpg" alt="메인 배너">
      </div>
      <div class="shop-info">
        <h1>NOIR</h1>
        <p>어둠 속 특별한 발견, NOIR.</p>
        <p>미니멀하고 세련된 감성을 담은 패션 브랜드.</p>
        <p>당신만의 개성을 살리는 독창적인 스타일을 만나보세요.</p>
        <ul>
          <li>✔ 트렌디한 디자인</li>
          <li>✔ 높은 퀄리티</li>
          <li>✔ 합리적인 가격</li>
        </ul>
        <button onclick="location.href='${contextPath}/company/introduction'">브랜드 스토리 보기</button>
      </div>
    </div>
  </div>

  <!-- JavaScript: Slider Functionality -->
  <script>
    document.addEventListener("DOMContentLoaded", function() {
      const slider = document.querySelector(".slider");
      const slides = slider.querySelector(".slides");
      const slideElements = slides.children;
      const slideCount = slideElements.length;
      const prevBtn = slider.querySelector(".prev-btn");
      const nextBtn = slider.querySelector(".next-btn");
      let currentIndex = 0;
      
      // 슬라이더 위치 업데이트 함수 (instant 인 경우 전환 효과 없이 즉시 이동)
      function updateSliderPosition(instant = false) {
        if (instant) {
          slides.style.transition = "none";
        } else {
          slides.style.transition = "transform 0.5s ease-in-out";
        }
        slides.style.transform = "translateX(" + (-currentIndex * slideElements[0].offsetWidth) + "px)";
        if (instant) {
          setTimeout(() => { slides.style.transition = "transform 0.5s ease-in-out"; }, 50);
        }
      }
      
      prevBtn.addEventListener("click", function() {
        if (currentIndex === 0) {
          currentIndex = slideCount - 1;
          updateSliderPosition(true);
        } else {
          currentIndex--;
          updateSliderPosition();
        }
      });
      
      nextBtn.addEventListener("click", function() {
        if (currentIndex === slideCount - 1) {
          currentIndex = 0;
          updateSliderPosition(true);
        } else {
          currentIndex++;
          updateSliderPosition();
        }
      });
      
      // 자동 슬라이드 (5초마다)
      setInterval(function() {
        if (currentIndex === slideCount - 1) {
          currentIndex = 0;
          updateSliderPosition(true);
        } else {
          currentIndex++;
          updateSliderPosition();
        }
      }, 5000);
      
      window.addEventListener("resize", function() {
        updateSliderPosition();
      });
    });
  </script>
</body>
</html>
