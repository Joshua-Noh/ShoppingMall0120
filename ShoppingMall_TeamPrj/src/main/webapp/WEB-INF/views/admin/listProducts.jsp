<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 목록</title>
    <!-- Google Fonts: Oswald (제목) & Roboto (본문) -->
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        /* CSS 변수 및 Google Fonts 적용 */
        :root {
            --primary-color: #333;
            --secondary-color: #fff;
            --accent-color: #3498db;       /* 수정 버튼 색상 */
            --accent-hover: #2980b9;
            --danger-color: #e74c3c;       /* 삭제 버튼 색상 */
            --danger-hover: #c0392b;
            --background-color: #f5f5f5;
            --light-gray: #f9f9f9;
            --border-color: #ddd;
            --font-family-base: 'Roboto', sans-serif;
            --font-family-heading: 'Oswald', sans-serif;
            --transition-duration: 0.3s;
            --border-radius: 8px;
            --box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        
        /* 기본 스타일 */
        body {
            font-family: var(--font-family-base);
            background-color: var(--background-color);
            color: var(--primary-color);
            margin: 0;
            padding: 20px;
        }
        
        h1 {
            font-family: var(--font-family-heading);
            text-align: center;
            margin-bottom: 20px;
        }
        
        /* 테이블 컨테이너: 모바일 등에서 가로 스크롤 처리 */
        .table-container {
            width: 100%;
            overflow-x: auto;
            margin-bottom: 20px; /* 하단 여백 추가 */
        }
        
        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: var(--secondary-color);
            box-shadow: var(--box-shadow);
            border-radius: var(--border-radius);
            /* overflow: hidden;  --> 하단 잘림 문제를 해결하기 위해 제거 */
        }
        
        th, td {
            border: 1px solid var(--border-color);
            padding: 12px 10px;
            text-align: center;
        }
        
        th {
            background-color: var(--primary-color);
            color: var(--secondary-color);
            font-weight: bold;
        }
        
        tr:nth-child(even) {
            background-color: var(--light-gray);
        }
        
        tr:hover {
            background-color: #eaeaea;
            transition: background-color var(--transition-duration);
        }
        
        /* 상품 추가하기 버튼 */
        .add-product-button {
            margin: 20px 0;
            display: inline-block;
            padding: 12px 24px;
            font-size: 16px;
            color: var(--secondary-color);
            background-color: #444;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            box-shadow: var(--box-shadow);
            transition: background-color var(--transition-duration);
        }
        
        .add-product-button:hover {
            background-color: #222;
        }
        
        /* 수정 버튼 */
        .edit-btn {
            background-color: var(--accent-color);
            color: var(--secondary-color);
            border: none;
            border-radius: 4px;
            padding: 6px 12px;
            cursor: pointer;
            font-size: 14px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            transition: background-color var(--transition-duration);
            text-decoration: none;
            display: inline-block;
        }
        
        .edit-btn:hover {
            background-color: var(--accent-hover);
        }
        
        /* 삭제 버튼 */
        .delete-btn {
            background-color: var(--danger-color);
            color: var(--secondary-color);
            border: none;
            border-radius: 4px;
            padding: 6px 12px;
            cursor: pointer;
            font-size: 14px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            transition: background-color var(--transition-duration);
            text-decoration: none;
            display: inline-block;
        }
        
        .delete-btn:hover {
            background-color: var(--danger-hover);
        }
        
        /* 반응형: 작은 화면에서도 여백 조절 */
        @media (max-width: 768px) {
            body {
                padding: 10px;
            }
            
            th, td {
                padding: 10px 8px;
            }
            
            .add-product-button {
                padding: 10px 20px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <h1>상품 목록</h1>
    
    <!-- 상품 추가하기 버튼 -->
    <a href="addProductForm.do" class="add-product-button">상품 추가하기</a>
    
    <!-- 테이블을 감싸는 컨테이너를 추가하여 가로 스크롤 및 하단 여백 보장 -->
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>상품 ID</th>
                    <th>상품 이름</th>
                    <th>카테고리 ID</th>
                    <th>가격</th>
                    <th>사이즈</th>
                    <th>생성일</th>
                    <th>수정</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <!-- JSTL c:forEach를 사용하여 데이터 출력 -->
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.productName}</td>
                        <td>${product.categoryId}</td>
                        <td>${product.price}</td>
                        <td>${product.size}</td>
                        <td>${product.createdAt}</td>
                        <td>
                            <a href="${contextPath}/admin/updateProductForm.do?productId=${product.productId}" class="edit-btn">수정</a>
                        </td>
                        <td>
                            <a href="${contextPath}/admin/removeProduct.do?productId=${product.productId}" class="delete-btn">삭제</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
