<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.Map, java.util.List" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Page - 주문내역 및 내 정보</title>
  
  <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <!-- Material Symbols Outlined -->
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">
  
  <style>
    /* 기본 리셋 */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    /* body: 화이트 배경, 기본 텍스트는 검정 */
    body {
      background-color: #fff;
      color: #000;
      font-family: 'Roboto', sans-serif;
      line-height: 1.6;
    }
    
    /* 메인 컨테이너 - Noir 스타일 (블랙 앤 화이트 톤) */
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
    
    /* 제목 스타일: Oswald, 검정 텍스트, 아래 언더라인 */
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
    h1 a {
      float: right;
      font-size: 0.9rem;
      color: #000;
      text-decoration: none;
      transition: text-decoration 0.3s;
    }
    h1 a:hover {
      text-decoration: underline;
    }
    
    /* 주문내역 테이블 스타일 */
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
      font-size: 14px;
      border: 1px solid #000;
    }
    table tbody tr {
      border: 1px dotted #000;
    }
    table tbody td {
      padding: 10px;
      text-align: center;
      font-size: 14px;
      border: 1px solid #000;
      background-color: #fff;
      color: #000;
    }
    table tbody tr:hover td {
      background-color: #f2f2f2;
    }
    
    /* 버튼 스타일 - 검정 배경, 흰색 텍스트 */
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
    
    /* 총 합계 스타일 */
    .total-price {
      font-size: 1.2rem;
      font-weight: bold;
      color: #000;
      text-align: right;
      margin-bottom: 30px;
    }
    
    /* 정보 테이블 스타일 */
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
    .info-table tr:not(:last-child) td {
      border-bottom: 1px solid #000;
    }
    
    /* 반응형 */
    @media (max-width: 768px) {
      .container2 {
        width: 95%;
        margin-top: 100px;
        padding: 20px;
      }
      table, th, td {
        font-size: 0.85rem;
      }
      h1 {
        font-size: 2rem;
      }
    }
    
    /* fade-in 애니메이션 (CSS 애니메이션 활용) */
    .fade-in {
      animation: fadeInUp 0.8s forwards;
    }
    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(30px); }
      to { opacity: 1; transform: translateY(0); }
    }
  </style>
  
  <!-- 주문 취소 스크립트 -->
  <script>
    function fn_cancel_order(order_id) {
      var answer = confirm("주문을 취소하시겠습니까?");
      if(answer) {
          var formObj = document.createElement("form");
          var i_order_id = document.createElement("input");
          i_order_id.name = "order_id";
          i_order_id.value = order_id;
          formObj.appendChild(i_order_id);
          document.body.appendChild(formObj);
          formObj.method = "post";
          formObj.action = "${contextPath}/mypage/cancelMyOrder.do";
          formObj.submit();
      }
    }
    
    document.addEventListener("DOMContentLoaded", function () {
      // 주문내역 영역과 나의 정보 영역 모두에 fade-in 효과 적용
      var orderContainer = document.getElementById('orderContainer');
      if(orderContainer){
        orderContainer.classList.add('fade-in');
      }
      var infoContainer = document.getElementById('infoContainer');
      if(infoContainer){
        infoContainer.classList.add('fade-in');
      }
    });
  </script>
  
</head>
<body>
  <!-- 헤더/푸터는 Tiles 레이아웃에서 처리된다고 가정 -->
  
  <!-- 최근 주문내역 영역 (container2) -->
  <div class="container2" id="orderContainer">
    <h1>
      최근 주문내역 
      <a href="${contextPath}/mypage/moreOrders.do" style="font-size:0.9rem; color:#000; text-decoration:none;">
        더보기 <img src="${contextPath}/resources/image/btn_more_see.jpg" alt="더보기" style="vertical-align: middle;"/>
      </a>
    </h1>
    <table>
      <thead>
        <tr>
          <th>주문번호</th>
          <th>주문일자</th>
          <th>주문상품</th>
          <th>주문상태</th>
          <th>주문취소</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty myOrderList}">
            <tr>
              <td colspan="5" style="color:#000;"><strong>주문한 상품이 없습니다.</strong></td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="item" items="${myOrderList}">
              <tr>
                <td>${item.order_id}</td>
                <td>${item.order_date}</td>
                <td>${item.product_name}</td>
                <td>
                  <c:choose>
                    <c:when test="${item.delivery_state == 'delivery_prepared'}">
                      배송준비중
                    </c:when>
                    <c:when test="${item.delivery_state == 'cancel_order'}">
                      주문취소
                    </c:when>
                    <c:otherwise>
                      ${item.delivery_state}
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${item.delivery_state == 'delivery_prepared'}">
                      <input type="button" onClick="fn_cancel_order('${item.order_id}')" value="주문취소" />
                    </c:when>
                    <c:otherwise>
                      <input type="button" onClick="fn_cancel_order('${item.order_id}')" value="주문취소" disabled />
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
    <div class="total-price">
      총 합계: <fmt:formatNumber value="${totalPrice}" type="currency" />
    </div>
  </div>
  
  <!-- 나의 정보 영역 (container2로 동일하게 사용) -->
  <div class="container2" id="infoContainer">
    <h1>
      나의 정보 
      <a href="${contextPath}/mypage/moreInfo.do" style="font-size:0.9rem; color:#000; text-decoration:none;">
        더보기 <img src="${contextPath}/resources/image/btn_more_see.jpg" alt="더보기" style="vertical-align: middle;"/>
      </a>
    </h1>
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
        <td colspan="2" style="text-align:center;">
          <button onclick="location.href='${contextPath}/member/updateMemberForm.do'">정보 변경</button>
        </td>
      </tr>
    </table>
  </div>
  
  <!-- 푸터는 Tiles 레이아웃에서 처리된다고 가정 -->
  
</body>
</html>
