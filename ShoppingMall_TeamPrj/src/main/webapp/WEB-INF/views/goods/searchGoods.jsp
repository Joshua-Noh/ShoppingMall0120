<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 컨텍스트 경로 설정 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>검색 결과</title>
    <style>
        /* 기본 스타일 */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        /* 섹션 스타일 */
        .search-results {
            padding: 20px;
            background-color: #ffffff; /* 검정 테마 */
        }

        .search-results h3 {
            color: #fff;
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
        }

        /* 상품 리스트 */
        .search-results .result-item {
            background-color: #fff;
            border-radius: 10px;
            padding: 10px;
            text-align: center;
            width: 220px;
            margin: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .search-results .result-item:hover {
            transform: scale(1.05); /* 마우스 올리면 확대 */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
        }

        /* 상품 이미지 */
        .search-results .result-item img {
            width: 100%;
            height: auto;
            border-radius: 5px;
        }

        /* 상품 제목 */
        .search-results .result-item .title {
            font-size: 16px;
            font-weight: bold;
            margin: 10px 0;
            color: #333;
        }

        /* 상품 가격 */
        .search-results .result-item .price {
            font-size: 14px;
            color: #d9534f;
            font-weight: bold;
        }

        /* 상품 리스트 그리드 */
        .result-grid {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }
    </style>
</head>
<body>
    <section class="search-results">
        <h3>검색 결과</h3>
        <div class="result-grid">
            <c:forEach var="item" items="${goodsList}">
                <div class="result-item">
                    <!-- 상품 이미지 -->
                    <img src="${contextPath}/thumbnails.do?goods_id=${item.product_id}&fileName=${item.fileName}" alt="${item.product_name}" />

                    <!-- 상품 제목 -->
                    <div class="title">${item.product_name}</div>

                    <!-- 상품 가격 -->
                    <div class="price">
                        <fmt:formatNumber value="${item.price}" type="number" />원
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</body>
</html>
