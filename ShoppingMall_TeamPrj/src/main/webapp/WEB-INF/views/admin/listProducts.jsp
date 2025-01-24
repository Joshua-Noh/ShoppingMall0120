<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #333;
            color: #fff;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .add-product-button {
            margin: 20px 0;
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #444;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s;
        }
        .add-product-button:hover {
            background-color: #222;
        }
        .delete-btn {
            background-color: #e74c3c;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 5px 10px;
            cursor: pointer;
            font-size: 14px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            transition: background-color 0.3s;
        }
        .delete-btn:hover {
            background-color: #c0392b;
        }
        .edit-btn {
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 5px 10px;
            cursor: pointer;
            font-size: 14px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            transition: background-color 0.3s;
        }
        .edit-btn:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <h1>상품 목록</h1>
    
    <!-- 상품 추가하기 버튼 -->
    <a href="addProductForm.do" class="add-product-button">상품 추가하기</a>

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
                    <a href="${contextPath}/admin/updateProductForm.do?productId=${product.productId}" 
                       class="edit-btn">수정</a>
                    </td>
                    <td>
                        <!-- 삭제 버튼 -->
                        <a href="${contextPath}/admin/removeProduct.do?productId=${product.productId}" 
                           class="delete-btn">삭제</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
