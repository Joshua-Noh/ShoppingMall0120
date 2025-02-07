<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <link rel="icon" href="https://static.toss.im/icons/png/4x/icon-toss-logo.png" />
    <link rel="stylesheet" type="text/css" href="style.css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>토스페이먼츠 샘플 프로젝트</title>
    <!-- 토스페이먼츠 SDK 추가 -->
    <script src="https://js.tosspayments.com/v2/standard"></script>
    <style>
      .btn {
          display: inline-block;
          padding: 10px 20px;
          background-color: #333;
          color: #fff;
          text-decoration: none;
          border-radius: 4px;
          cursor: pointer;
          margin-top: 20px;
      }
    </style>
  </head>
  <body>
    <%
      // 서버에서 디버깅 로그 출력
      System.out.println("[DEBUG] success.jsp 로딩됨.");

      // URL 파라미터 값 가져오기
      String paymentKey = request.getParameter("paymentKey");
      String orderId = request.getParameter("orderId");
      String amount = request.getParameter("amount");

      // 가져온 값 로그 출력
      System.out.println("[DEBUG] 수신된 결제 정보:");
      System.out.println("[DEBUG] 주문번호(orderId): " + orderId);
      System.out.println("[DEBUG] 결제금액(amount): " + amount);
      System.out.println("[DEBUG] 결제키(paymentKey): " + paymentKey);
    %>

    <!-- 주문서 영역 -->
    <div class="box_section" style="width: 600px">
      <img width="100px" src="https://static.toss.im/illusts/check-blue-spot-ending-frame.png" />
      <h2>결제를 완료했어요</h2>

      <div class="p-grid typography--p" style="margin-top: 50px">
        <div class="p-grid-col text--left"><b>결제금액</b></div>
        <div class="p-grid-col text--right"><%= amount %>원</div>
      </div>
      <div class="p-grid typography--p" style="margin-top: 10px">
        <div class="p-grid-col text--left"><b>주문번호</b></div>
        <div class="p-grid-col text--right"><%= orderId %></div>
      </div>
      <div class="p-grid typography--p" style="margin-top: 10px">
        <div class="p-grid-col text--left"><b>결제키</b></div>
        <div class="p-grid-col text--right"><%= paymentKey %></div>
      </div>
    </div>

    <!-- 최종 주문 완료 버튼 -->
    <div class="box_section" style="width: 600px; text-align: center">
      <form action="<%= request.getContextPath() %>/order/completeOrder.do" method="post">
        <input type="hidden" name="paymentId" value="<%= paymentKey %>" />
        <button type="submit" class="btn">최종 주문 완료</button>
      </form>
    </div>

  </body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
