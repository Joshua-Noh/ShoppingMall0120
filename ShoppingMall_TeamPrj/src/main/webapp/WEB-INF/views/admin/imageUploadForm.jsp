<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품 이미지 업로드</title>
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
    
    .upload-container {
      background-color: #fff;
      padding: 25px 30px;
      border: 1px solid #ddd;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      text-align: center;
      width: 400px;
    }
    
    .upload-container h1 {
      font-family: 'Oswald', sans-serif;
      font-size: 22px;
      color: #333;
      margin-bottom: 20px;
    }
    
    .upload-container form {
      display: flex;
      flex-direction: column;
    }
    
    .upload-container select,
    .upload-container input,
    .upload-container button {
      padding: 12px;
      margin-bottom: 15px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    
    /* 커스텀 드롭다운 아이콘 */
    .upload-container select {
      appearance: none;
      background: url('data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="%23333"%3E%3Cpath d="M7 10l5 5 5-5H7z"/%3E%3C/svg%3E') no-repeat right 10px center;
      background-color: #fff;
      background-size: 16px;
    }
    
    .upload-container button {
      background-color: #333;
      color: #fff;
      border: none;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    
    .upload-container button:hover {
      background-color: #555;
    }
  </style>
</head>
<body>
  <div class="upload-container">
    <h1>상품 이미지 업로드</h1>
    <form method="post" action="${contextPath}/admin/upload" enctype="multipart/form-data">
      <!-- 카테고리 선택 (제품 등록 페이지와 동일한 옵션) -->
      <label>카테고리:</label>
      <select name="category" required>
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

      <!-- 전달받은 product_id를 hidden 필드로 포함 (addProduct()에서 쿼리파라미터로 전달받음) -->
      <input type="hidden" name="product_id" value="${param.product_id}" />

      <!-- 파일 업로드 필드 -->
      <label>이미지 선택:</label>
      <input type="file" name="file" required>

      <!-- 업로드 버튼 -->
      <button type="submit" class="upload-btn">이미지 업로드</button>
    </form>
  </div>
</body>
</html>
