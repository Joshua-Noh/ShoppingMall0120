<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>주문 배송 조회</title>
  
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
  <!-- Google Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <!-- jQuery (최신 버전) -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
  <style>
    /* CSS Variables */
    :root {
      --primary-color: #4285f4;
      --primary-hover: #3367d6;
      --background-color: #f3f3f3;
      --text-color: #333;
      --card-bg: #fff;
      --table-header-bg: #4285f4;
      --table-header-text: #fff;
      --border-color: #ddd;
      --hover-bg: #f1f1f1;
    }
    
    /* Global Reset & Body Styles */
    * {
      box-sizing: border-box;
    }
    body {
      font-family: 'Roboto', sans-serif;
      background-color: var(--background-color);
      margin: 0;
      padding: 20px;
      color: var(--text-color);
    }
    h3 {
      text-align: center;
      margin-bottom: 20px;
      font-weight: 500;
    }
    
    /* 배송조회 버튼 */
    .delivery-btn {
      display: block;
      margin: 0 auto 20px;
      padding: 10px 20px;
      background-color: var(--primary-color);
      color: #fff;
      border: none;
      border-radius: 8px;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.3s;
    }
    .delivery-btn:hover {
      background-color: var(--primary-hover);
    }
    .delivery-btn i {
      vertical-align: middle;
      margin-right: 8px;
    }
    
    /* 검색 폼 */
    form.search-form {
      background-color: var(--card-bg);
      padding: 15px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
      margin-bottom: 20px;
      text-align: center;
      max-width: 600px;
      margin-left: auto;
      margin-right: auto;
    }
    form.search-form input[type="date"] {
      padding: 8px;
      font-size: 1rem;
      border: 1px solid var(--border-color);
      border-radius: 4px;
      margin: 0 5px;
    }
    form.search-form input[type="submit"] {
      padding: 8px 16px;
      background-color: var(--primary-color);
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.3s;
      font-size: 1rem;
    }
    form.search-form input[type="submit"]:hover {
      background-color: var(--primary-hover);
    }
    
    /* 테이블 컨테이너 */
    .table-container {
      max-width: 800px;
      margin: 0 auto;
    }
    /* 주문 내역 테이블 */
    table.list_view {
      width: 100%;
      border-collapse: separate;
      border-spacing: 0;
      background-color: var(--card-bg);
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
      border-radius: 8px;
      overflow: hidden;
      margin-bottom: 20px;
    }
    table.list_view th,
    table.list_view td {
      padding: 12px 15px;
      text-align: center;
      border-bottom: 1px solid var(--border-color);
    }
    table.list_view th {
      background-color: var(--table-header-bg);
      color: var(--table-header-text);
      font-weight: 500;
    }
    table.list_view tr:last-child td {
      border-bottom: none;
    }
    table.list_view tr:hover {
      background-color: var(--hover-bg);
    }
    .fixed {
      width: 120px;
    }
    
    /* Responsive */
    @media (max-width: 768px) {
      table.list_view th, table.list_view td {
        padding: 8px 10px;
        font-size: 0.9rem;
      }
      .fixed { width: auto; }
      .delivery-btn, form.search-form input[type="submit"] {
        font-size: 0.9rem;
      }
    }
  </style>
  
  <script>
    // 배송조회 팝업 함수: carrierId와 trackId를 설정합니다.
    function openDeliveryPopup() {
      var carrierId = 'kr.hanjin';      // 한진택배
      var trackId = '458203901304';      // 송장번호
      var url = "${contextPath}/delivery/track.do?carrierId=" + carrierId + "&trackId=" + trackId;
      window.open(url, 'deliveryPopup', 'width=600,height=400');
    }
  </script>

</head>
<body>
  <h3>주문 배송 조회</h3>
  
  <!-- 배송조회 버튼 (구글 아이콘 포함) -->
  <button type="button" class="delivery-btn" onclick="openDeliveryPopup()">
    <i class="material-icons">local_shipping</i> 배송조회
  </button>
  
  <!-- 간단한 검색 폼: 조회기간 설정 -->
  <form class="search-form" method="get" action="${contextPath}/mypage/listMyOrderHistory.do">
    조회기간:
    <input type="date" name="beginDate" value="${beginDate}" />
    ~ 
    <input type="date" name="endDate" value="${endDate}" />
    <input type="submit" value="검색" />
  </form>
  
  <!-- 모델 데이터 디버그 출력 -->
  <c:if test="${empty myOrderHistList}">
    <p style="text-align: center; color: red;">주문 목록이 없습니다.</p>
  </c:if>
  <c:if test="${not empty myOrderHistList}">
    <p style="text-align: center; color: green;">주문 목록이 존재합니다. (총 ${fn:length(myOrderHistList)}건)</p>
  </c:if>
  
  <!-- 주문 내역 테이블 -->
  <div class="table-container">
    <table class="list_view">
      <thead>
        <tr>
          <th class="fixed">주문번호</th>
          <th class="fixed">주문일자</th>
          <th>총 금액 / 수량</th>
          <th>주문상태</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty myOrderHistList}">
            <tr>
              <td colspan="4" class="fixed"><strong>주문한 상품이 없습니다.</strong></td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="item" items="${myOrderHistList}" varStatus="i">
              <tr>
                <td>
                  <a href="${contextPath}/mypage/myOrderDetail.do?order_id=${item.order_id}" style="color: var(--primary-color); text-decoration: none;">
                    <strong>${item.order_id}</strong>
                  </a>
                </td>
                <td><strong>${item.order_date}</strong></td>
                <td><strong>${item.total_price}원 / ${item.quantity}</strong></td>
                <td><strong>${item.delivery_state}</strong></td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>
  
  <div class="clear"></div>
</body>
</html>
