<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8" 
    isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 컨텍스트 경로 설정 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- 만약 세션에 welcomeMessage가 있다면 표시 -->
<c:if test="${not empty sessionScope.welcomeMessage}">
    <div class="welcome-message">
        ${sessionScope.welcomeMessage}
    </div>
    <c:remove var="welcomeMessage" scope="session" />
</c:if>

<!-- body 부분 -->
<div class="container">
    <div class="goods-container">
        <c:forEach var="item" items="${goodsList}">
            <div class="goods-item">
                <!-- 상품 이미지 (클릭 시 상세 페이지로 이동) -->
                <a href="${contextPath}/goods/detailInfo.do?product_id=${item.product_id}">
                    <img alt="상품 이미지" src="${contextPath}/common/thumbnails.do?product_id=${item.product_id}&fileName=${item.fileName}">
                </a>
                <div class="name">
                    <!-- 상품 이름 (클릭 시 상세 페이지로 이동) -->
                    <a href="${contextPath}/goods/detailInfo.do?product_id=${item.product_id}">
                        ${item.product_name}
                    </a>
                </div>
                <div class="price">
                    <fmt:formatNumber value="${item.price}" type="number" />원
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- 내부 스타일 (개별 페이지에만 적용하려면 여기 작성, 아니면 별도 CSS 파일로 분리) -->
<style>
    /* 기본 리셋 */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f9f9f9;
        color: #333;
    }
    /* 컨테이너 - 전체 페이지 영역(헤더/푸터는 Tiles로 처리) */
    .container {
        width: 1200px;
        margin: 20px auto;
        padding: 20px;
    }
    /* 상품 목록을 CSS Grid로 고정형 배치 */
    .goods-container {
        display: grid;
        grid-template-columns: repeat(4, 250px); /* 4열, 각 카드 250px 고정 */
        gap: 40px;  /* 카드 간 간격 40px */
        justify-content: center;
    }
    /* 개별 상품 카드 */
    .goods-item {
        background-color: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        text-align: center;
        transition: transform 0.2s, box-shadow 0.2s;
        width: 250px;
        height: 380px;  /* 높이를 약간 높게 잡아서 텍스트가 잘리지 않도록 함 */
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .goods-item:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }
    /* 상품 이미지: 고정 높이와 object-fit으로 균일하게 처리 */
    .goods-item img {
        width: 100%;
        height: 200px; 
        object-fit: cover;
        border-radius: 4px;
    }
    /* 상품명: 텍스트가 너무 길면 말줄임 처리 */
    .goods-item .name {
        margin: 15px 0 10px;
        font-size: 1.1em;
        font-weight: bold;
        min-height: 40px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .goods-item .name a {
        text-decoration: none;
        color: #333;
        transition: color 0.2s;
    }
    .goods-item .name a:hover {
        color: #1976d2;
    }
    .goods-item .price {
        color: #e53935;
        font-size: 1.1em;
    }
    /* welcome-message 스타일 */
    .welcome-message {
        text-align: center;
        color: #2e7d32;
        font-size: 1.2em;
        margin: 20px 0;
    }
</style>
