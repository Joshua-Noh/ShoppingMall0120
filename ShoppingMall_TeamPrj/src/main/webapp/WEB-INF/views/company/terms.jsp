<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Noir - 쇼핑몰 이용약관</title>
  
  <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <!-- Material Symbols Outlined -->
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">

  <style>
    /* 기본 설정 */
    body {
      font-family: 'Roboto', sans-serif;
      background-color: #f9f9f9;
      color: #333;
      margin: 0;
      padding: 0;
      line-height: 1.8;
    }
    .terms-container {
      max-width: 960px;
      margin: 0 auto;
      padding: 40px 20px;
      background-color: #fff;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
      border-radius: 10px;
    }
    h1, h2, h3 {
      font-family: 'Oswald', sans-serif;
      color: #222;
      text-transform: uppercase;
      margin-top: 30px;
      margin-bottom: 10px;
    }
    h1 {
      text-align: center;
      font-size: 2.8rem;
      position: relative;
    }
    h1::after {
      content: '';
      display: block;
      width: 80px;
      height: 4px;
      background-color: #d4af37;
      margin: 10px auto 0;
    }
    h2 {
      font-size: 1.8rem;
      border-bottom: 2px solid #d4af37;
      padding-bottom: 5px;
    }
    h3 {
      font-size: 1.4rem;
    }
    p, li {
      font-size: 1.1rem;
      color: #555;
      margin-bottom: 10px;
      text-align: justify;
    }
    ul {
      margin: 10px 0 20px 20px;
    }
    .section {
      margin-bottom: 40px;
    }
    .clause {
      margin-bottom: 20px;
    }
    .clause p {
      margin: 5px 0;
    }
  </style>
</head>
<body>
  <div class="terms-container">
    <!-- 페이지 제목 -->
    <h1>쇼핑몰 이용약관</h1>
    
    <!-- 제1조 목적 -->
    <div class="section">
      <h2>제1조 (목적)</h2>
      <p>
        이 약관은 Noir(이하 "회사"라 한다)가 운영하는 쇼핑몰(이하 "서비스"라 한다)을 이용함에 있어 회사와 이용자의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.
      </p>
    </div>
    
    <!-- 제2조 약관의 효력 및 변경 -->
    <div class="section">
      <h2>제2조 (약관의 효력 및 변경)</h2>
      <div class="clause">
        <h3>1. 약관의 효력</h3>
        <p>
          이 약관은 서비스를 이용하고자 하는 모든 이용자에 대하여 그 효력을 발생합니다.
        </p>
      </div>
      <div class="clause">
        <h3>2. 약관의 변경</h3>
        <p>
          회사는 관련 법령에 위배되지 않는 범위에서 이 약관을 변경할 수 있으며, 변경된 약관은 홈페이지에 공지함으로써 효력을 발생합니다.
        </p>
      </div>
    </div>
    
    <!-- 제3조 이용자의 의무 -->
    <div class="section">
      <h2>제3조 (이용자의 의무)</h2>
      <p>
        이용자는 서비스를 이용함에 있어 다음 각 호의 행위를 하여서는 안 됩니다.
      </p>
      <ul>
        <li>타인의 정보 도용 또는 허위 정보의 등록</li>
        <li>회사 또는 제3자의 지식재산권을 침해하는 행위</li>
        <li>서비스의 안정적인 운영을 방해하는 행위</li>
        <li>기타 관계 법령에 위배되는 행위</li>
      </ul>
    </div>
    
    <!-- 제4조 서비스의 제공 및 변경 -->
    <div class="section">
      <h2>제4조 (서비스의 제공 및 변경)</h2>
      <p>
        회사는 이용자에게 아래와 같은 서비스를 제공합니다.
      </p>
      <ul>
        <li>상품 정보 제공, 구매 및 결제 서비스</li>
        <li>배송 및 환불 서비스</li>
        <li>기타 회사가 정하는 서비스</li>
      </ul>
      <p>
        회사는 서비스의 내용, 이용 방법, 수수료 등에 대하여 사전 공지 없이 변경할 수 있으며, 이에 대해 이용자는 이의를 제기할 수 없습니다.
      </p>
    </div>
    
    <!-- 제5조 책임 제한 -->
    <div class="section">
      <h2>제5조 (책임 제한)</h2>
      <p>
        회사는 천재지변, 불가항력적 사유 또는 이용자의 귀책사유로 인한 서비스 이용 장애에 대하여 책임을 지지 않습니다.
      </p>
    </div>
    
    <!-- 제6조 준거법 및 관할 -->
    <div class="section">
      <h2>제6조 (준거법 및 관할)</h2>
      <p>
        이 약관의 해석 및 적용에 관하여는 대한민국 법률을 적용하며, 서비스 이용과 관련하여 분쟁이 발생할 경우 회사의 본사 소재지를 관할하는 법원을 제1심 관할 법원으로 합니다.
      </p>
    </div>
    
    <!-- 기타 -->
    <div class="section">
      <h2>기타</h2>
      <p>
        이 약관에 명시되지 않은 사항에 대해서는 관련 법령 및 회사가 정한 서비스 이용규칙 등에 따릅니다.
      </p>
    </div>
  </div>
  
  <!-- JavaScript: 페이지 스크롤 애니메이션(옵션) -->
  <script>
    // 페이지 로드시 부드러운 애니메이션 효과 (옵션)
    document.addEventListener("DOMContentLoaded", function () {
      const sections = document.querySelectorAll(".section");
      sections.forEach(section => {
        section.style.opacity = 0;
        section.style.transform = "translateY(20px)";
        section.style.transition = "opacity 0.8s ease, transform 0.8s ease";
      });
      setTimeout(() => {
        sections.forEach(section => {
          section.style.opacity = 1;
          section.style.transform = "translateY(0)";
        });
      }, 200);
    });
  </script>
</body>
</html>
