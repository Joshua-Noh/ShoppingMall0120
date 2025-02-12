<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품 등록</title>
  <!-- Google Fonts: Oswald for headings, Roboto for body -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
  <style>
    /* Reset */
    * { margin: 0; padding: 0; box-sizing: border-box; }
    
    body {
      font-family: 'Roboto', sans-serif;
      background-color: #f9f9f9;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      padding: 20px;
    }
    
    .form-container {
      background-color: #fff;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 30px;
      width: 400px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      position: relative;
    }
    
    .form-container h1 {
      font-family: 'Oswald', sans-serif;
      font-size: 24px;
      color: #333;
      text-align: center;
      margin-bottom: 20px;
    }
    
    .form-container form {
      display: flex;
      flex-direction: column;
    }
    
    .form-container input,
    .form-container select {
      padding: 12px;
      margin-bottom: 15px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    
    .form-container button {
      padding: 12px;
      background-color: #333;
      color: #fff;
      border: none;
      border-radius: 4px;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    
    .form-container button:hover {
      background-color: #555;
    }
    
    /* 카테고리 가이드 (옵션) */
    .category-guide {
      font-size: 14px;
      color: #777;
      margin-top: 10px;
    }
  </style>
</head>
<body>
  <div class="form-container">
    <h1>상품 등록</h1>
    <form action="${contextPath}/admin/addProduct.do" method="post">
      <input type="text" name="productName" placeholder="제품 이름" required />
      
      <!-- 카테고리 선택: 테이블에 맞게 수정 (1: 티셔츠, 2: 셔츠/블라우스, ... 10: 스포츠웨어) -->
      <select name="categoryId" required>
         <option value="" disabled selected>카테고리를 선택하세요</option>
         <option value="1">티셔츠</option>
         <option value="2">셔츠/블라우스</option>
         <option value="3">니트/스웨터</option>
         <option value="4">팬츠</option>
         <option value="5">스커트</option>
         <option value="6">재킷</option>
         <option value="7">코트</option>
         <option value="8">원피스</option>
         <option value="9">정장</option>
         <option value="10">스포츠웨어</option>
      </select>
      
      <input type="text" name="price" placeholder="가격" required />
      
      <select name="size" required>
        <option value="" disabled selected>사이즈 선택</option>
        <option value="S">Small (S)</option>
        <option value="M">Medium (M)</option>
        <option value="L">Large (L)</option>
        <option value="XL">Extra Large (XL)</option>
      </select>
      
      <button type="submit">등록하기</button>
    </form>
    <!-- 카테고리 가이드 (옵션) -->
    <div class="category-guide">
      <strong>카테고리 가이드:</strong>
      <ul>
        <li>1: 티셔츠</li>
        <li>2: 셔츠/블라우스</li>
        <li>3: 니트/스웨터</li>
        <li>4: 팬츠</li>
        <li>5: 스커트</li>
        <li>6: 재킷</li>
        <li>7: 코트</li>
        <li>8: 원피스</li>
        <li>9: 정장</li>
        <li>10: 스포츠웨어</li>
      </ul>
    </div>
  </div>
</body>
</html>
