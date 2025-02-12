<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.Map"%>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>주문 확인</title>

  <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <!-- Material Symbols Outlined -->
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">

  <style>
    /* CSS Variables 설정 */
    :root {
      --primary-color: #333;
      --secondary-color: #fff;
      --accent-color: #4CAF50;
      --accent-hover: #43a047;
      --bg-gradient-start: #f4f7f6;
      --bg-gradient-end: #eaeaea;
      --container-bg: #fff;
      --box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
      --border-radius: 10px;
      --transition-duration: 0.3s;
      --font-family-base: 'Roboto', sans-serif;
      --font-family-heading: 'Oswald', sans-serif;
    }

    /* 기본 리셋 */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: var(--font-family-base);
      background: linear-gradient(135deg, var(--bg-gradient-start), var(--bg-gradient-end));
      color: var(--primary-color);
      line-height: 1.6;
    }

    /* 메인 컨테이너 (결제 위젯) */
    .container {
      max-width: 600px; /* 기존 900px에서 축소하여 적당한 크기로 조정 */
      margin: 100px auto 30px auto; /* 상단 여백을 약간 줄임 */
      padding: 30px 20px;
      background-color: var(--container-bg);
      border-radius: var(--border-radius);
      box-shadow: var(--box-shadow);
      opacity: 0;
      transform: translateY(30px);
      transition: opacity 0.8s ease, transform 0.8s ease;
    }

    /* 제목 스타일 */
    .container h1 {
      font-family: var(--font-family-heading);
      font-size: 2.6rem;
      text-align: center;
      color: var(--primary-color);
      margin-bottom: 30px;
      position: relative;
    }
    .container h1::after {
      content: '';
      display: block;
      width: 100px;
      height: 4px;
      background-color: var(--accent-color);
      margin: 10px auto 0;
    }

    /* 테이블 스타일 */
    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 30px;
      font-size: 1rem;
    }
    thead {
      background-color: var(--bg-gradient-start);
    }
    th, td {
      padding: 15px;
      text-align: center;
      border-bottom: 1px solid #ddd;
    }
    th {
      color: #555;
      font-weight: 600;
    }
    td {
      background-color: var(--container-bg);
    }
    tr:hover td {
      background-color: #f9f9f9;
    }

    /* 총 합계 스타일 */
    .total-price {
      font-size: 1.2rem;
      font-weight: bold;
      color: #d9534f;
      text-align: right;
      margin-bottom: 30px;
    }

    /* 폼 입력 필드 스타일 */
    .form-group {
      margin-bottom: 20px;
    }
    .form-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }
    .form-group input,
    .form-group textarea {
      width: 100%;
      padding: 10px;
      font-size: 1rem;
      border: 1px solid #ddd;
      border-radius: 5px;
    }

    /* 체크박스 최신화 스타일 */
    input[type="checkbox"] {
      appearance: none;
      width: 18px;
      height: 18px;
      border: 1px solid #ccc;
      border-radius: 4px;
      background: var(--secondary-color);
      cursor: pointer;
      vertical-align: middle;
      margin-right: 8px;
      position: relative;
    }
    input[type="checkbox"]:checked {
      background-color: var(--accent-color);
      border-color: var(--accent-color);
    }
    input[type="checkbox"]:checked::after {
      content: '✓';
      font-size: 14px;
      color: var(--secondary-color);
      position: absolute;
      top: 0;
      left: 4px;
    }

    /* 버튼 스타일 */
    .btn {
      display: block;
      width: 100%;
      padding: 14px;
      background-color: var(--primary-color);
      color: var(--secondary-color);
      text-align: center;
      font-size: 1.1rem;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color var(--transition-duration) ease, transform var(--transition-duration) ease, box-shadow var(--transition-duration) ease;
    }
    .btn:hover {
      background-color: #555;
      transform: scale(1.02);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    }

    /* 결제 실패 메시지 */
    .message {
      color: red;
      text-align: center;
      font-size: 1rem;
      margin: 20px 0;
    }

    /* 모바일 반응형 */
    @media (max-width: 768px) {
      .container {
        width: 95%;
        margin-top: 80px;
        padding: 20px;
      }
      table, th, td {
        font-size: 0.9rem;
      }
      .container h1 {
        font-size: 2.2rem;
      }
    }
  </style>
</head>
<body>
  <div class="container" id="mainContainer">
    <h1>
      주문 내역 확인 
      <span class="material-symbols-outlined" style="font-size: 2rem; vertical-align: middle; color: var(--accent-color); margin-left: 10px;">
        receipt_long
      </span>
    </h1>

    <!-- 주문 내역 테이블 -->
    <table>
      <thead>
        <tr>
          <th>상품명</th>
          <th>가격</th>
          <th>수량</th>
          <th>총 가격</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="cartItem" items="${cartMap.myCartList}">
          <c:forEach var="goods" items="${cartMap.myGoodsList}">
            <c:if test="${goods.product_id == cartItem.product_id}">
              <tr>
                <td>${goods.product_name}</td>
                <td><fmt:formatNumber value="${goods.price}" type="currency" /></td>
                <td>${cartItem.quantity}</td>
                <td><fmt:formatNumber value="${cartItem.quantity * goods.price}" type="currency" /></td>
              </tr>
            </c:if>
          </c:forEach>
        </c:forEach>
      </tbody>
    </table>

    <!-- 총 합계 -->
    <div class="total-price">
      총 합계:
      <fmt:formatNumber value="${totalPrice}" type="currency" />
    </div>

    <!-- 배송 및 수령인 정보 입력 폼 (Phase 1: 미결제 상태 임시 주문 정보 저장) -->
    <form action="${pageContext.request.contextPath}/order/myOrder.do" method="post">
      <div class="form-group">
        <label for="receiver_name">받는 사람</label>
        <input type="text" id="receiver_name" name="receiver_name" required placeholder="예: 홍길동" />
      </div>
      <div class="form-group">
        <label for="receiver_hp">휴대폰 번호</label>
        <input type="text" id="receiver_hp" name="receiver_hp" required placeholder="예: 010-1234-5678" />
      </div>
      <div class="form-group">
        <label for="receiver_tel">전화 번호 (선택)</label>
        <input type="text" id="receiver_tel" name="receiver_tel" placeholder="예: 02-123-4567" />
      </div>
      <div class="form-group">
        <label for="delivery_address">배송 주소</label>
        <input type="text" id="delivery_address" name="delivery_address" required placeholder="배송 주소를 입력하세요" />
      </div>
      <div class="form-group">
        <label for="delivery_message">배송 메시지 (선택)</label>
        <textarea id="delivery_message" name="delivery_message" placeholder="배송 시 요청 사항을 입력하세요"></textarea>
      </div>

      <!-- 기존 결제 진행에 필요한 hidden 필드 -->
      <input type="hidden" name="amount" value="${totalPrice}" />
      <input type="hidden" name="payment_method" value="card" />
      <input type="hidden" name="return_url" value="${pageContext.request.contextPath}/success" />

      <!-- 결제 진행 버튼 -->
      <button type="submit" class="btn">
        <span class="material-symbols-outlined" style="font-size: 1.5rem; vertical-align: middle;">
          local_mall
        </span>
        결제 진행
      </button>
    </form>

    <!-- 결제 실패 시 메시지 -->
    <c:if test="${not empty message}">
      <div class="message">${message}</div>
    </c:if>
  </div>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />

  <!-- 페이지 로드시 컨테이너 애니메이션 효과 -->
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const container = document.getElementById('mainContainer');
      setTimeout(() => {
        container.style.opacity = 1;
        container.style.transform = "translateY(0)";
      }, 200);
    });
  </script>
</body>
</html>
