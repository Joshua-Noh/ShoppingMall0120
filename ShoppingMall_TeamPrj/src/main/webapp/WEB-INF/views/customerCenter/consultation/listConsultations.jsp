<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>1:1 상담 게시판</title>
  <!-- Google Fonts & Material Icons -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <style>
    /* 기본 리셋 */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    
    /* body에 대각선 배경: 상단/좌측 흰색, 하단/우측 검정 */
    body {
        font-family: 'Roboto', sans-serif;
        background: linear-gradient(135deg, #ffffff 50%, #000000 50%);
        color: #333;
        min-height: 100vh;
        margin: 20px;
    }
    
    /* 컨테이너 영역 */
    .container {
        max-width: 900px;
        margin: 0 auto;
        background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }
    
    h2 {
        font-family: 'Oswald', sans-serif;
        margin-bottom: 25px;
        text-align: center;
        font-size: 2rem;
        color: #333;
    }
    
    /* 링크 스타일 */
    a {
        text-decoration: none;
        color: #007BFF;
        transition: color 0.3s;
        display: inline-flex;
        align-items: center;
        gap: 5px;
    }
    a:hover {
        color: #0056b3;
    }
    
    /* 테이블 스타일 (모던 디자인 적용) */
    table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0 8px;
    }
    th, td {
        text-align: left;
        padding: 15px;
    }
    thead th {
        background-color: #333;
        color: #fff;
        font-size: 1.1rem;
    }
    tbody td {
        background-color: #f9f9f9;
        border-bottom: 2px solid #e0e0e0;
    }
    tbody tr {
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
    }
    tbody tr:hover td {
        background-color: #f1f1f1;
    }
    
    /* 반응형 디자인 */
    @media (max-width: 768px) {
        h2 {
            font-size: 1.75rem;
        }
        th, td {
            padding: 12px;
        }
    }
  </style>
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script>
    $(document).ready(function(){
        // URL 파라미터에서 error 파라미터를 확인하여 로그인 필요 메시지 표시
        var params = new URLSearchParams(window.location.search);
        if (params.get("error") === "loginRequired") {
            alert("상담 문의를 작성하려면 로그인이 필요합니다.");
        }
    });
  </script>
</head>
<body>
  <div class="container">
    <h2>1:1 상담 게시판</h2>
    <!-- 새 상담 문의 작성 링크 (오른쪽 정렬) -->
    <p style="text-align: right; margin-bottom: 20px;">
      <a href="${pageContext.request.contextPath}/customerCenter/consultation/form.do">
        <span class="material-icons" style="font-size: 1.2rem;">create</span>
        새 상담 문의 작성
      </a>
    </p>
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>상태</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="consultation" items="${consultationList}" varStatus="status">
          <tr>
            <td>${status.index + 1}</td>
            <td>
              <a href="${pageContext.request.contextPath}/customerCenter/consultation/detail.do?consultationId=${consultation.consultationId}">
                ${consultation.subject}
              </a>
            </td>
            <td>${consultation.userName}</td>

            <td>${consultation.createdAt}</td>
            <td>${consultation.status}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>
