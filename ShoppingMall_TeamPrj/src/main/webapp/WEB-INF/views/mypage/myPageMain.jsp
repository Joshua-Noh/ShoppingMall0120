<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Page - 주문내역 및 내 정보</title>
  <style>
    /* 기본 스타일 */
    body {
        margin: 0;
        background-color: #f8f9fa;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
    }
    
    /* 헤더 스타일 */
    header {
        background-color: #000;
        padding: 15px 30px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .top-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .logo img {
        max-height: 50px;
        cursor: pointer;
        transition: transform 0.3s ease;
    }
    .logo img:hover {
        transform: scale(1.1);
    }
    
    /* 컨테이너 스타일 */
    .container {
        max-width: 800px;
        margin: 40px auto;
        padding: 20px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    }
    
    /* 제목 스타일 */
    h1 {
        font-size: 24px;
        color: #333;
        margin-bottom: 20px;
        border-bottom: 2px solid #ddd;
        padding-bottom: 10px;
    }
    h1 a {
        float: right;
        font-size: 14px;
        color: #000;
        text-decoration: none;
        transition: text-decoration 0.3s;
    }
    h1 a:hover {
        text-decoration: underline;
    }
    
    /* 테이블 스타일 */
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    table thead {
        background-color: #000;
        color: #fff;
    }
    table thead th {
        padding: 10px;
        font-size: 14px;
    }
    table tbody tr {
        border-bottom: 1px dotted #999;
    }
    table tbody td {
        padding: 10px;
        text-align: center;
        font-size: 14px;
        color: #333;
    }
    
    /* 버튼 스타일 */
    button, input[type="button"] {
        padding: 8px 16px;
        background-color: #000;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s;
    }
    button:hover, input[type="button"]:hover {
        background-color: #333;
    }
    
    /* 정보 테이블 스타일 */
    .info-table {
        width: 100%;
        border-collapse: collapse;
    }
    .info-table td {
        padding: 10px;
        font-size: 14px;
    }
    .info-table tr:not(:last-child) td {
        border-bottom: 1px solid #ddd;
    }
  </style>
  
  <!-- 주문 취소 후 메시지 출력 -->
  <c:if test="${message=='cancel_order'}">
    <script>
      window.onload = function(){
        alert("주문을 취소했습니다.");
      }
    </script>
  </c:if>
  
  <script>
    function fn_cancel_order(order_id){
        var answer = confirm("주문을 취소하시겠습니까?");
        if(answer){
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
  </script>
</head>
<body>
  <header>
    <div class="top-header">
      <div class="logo">
        <a href="${contextPath}/main/main.do">
          <img src="${contextPath}/resources/image/NOIR_LOGO.png" alt="My Shop Logo">
        </a>
      </div>
    </div>
  </header>
  
  <!-- 최근 주문내역 영역 -->
  <div class="container">
    <h1>
      최근 주문내역 
      <a href="${contextPath}/mypage/moreOrders.do">
        더보기 <img src="${contextPath}/resources/image/btn_more_see.jpg" alt="더보기">
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
              <td colspan="5"><strong>주문한 상품이 없습니다.</strong></td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="item" items="${myOrderList}" varStatus="i">
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
  </div>
  
  <!-- 나의 정보 영역 -->
  <div class="container">
    <h1>
      나의 정보 
      <a href="${contextPath}/mypage/moreInfo.do">
        더보기 <img src="${contextPath}/resources/image/btn_more_see.jpg" alt="더보기">
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
  
  <!-- 추가 기능 추천 -->
  <!-- 
       추가하면 좋은 기능들:
       1. **검색 및 필터링**: 주문내역 영역 상단에 날짜별, 상태별 검색/필터 기능을 추가하여 원하는 주문만 쉽게 찾을 수 있음.
       2. **페이지네이션**: 주문내역이 많을 경우, 페이지네이션을 도입하여 목록을 분할해서 보여주기.
       3. **주문 상세보기 모달**: 주문번호를 클릭하면 주문 상세 정보를 모달 창으로 보여주는 기능.
       4. **실시간 알림**: 주문 상태 변경이나 새 주문 발생 시 실시간 알림(예: 웹소켓, 폴링 등)을 추가하여 사용자에게 업데이트 제공.
       5. **프로필 이미지 업로드**: 나의 정보 영역에 프로필 이미지를 추가하고, 이미지를 업로드 및 변경할 수 있는 기능.
       6. **테마 전환**: 다크 모드/라이트 모드를 선택할 수 있는 토글 스위치를 제공하여 사용자 환경에 맞게 테마 변경 기능.
  -->
</body>
</html>
