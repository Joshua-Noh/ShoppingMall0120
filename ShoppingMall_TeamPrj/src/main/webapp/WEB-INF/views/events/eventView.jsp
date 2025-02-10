<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>이벤트 목록</title>
  
  <!-- Google Fonts 및 Material Icon 설정 (원본과 동일) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
  
  <!-- 동일한 CSS 스타일 (원본 참조) -->
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body {
      background-color: #fff;
      color: #000;
      font-family: 'Roboto', sans-serif;
      line-height: 1.6;
    }
    .container2 {
      max-width: 800px;
      margin: 80px auto 40px;
      padding: 30px 20px;
      background-color: #fff;
      border: 2px solid #000;
      border-radius: 8px;
      box-shadow: 0 8px 16px rgba(0,0,0,0.2);
      transition: box-shadow 0.3s ease, transform 0.3s ease;
      opacity: 0;
      transform: translateY(30px);
    }
    .container2:hover {
      box-shadow: 0 12px 30px rgba(0,0,0,0.3);
      transform: translateY(0);
    }
    h1 {
      font-family: 'Oswald', sans-serif;
      font-size: 2.4rem;
      color: #000;
      margin-bottom: 20px;
      border-bottom: 2px solid #000;
      padding-bottom: 10px;
      text-align: center;
      position: relative;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
      font-size: 0.95rem;
    }
    table thead {
      background-color: #000;
      color: #fff;
    }
    table thead th {
      padding: 10px;
      border: 1px solid #000;
    }
    table tbody tr {
      border: 1px dotted #000;
    }
    table tbody td {
      padding: 10px;
      text-align: center;
      border: 1px solid #000;
      background-color: #fff;
      color: #000;
    }
    table tbody tr:hover td {
      background-color: #f2f2f2;
    }
    button, input[type="button"] {
      padding: 8px 16px;
      background-color: #000;
      color: #fff;
      border: 1px solid #000;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
      transition: background-color 0.3s, color 0.3s;
    }
    button:hover, input[type="button"]:hover {
      background-color: #fff;
      color: #000;
    }
    @media (max-width: 768px) {
      .container2 { width: 95%; margin-top: 100px; padding: 20px; }
      table, th, td { font-size: 0.85rem; }
      h1 { font-size: 2rem; }
    }
    .fade-in { animation: fadeInUp 0.8s forwards; }
    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(30px); }
      to { opacity: 1; transform: translateY(0); }
    }
  </style>
  
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      var container = document.getElementById('eventContainer');
      if(container){
        container.classList.add('fade-in');
      }
    });
  </script>
  
</head>
<body>
  <!-- 이벤트 목록 영역 -->
  <div class="container2" id="eventContainer">
    <h1>이벤트 목록</h1>
    <table>
      <thead>
        <tr>
          <th>이벤트 ID</th>
          <th>이벤트 이름</th>
          <th>설명</th>
          <th>유형</th>
          <th>시작일</th>
          <th>종료일</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty eventList}">
            <tr>
              <td colspan="6" style="color:#000;"><strong>등록된 이벤트가 없습니다.</strong></td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="event" items="${eventList}">
              <tr>
                <td>${event.eventId}</td>
                <td>${event.eventName}</td>
                <td>${event.eventDescription}</td>
                <td>${event.eventType}</td>
                <td><fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
    <button onclick="location.href='${contextPath}/events/createEventForm.do'">새 이벤트 등록</button>
  </div>
</body>
</html>
