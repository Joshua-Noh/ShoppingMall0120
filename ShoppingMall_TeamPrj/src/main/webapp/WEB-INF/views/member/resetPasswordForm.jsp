<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>비밀번호 재설정</title>
  <!-- Google Fonts: Playfair Display (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <style>
    /* Reset 및 기본 스타일 */
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body {
      font-family: 'Roboto', sans-serif;
      background-color: #ffffff;
      color: #333333;
      line-height: 1.6;
    }
    .container {
      width: 400px;
      margin: 50px auto;
      padding: 30px;
      background-color: #ffffff;
      border: 1px solid #dddddd;
      border-radius: 8px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      text-align: center;
    }
    h2 {
      font-family: 'Playfair Display', serif;
      font-size: 2rem;
      margin-bottom: 20px;
      color: #222222;
    }
    .form-group {
      margin-bottom: 15px;
      text-align: left;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    .form-group input {
      width: 100%;
      padding: 10px;
      border: 1px solid #cccccc;
      border-radius: 4px;
      font-size: 1rem;
    }
    .btn {
      display: block;
      width: 100%;
      padding: 12px;
      background-color: #333333;
      color: #ffffff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      margin-top: 20px;
      font-size: 1rem;
      transition: background 0.3s ease;
    }
    .btn:hover {
      background-color: #555555;
    }
    .error {
      color: #ff6f61;
      font-weight: bold;
      margin-bottom: 15px;
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>비밀번호 재설정</h2>
    <c:if test="${not empty error}">
      <p class="error">${error}</p>
    </c:if>
    <form action="<c:url value='/member/resetPassword.do'/>" method="post">
      <div class="form-group">
        <label for="newPassword">새 비밀번호</label>
        <input type="password" id="newPassword" name="newPassword" required />
      </div>
      <button type="submit" class="btn">비밀번호 재설정</button>
    </form>
  </div>
</body>
</html>
