<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원 목록 - 관리자 전용</title>
  
  <!-- Google Fonts: Playfair Display (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
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
      width: 95%;
      max-width: 1000px;
      margin: 50px auto;
      background-color: #ffffff;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    }
    h2, h3 {
      text-align: center;
      margin-bottom: 30px;
      font-family: 'Playfair Display', serif;
      color: #222222;
    }
    table {
      width: 100%;
      border-collapse: separate;
      border-spacing: 0 10px;
      margin-bottom: 30px;
      background-color: #ffffff;
    }
    th, td {
      padding: 15px;
      text-align: left;
      background-color: #f7f7f7;
      border: 1px solid #dddddd;
      color: #333333;
    }
    /* 이름 칸(두 번째 열) 너비 늘리기 */
    th:nth-child(2), td:nth-child(2) {
      width: 20000px; /* 원하는 너비로 조절 (예: 200px) */
    }
    th {
      background-color: #eeeeee;
      font-weight: bold;
    }
    tr {
      border-radius: 8px;
      overflow: hidden;
    }
    a {
      text-decoration: none;
      color: inherit;
    }
    a:hover {
      color: #555555;
    }
    button {
      padding: 10px 16px;
      background-color: #000000;
      color: #ffffff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 0.9rem;
      font-family: 'Roboto', sans-serif;
      transition: background 0.3s ease;
    }
    button:hover {
      background-color: #333333;
    }
    .center { text-align: center; }
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
      // fade-in 효과 적용
      document.body.classList.add("fade-in");
    });
  </script>
</head>
<body>
  <!-- 관리자 권한 체크: 관리자 계정이 아니라면 경고창 후 메인 페이지로 리다이렉트 -->
  <c:if test="${sessionScope.memberInfo == null || sessionScope.memberInfo.role != 'ADMIN'}">
    <script type="text/javascript">
      alert("사용권한이 없습니다.");
      location.href = '${contextPath}/main/main.do';
    </script>
  </c:if>
  
  <div class="container">
    <h2>관리자 - 회원 목록</h2>
    <c:if test="${not empty membersList}">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>이름</th>
            <th>비밀번호</th>
            <th>이메일</th>
            <th>휴대전화번호</th>
            <th>주소</th>
            <th>생년월일</th>
            <th>가입일</th>
            <th>성별</th>
            <th>수정</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="member" items="${membersList}">
            <tr>
              <td>${member.user_id}</td>
              <td>${member.user_name}</td>
              <td>${member.password}</td>
              <td>${member.email}</td>
              <td>${member.phone_number}</td>
              <td>${member.address}</td>
              <td>${member.date_of_birth}</td>
              <td>${member.join_date}</td>
              <td>${member.gender}</td>
              <td class="center">
                <!-- 수정 버튼: 수정 폼에 해당 회원의 user_id 파라미터 전달 -->
                <a href="${contextPath}/member/updateMemberForm.do?user_id=${member.user_id}">
                  <button type="button" class="action-btn">수정</button>
                </a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>
  </div>
</body>
</html>
