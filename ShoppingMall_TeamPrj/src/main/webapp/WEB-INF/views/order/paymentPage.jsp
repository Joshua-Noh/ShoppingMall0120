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
    <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
    <link
      href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap"
      rel="stylesheet"
    />
    <!-- 토스페이먼츠 SDK 추가 -->
    <script src="https://js.tosspayments.com/v2/standard"></script>
    
    <style>
      /* CSS Reset */
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      
      /* CSS Variables */
      :root {
        --primary-color: #333;
        --secondary-color: #fff;
        --accent-color: #4CAF50;
        --accent-hover: #43a047;
        --font-family-base: 'Roboto', sans-serif;
        --font-family-heading: 'Oswald', sans-serif;
        --border-radius: 8px;
        --transition-duration: 0.3s;
        --bg-light: #f4f7f6;
        --bg-dark: #eaeaea;
      }
      
      body {
        font-family: var(--font-family-base);
        background: var(--bg-light);
        color: var(--primary-color);
        line-height: 1.6;
        padding: 20px;
      }
      
      /* 메인 래퍼 */
      .wrapper {
        max-width: 600px;
        margin: 30px auto;
        padding: 0 15px;
      }
      
      /* 결제 위젯 및 주문서 박스 */
      .box_section {
        background-color: var(--secondary-color);
        border-radius: var(--border-radius);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        padding: 40px 30px 50px 30px;
        margin-top: 30px;
        margin-bottom: 50px;
      }
      
      /* 결제 위젯 크기 조절 (너무 비대한 위젯은 적당한 스케일 적용) */
      #payment-method {
        transform: scale(0.95);
        transform-origin: top center;
        margin-bottom: 20px;
      }
      
      /* 이용약관 UI */
      #agreement {
        margin-bottom: 20px;
      }
      
      /* 커스텀 체크박스 스타일 */
      .checkable {
        display: flex;
        align-items: center;
        font-size: 1rem;
        margin-bottom: 10px;
      }
      .checkable__input {
        appearance: none;
        width: 18px;
        height: 18px;
        border: 1px solid #ccc;
        border-radius: 4px;
        background: var(--secondary-color);
        cursor: pointer;
        margin-right: 8px;
        position: relative;
      }
      .checkable__input:checked {
        background-color: var(--accent-color);
        border-color: var(--accent-color);
      }
      .checkable__input:checked::after {
        content: '✓';
        font-size: 14px;
        color: var(--secondary-color);
        position: absolute;
        top: 0;
        left: 4px;
      }
      .checkable__label-text {
        vertical-align: middle;
      }
      
      /* 결제하기 버튼 스타일 */
      .result.wrapper {
        text-align: center;
      }
      .button {
        display: inline-block;
        padding: 14px 30px;
        background-color: var(--primary-color);
        color: var(--secondary-color);
        border: none;
        border-radius: var(--border-radius);
        font-size: 1.1rem;
        cursor: pointer;
        transition: background-color var(--transition-duration) ease,
                    transform var(--transition-duration) ease,
                    box-shadow var(--transition-duration) ease;
      }
      .button:hover {
        background-color: #555;
        transform: scale(1.02);
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
      }
      
      /* 모바일 반응형 */
      @media (max-width: 768px) {
        .wrapper {
          max-width: 95%;
        }
        .box_section {
          padding: 30px 20px;
          margin-top: 20px;
          margin-bottom: 30px;
        }
      }
    </style>
  </head>

  <body>
    <!-- 주문서 영역 -->
    <div class="wrapper">
      <div class="box_section">
        <!-- 결제 UI -->
        <div id="payment-method"></div>
        <!-- 이용약관 UI -->
        <div id="agreement"></div>
        <!-- 쿠폰 체크박스 -->
        <div style="padding-left: 25px; margin-bottom: 20px;">
          <div class="checkable typography--p">
            <label for="coupon-box" class="checkable__label typography--regular">
              <input id="coupon-box" class="checkable__input" type="checkbox" aria-checked="true" />
              <span class="checkable__label-text">5,000원 쿠폰 적용</span>
            </label>
          </div>
        </div>
        <!-- 결제하기 버튼 -->
        <div class="result wrapper">
          <button class="button" id="payment-button" style="margin-top: 30px;">
            결제하기
          </button>
        </div>
      </div>
    </div>

    <script>
      main();

      async function main() {
        const button = document.getElementById("payment-button");
        const coupon = document.getElementById("coupon-box");
        const amount = {
          currency: "KRW",
          value: ${totalPrice}  // JSP EL로 totalPrice 값을 삽입
        };

        // ------  결제위젯 초기화 ------
        // TODO: clientKey는 개발자센터의 결제위젯 연동 키 (클라이언트 키)로 바꾸세요.
        // TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요.
        // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
        const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
        const customerKey = generateRandomString();
        const tossPayments = TossPayments(clientKey);
        // 회원 결제
        const widgets = tossPayments.widgets({
          customerKey,
        });
        // 비회원 결제 (필요시 아래 주석 해제)
        // const widgets = tossPayments.widgets({ customerKey: TossPayments.ANONYMOUS });

        // ------  주문서의 결제 금액 설정 ------
        // 위젯의 결제금액을 결제하려는 금액으로 초기화 (반드시 renderPaymentMethods, renderAgreement, requestPayment보다 선행)
        await widgets.setAmount(amount);

        // ------  결제 UI 렌더링 ------
        // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
        await widgets.renderPaymentMethods({
          selector: "#payment-method",
          variantKey: "DEFAULT", // 기본 결제 UI variant
        });

        // ------  이용약관 UI 렌더링 ------
        // @docs https://docs.tosspayments.com/reference/widget-sdk#renderagreement선택자-옵션
        await widgets.renderAgreement({ selector: "#agreement", variantKey: "AGREEMENT" });

        // ------  주문서의 결제 금액 업데이트 (쿠폰 적용) ------
        // @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount
        coupon.addEventListener("change", async function () {
          if (coupon.checked) {
            await widgets.setAmount({
              currency: "KRW",
              value: amount.value - 5000,
            });
            return;
          }
          await widgets.setAmount(amount);
        });

        // ------ '결제하기' 버튼 클릭 시 결제창 띄우기 ------
        // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
        button.addEventListener("click", async function () {
          // 결제 요청 전에 orderId, amount 등의 정보를 서버에 저장하여
          // 결제 진행 중에 금액이 변조되는 것을 방지합니다.
          await widgets.requestPayment({
            orderId: generateRandomString(),
            orderName: "토스 티셔츠 외 2건",
            successUrl: window.location.origin + "${pageContext.request.contextPath}/success",
            failUrl: window.location.origin + "${pageContext.request.contextPath}/fail",
            customerEmail: "customer123@gmail.com",
            customerName: "김토스",
            customerMobilePhone: "01012341234",
          });
        });
      }

      function generateRandomString() {
        return window.btoa(Math.random()).slice(0, 20);
      }
    </script>
  </body>
</html>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
