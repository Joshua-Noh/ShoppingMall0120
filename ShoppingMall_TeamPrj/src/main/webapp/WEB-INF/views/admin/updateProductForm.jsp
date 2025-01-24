<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insert Product</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        .form-container {
            background-color: #fff;
            padding: 20px 30px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px;
            position: relative;
        }
        .form-container h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }
        .form-container input, .form-container select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-container button {
            width: 100%;
            padding: 10px;
            background-color: #333;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-container button:hover {
            background-color: #555;
        }
        .form-container .category-guide {
            position: absolute;
            top: 20px;
            right: -170px;
            width: 150px;
            background-color: #f5f5f5;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            text-align: left;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-container select {
            appearance: none;
            background: url('data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="%23333"%3E%3Cpath d="M7 10l5 5 5-5H7z"/%3E%3C/svg%3E') no-repeat right 10px center;
            background-color: #fff;
            background-size: 16px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>Update Product</h1>
    <form action="${contextPath}/admin/updateProduct.do" method="post">
        <!-- Hidden input으로 Product ID 전달 -->
        <input type="hidden" name="productId" value="${product.productId}" />
        
        <!-- 기존 데이터를 value 속성에 표시 -->
        <input type="text" name="productName" value="${product.productName}" placeholder="제품 이름"  required/>
        <input type="number" name="categoryId" value="${product.categoryId}" placeholder="카테고리 아이디(오른쪽 참고)" required />
        <input type="text" name="price" value="${product.price}" placeholder="가격"  />
        
        <select name="size" required>
            <option value="S" ${product.size == 'S' ? 'selected' : ''}>Small (S)</option>
            <option value="M" ${product.size == 'M' ? 'selected' : ''}>Medium (M)</option>
            <option value="L" ${product.size == 'L' ? 'selected' : ''}>Large (L)</option>
            <option value="XL" ${product.size == 'XL' ? 'selected' : ''}>Extra Large (XL)</option>
        </select>
        
        <button type="submit">Update</button>
    </form>
    <div class="category-guide">
        <strong>Category Guide:</strong>
        <ul>
            <li>1: 신상품</li>
            <li>2: 상의</li>
            <li>3: 하의</li>
        </ul>
    </div>
</div>

</body>
</html>
