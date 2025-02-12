<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <link rel="icon" href="https://static.toss.im/icons/png/4x/icon-toss-logo.png" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>토스페이먼츠 샘플 프로젝트</title>

    <!-- Google Fonts: Roboto 추가 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- 토스페이먼츠 SDK 추가 -->
    <script src="https://js.tosspayments.com/v2/standard"></script>

    <!-- 최신 트렌드를 반영한 CSS -->
    <style>
      /* 1. CSS Reset 및 기본 변수 설정 */
      :root {
        --primary-color: #333;
        --secondary-color: #fff;
        --accent-color: #4CAF50;
        --accent-color-hover: #43a047;
        --font-family: 'Roboto', sans-serif;
        --border-radius: 8px;
        --transition-duration: 0.3s;
      }
      
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      
      /* 2. 기본 바디 스타일 */
      body {
        font-family: var(--font-family);
        line-height: 1.6;
        background-color: #f9f9f9;
        color: var(--primary-color);
        padding: 20px;
      }
      
      /* 3. 이미지 반응형 처리 */
      img {
        max-width: 100%;
        display: block;
      }
      
      /* 4. 콘텐츠 박스 스타일 */
      .box_section {
        background-color: var(--secondary-color);
        border-radius: var(--border-radius);
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        margin: 20px auto;
        padding: 20px;
        width: 600px;
      }
      
      /* 5. 주문 상세 영역 그리드 레이아웃 */
      .p-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        align-items: center;
        gap: 10px;
      }
      
      .typography--p {
        font-size: 1rem;
      }
      
      .text--left {
        text-align: left;
      }
      
      .text--right {
        text-align: right;
      }
      
      /* 6. 제목 스타일 */
      h2 {
        font-size: 1.5rem;
        margin: 10px 0 20px;
        text-align: center;
      }
      
      /* 7. 버튼 스타일 (최종 주문 완료 버튼) */
      .btn {
        display: inline-block;
        padding: 12px 24px;
        font-size: 1rem;
        font-weight: 600;
        color: var(--secondary-color);
        background-color: var(--accent-color);
        border: none;
        border-radius: var(--border-radius);
        cursor: pointer;
        text-decoration: none;
        transition: background-color var(--transition-duration);
        margin-top: 20px;
      }
      
      .btn:hover {
        background-color: var(--accent-color-hover);
      }
      
      /* 8. 반응형 디자인: 모바일 최적화 */
      @media (max-width: 600px) {
        .box_section {
          width: 100%;
          padding: 15px;
        }
        .p-grid {
          grid-template-columns: 1fr;
        }
        .text--right {
          text-align: left;
        }
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
    <div class="box_section">
      <img width="100px" src="https://static.toss.im/illusts/check-blue-spot-ending-frame.png" alt="결제 완료 이미지" />
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
    <div class="box_section" style="text-align: center">
      <form action="<%= request.getContextPath() %>/order/completeOrder.do" method="post">
        <input type="hidden" name="paymentId" value="<%= paymentKey %>" />
        <button type="submit" class="btn">최종 주문 완료</button>
      </form>
    </div>
  </body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
