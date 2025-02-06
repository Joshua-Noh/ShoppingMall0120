<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>FAQ 상세보기</title>
  
  <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <style>
    :root {
      --primary-color: #111;
      --secondary-color: #333;
      --accent-color: #d4af37;
      --bg-color: #f4f4f4;
      --container-bg: #fff;
      --btn-bg: #000;
      --btn-hover-bg: #333;
      --font-heading: 'Oswald', sans-serif;
      --font-body: 'Roboto', sans-serif;
    }
    
    /* 기본 리셋 */
    * { margin: 0; padding: 0; box-sizing: border-box; }
    
    body {
      font-family: var(--font-body);
      background: var(--bg-color);
      color: var(--secondary-color);
      padding: 20px;
      line-height: 1.6;
    }
    
    .container {
      max-width: 800px;
      margin: 40px auto;
      background: var(--container-bg);
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 4px 20px rgba(0,0,0,0.1);
    }
    
    h2.section-title {
      font-family: var(--font-heading);
      font-size: 2.5rem;
      margin-bottom: 20px;
      text-align: center;
      color: var(--primary-color);
      position: relative;
    }
    h2.section-title::after {
      content: '';
      display: block;
      width: 80px;
      height: 4px;
      background: var(--primary-color);
      margin: 10px auto 0;
      border-radius: 2px;
    }
    
    /* FAQ 상세보기 테이블 스타일 */
    .faq-table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 30px;
      font-size: 1rem;
    }
    .faq-table th, .faq-table td {
      padding: 15px;
      border: 1px solid #ddd;
      text-align: left;
    }
    .faq-table th {
      width: 120px;
      background: #f0f0f0;
      color: var(--primary-color);
      font-weight: bold;
    }
    
    /* 모던 플랫 버튼 스타일 (크기 축소) */
    .back-link {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      background-color: var(--btn-bg);
      color: #fff;
      padding: 8px 14px;  /* 기존보다 작게 */
      border: none;
      border-radius: 5px;
      text-decoration: none;
      font-size: 0.9rem;
      transition: background 0.3s, transform 0.2s;
    }
    .back-link:hover {
      background-color: var(--btn-hover-bg);
      transform: translateY(-2px);
    }
    
    /* 반응형 디자인 */
    @media (max-width: 768px) {
      .container { margin: 20px; padding: 20px; }
      h2.section-title { font-size: 2rem; }
      .faq-table th, .faq-table td { padding: 12px; }
      .back-link { font-size: 0.85rem; padding: 6px 12px; }
    }
  </style>
  
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      console.log("FAQ 상세보기 페이지 로드 완료");
      // 추가 JavaScript 코드 (필요 시)
    });
  </script>
  
</head>
<body>
  <div class="container">
    <h2 class="section-title">FAQ 상세보기</h2>
    <table class="faq-table">
      <tr>
        <th>질문</th>
        <td>${faq.question}</td>
      </tr>
      <tr>
        <th>답변</th>
        <td>${faq.answer}</td>
      </tr>
    </table>
    <a class="back-link" href="${contextPath}/customerCenter/faq/list.do">
      <span class="material-icons" style="vertical-align: middle; font-size: 1rem;">arrow_back</span>
      목록으로 돌아가기
    </a>
  </div>
</body>
</html>
