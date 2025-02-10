<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원 정보 및 포인트 거래 내역</title>
  
  <!-- Google Fonts 및 Material Icon 설정 -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
  
  <!-- 동일한 CSS 스타일 -->
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
    .info-table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
    }
    .info-table td {
      padding: 10px;
      font-size: 14px;
      color: #000;
      border-bottom: 1px solid #000;
    }
    .info-table tr:not(:last-child) td { border-bottom: 1px solid #000; }
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
    table tbody tr { border: 1px dotted #000; }
    table tbody td {
      padding: 10px;
      text-align: center;
      border: 1px solid #000;
      background-color: #fff;
      color: #000;
    }
    table tbody tr:hover td { background-color: #f2f2f2; }
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
      table, th, td, .info-table td { font-size: 0.85rem; }
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
      var container = document.getElementById('membershipContainer');
      if(container){
        container.classList.add('fade-in');
      }
    });
  </script>
  
</head>
<body>
  <!-- 회원 정보 및 포인트 거래 내역 영역 -->
  <div class="container2" id="membershipContainer">
    <h1>회원 정보</h1>
    <table class="info-table">
      <tr>
        <td style="width:150px;">이름:</td>
        <td><strong>${memberInfo.user_name}</strong></td>
      </tr>
      <tr>
        <td>이메일:</td>
        <td><strong>${memberInfo.email}</strong></td>
      </tr>
      <tr>
        <td>전화번호:</td>
        <td><strong>${memberInfo.phone_number}</strong></td>
      </tr>
      <tr>
        <td>주소:</td>
        <td><strong>${memberInfo.address}</strong></td>
      </tr>
      <tr>
        <td>생년월일:</td>
        <td><strong>${memberInfo.date_of_birth}</strong></td>
      </tr>
      <tr>
        <td>멤버십 등급:</td>
        <td><strong>${memberInfo.membershipLevel}</strong></td>
      </tr>
      <tr>
        <td>포인트:</td>
        <td><strong>${memberInfo.membershipPoints}</strong></td>
      </tr>
    </table>
    
    <h2>포인트 거래 내역</h2>
    <table>
      <thead>
        <tr>
          <th>거래 ID</th>
          <th>포인트 변화</th>
          <th>거래 유형</th>
          <th>거래 일시</th>
          <th>설명</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty transactionList}">
            <tr>
              <td colspan="5" style="color:#000;"><strong>거래 내역이 없습니다.</strong></td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="transaction" items="${transactionList}">
              <tr>
                <td>${transaction.transactionId}</td>
                <td>${transaction.pointsChange}</td>
                <td>${transaction.transactionType}</td>
                <td><fmt:formatDate value="${transaction.transactionDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td>${transaction.description}</td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
    <button onclick="location.href='${contextPath}/membership/updateMembershipForm.do?userId=${memberInfo.user_id}'">회원정보 수정</button>
    <button onclick="location.href='${contextPath}/membership/registerTransactionForm.do?userId=${memberInfo.user_id}'">포인트 거래 등록</button>
  </div>
</body>
</html>
