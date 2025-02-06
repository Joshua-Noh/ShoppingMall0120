<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원 이메일 확인</title>
  
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
    p {
      font-size: 1.1rem;
      color: #555555;
      margin-bottom: 10px;
    }
    p strong {
      color: #d4af37;
    }
    .error {
      color: #ff6f61;
      font-weight: bold;
      margin-bottom: 15px;
    }
    /* fade-in 애니메이션 */
    .fade-in {
      opacity: 0;
      animation: fadeIn 1s forwards;
    }
    @keyframes fadeIn {
      to { opacity: 1; }
    }
  </style>
  
  <script>
    document.addEventListener("DOMContentLoaded", function() {
      // 페이지 로드시 fade-in 효과 적용
      document.body.classList.add("fade-in");
    });
  </script>
</head>
<body class="fade-in">
  <div class="container">
    <h2>회원 이메일 확인</h2>
    <c:choose>
      <c:when test="${not empty email}">
        <p>회원 이메일: <strong>${email}</strong></p>
      </c:when>
      <c:otherwise>
        <p class="error">${error}</p>
      </c:otherwise>
    </c:choose>
    <!-- 이메일 확인 후, 비밀번호 재설정 페이지로 이동하는 링크 추가 -->
    <c:if test="${not empty email}">
      <p>
        <a href="${pageContext.request.contextPath}/member/resetPassword.do" style="color: #d4af37; text-decoration: none; font-weight: bold;">
          비밀번호 재설정하기 &raquo;
        </a>
      </p>
    </c:if>
  </div>
</body>
</html>
