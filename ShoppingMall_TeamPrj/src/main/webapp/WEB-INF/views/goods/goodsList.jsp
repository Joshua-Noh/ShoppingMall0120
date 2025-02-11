<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>NOIR - 스타일리쉬한 패션몰</title>

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Lora:wght@700&family=Roboto:wght@400;500&display=swap" rel="stylesheet">

  <!-- CSS 스타일 -->
  <style>
    :root {
      --primary-color: #2c3e50;
      --secondary-color: #e67e22;
      --background-color: #f5f7fa;
      --accent-color: #3498db;
      --font-main: 'Roboto', sans-serif;
      --heading-font: 'Lora', serif;
      --transition-speed: 0.3s;
    }

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
    }

    /* === 컨테이너 및 그리드 설정 === */
    .container {
      width: 90%;
      max-width: 1200px;
      margin: 40px auto;
      padding: 20px;
    }

    .goods-container {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      justify-content: center;
    }

    /* === 상품 카드 스타일 === */
    .goods-item {
      background: #fff;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      transition: transform var(--transition-speed), box-shadow var(--transition-speed);
      display: flex;
      flex-direction: column;
      align-items: center;
      text-align: center;
      padding: 15px;
      cursor: pointer;
    }

    .goods-item:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 18px rgba(0, 0, 0, 0.2);
    }

    /* === 상품 이미지 === */
    .image-box {
      width: 100%;
      height: 230px;
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      border-radius: 10px;
    }

    .goods-item img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s ease-in-out;
    }

    .goods-item:hover img {
      transform: scale(1.07);
    }

    /* === 상품명 스타일 === */
    .goods-item .name {
      font-size: 1.3rem;
      font-weight: bold;
      margin: 15px 0 8px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      font-family: var(--heading-font);
    }

    .goods-item .name a {
      text-decoration: none;
      color: #333;
      transition: color var(--transition-speed);
    }

    .goods-item .name a:hover {
      color: var(--accent-color);
    }

    /* === 가격 스타일 === */
    .goods-item .price {
      font-size: 1.4rem;
      color: var(--secondary-color);
      font-weight: bold;
      margin-bottom: 10px;
    }

    /* === 웰컴 메시지 스타일 === */
    .welcome-message {
      text-align: center;
      font-size: 1.5rem;
      font-weight: bold;
      color: #28a745;
      padding: 15px;
      background: #e8f5e9;
      border-radius: 10px;
      margin-bottom: 25px;
    }

    /* === 반응형 설정 === */
    @media (max-width: 768px) {
      .goods-container {
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 15px;
      }
    }

    @media (max-width: 480px) {
      .goods-container {
        grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
      }
      .goods-item .name {
        font-size: 1rem;
      }
      .goods-item .price {
        font-size: 1.2rem;
      }
    }
  </style>
</head>

<body>

  <!-- 환영 메시지 -->
  <c:if test="${not empty sessionScope.welcomeMessage}">
      <div class="welcome-message">
          ${sessionScope.welcomeMessage}
      </div>
      <c:remove var="welcomeMessage" scope="session" />
  </c:if>

  <!-- 상품 목록 -->
  <div class="container">
      <div class="goods-container">
          <c:forEach var="item" items="${goodsList}" varStatus="status" begin="0" end="19">
              <div class="goods-item">
                  <div class="image-box">
                      <img alt="상품 이미지" src="${contextPath}/common/largeImage.do?product_id=${item.product_id}&fileName=${item.fileName}">
                  </div>
                  <div class="info">
                      <div class="name">
                          <a href="${contextPath}/goods/detailInfo.do?product_id=${item.product_id}">
                              ${item.product_name}
                          </a>
                      </div>
                      <div class="price">${item.price}원</div>
                  </div>
              </div>
          </c:forEach>
      </div>
  </div>

</body>
</html>
