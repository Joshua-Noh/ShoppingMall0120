<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>FAQ 게시판</title>
  
  <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <style>
    /* CSS 커스텀 프로퍼티로 색상 및 폰트 관리 */
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
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    
    body {
      font-family: var(--font-body);
      background: var(--bg-color);
      color: var(--secondary-color);
      padding: 20px;
      line-height: 1.6;
    }
    
    .container {
      max-width: 1000px;
      margin: 0 auto;
      background: var(--container-bg);
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    }
    
    h1 {
      font-family: var(--font-heading);
      font-size: 3rem;
      color: var(--primary-color);
      text-align: center;
      margin-bottom: 30px;
    }
    
    h2.section-title {
      font-family: var(--font-heading);
      font-size: 2.5rem;
      color: var(--primary-color);
      text-align: center;
      margin-bottom: 20px;
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
    
    /* FAQ 목록 테이블 */
    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 30px;
      font-size: 1rem;
    }
    table thead {
      background: var(--primary-color);
      color: #fff;
    }
    table thead th {
      padding: 15px;
      border: 1px solid var(--primary-color);
    }
    table tbody td {
      padding: 15px;
      border: 1px solid #ddd;
      text-align: center;
      background: #fff;
    }
    table tbody tr:hover td {
      background: #f0f0f0;
    }
    
    /* 모던 플랫 버튼 스타일 */
    .btn {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      background-color: var(--btn-bg);
      color: #fff;
      padding: 12px 20px;
      border: none;
      border-radius: 5px;
      text-decoration: none;
      font-size: 1.1rem;
      transition: background 0.3s, transform 0.2s;
    }
    .btn:hover {
      background-color: var(--btn-hover-bg);
      transform: translateY(-2px);
    }
    
    /* 버튼 컨테이너: Flexbox로 수평 정렬 */
    .btn-container {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin-top: 40px;
    }
    
    /* 반응형 디자인 */
    @media (max-width: 768px) {
      h1 { font-size: 2.5rem; }
      h2.section-title { font-size: 2rem; }
      .btn { font-size: 1rem; padding: 10px 16px; }
      table thead th, table tbody td { padding: 12px; }
    }
  </style>
  
  <!-- jQuery (최신 버전 권장) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script>
    $(document).ready(function(){
      // 추가 JS (필요 시 애니메이션, 이벤트 핸들러 등을 추가)
      console.log("FAQ 페이지 로드 완료");
    });
  </script>
  
</head>
<body>
  <div class="container">
    <h1>FAQ 게시판</h1>
    
    <!-- FAQ 목록 출력 -->
    <c:if test="${not empty faqList}">
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>질문</th>
            <th>등록일</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="faq" items="${faqList}" varStatus="status">
            <tr>
              <td>${status.index + 1}</td>
              <td>
                <a href="${contextPath}/customerCenter/faq/detail.do?faqId=${faq.faqId}" style="color: var(--primary-color); text-decoration: none;">
                  ${faq.question}
                </a>
              </td>
              <td>${faq.createdAt}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>
    <c:if test="${empty faqList}">
      <p style="text-align: center;">등록된 FAQ가 없습니다.</p>
    </c:if>
    
    <!-- 조건부 버튼: ADMIN이면 "새 FAQ 등록", 일반 사용자이면 "1:1 상담하기" -->
    <c:choose>
      <c:when test="${sessionScope.memberInfo.role eq 'ADMIN'}">
        <div class="btn-container">
          <a class="btn" href="${contextPath}/customerCenter/faq/form.do">
            <span class="material-icons" style="font-size: 1.2rem;">note_add</span>
            새 FAQ 등록
          </a>
        </div>
      </c:when>
      <c:otherwise>
        <div class="btn-container">
          <a class="btn" href="${contextPath}/customerCenter/consultation/form.do">
            <span class="material-icons" style="font-size: 1.2rem;">create</span>
            1:1 상담하기
          </a>
        </div>
      </c:otherwise>
    </c:choose>
    
  </div>
</body>
</html>
