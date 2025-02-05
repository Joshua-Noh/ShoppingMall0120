<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Noir - 개인정보처리방침</title>
  
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
    .privacy-container {
      max-width: 960px;
      margin: 40px auto;
      padding: 40px 20px;
      background: #fff;
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
  <div class="privacy-container">
    <!-- 페이지 제목 -->
    <h1>개인정보처리방침</h1>
    
    <!-- 제1조 개인정보의 수집 및 이용 목적 -->
    <div class="section">
      <h2>제1조 (개인정보의 수집 및 이용 목적)</h2>
      <p>
        본 개인정보처리방침은 Noir(이하 "회사")가 운영하는 쇼핑몰 서비스(이하 "서비스")에서 수집하는 개인정보의 종류, 이용 목적, 보유 및 이용 기간 등 개인정보 보호에 관한 사항을 규정합니다.
      </p>
    </div>
    
    <!-- 제2조 개인정보의 수집 항목 -->
    <div class="section">
      <h2>제2조 (개인정보의 수집 항목)</h2>
      <div class="clause">
        <h3>1. 필수 개인정보</h3>
        <p>회원가입, 상담, 서비스 신청 등과 관련하여 이름, 이메일, 연락처, 주소 등이 수집됩니다.</p>
      </div>
      <div class="clause">
        <h3>2. 선택 개인정보</h3>
        <p>생년월일, 성별 등 추가 정보를 선택적으로 수집할 수 있습니다.</p>
      </div>
    </div>
    
    <!-- 제3조 개인정보의 이용 및 보유 기간 -->
    <div class="section">
      <h2>제3조 (개인정보의 이용 및 보유 기간)</h2>
      <p>
        회사는 수집된 개인정보를 서비스 제공 및 고객 관리를 위해 이용하며, 목적 달성 후에는 지체 없이 파기합니다. 단, 관련 법령에 따라 일정 기간 보존할 수 있습니다.
      </p>
    </div>
    
    <!-- 제4조 개인정보의 제3자 제공 -->
    <div class="section">
      <h2>제4조 (개인정보의 제3자 제공)</h2>
      <p>
        회사는 원칙적으로 이용자의 개인정보를 외부에 제공하지 않으나, 관련 법령에 근거한 경우 및 이용자의 동의가 있는 경우에 한해 제공할 수 있습니다.
      </p>
    </div>
    
    <!-- 제5조 이용자의 권리 및 행사 방법 -->
    <div class="section">
      <h2>제5조 (이용자의 권리 및 행사 방법)</h2>
      <p>
        이용자는 언제든지 자신의 개인정보 열람, 수정, 삭제, 처리 정지 등의 권리를 행사할 수 있으며, 관련 요청은 고객지원 페이지를 통해 제출할 수 있습니다.
      </p>
    </div>
    
    <!-- 제6조 개인정보 보호를 위한 기술적, 관리적 대책 -->
    <div class="section">
      <h2>제6조 (개인정보 보호를 위한 기술적, 관리적 대책)</h2>
      <p>
        회사는 개인정보의 안전한 처리를 위해 내부관리계획 수립, 접근 권한 관리, 보안프로그램 설치 등 필요한 기술적, 관리적 조치를 취하고 있습니다.
      </p>
    </div>
    
    <!-- 제7조 책임 및 손해배상 -->
    <div class="section">
      <h2>제7조 (책임 및 손해배상)</h2>
      <p>
        회사는 천재지변, 불가항력 또는 이용자의 고의·과실로 인한 개인정보 유출 및 손해에 대해 책임을 지지 않습니다.
      </p>
    </div>
    
    <!-- 제8조 준거법 및 관할 -->
    <div class="section">
      <h2>제8조 (준거법 및 관할)</h2>
      <p>
        본 개인정보처리방침의 해석 및 적용에 관한 분쟁은 대한민국 법률에 따르며, 관련 분쟁은 회사 본사 소재지를 관할하는 법원을 제1심 관할 법원으로 합니다.
      </p>
    </div>
    
    <!-- 기타 -->
    <div class="section">
      <h2>기타</h2>
      <p>
        이 개인정보처리방침에 명시되지 않은 사항은 관련 법령 및 회사의 내부 규정에 따릅니다.
      </p>
    </div>
  </div>
  
  <!-- JavaScript: 페이지 로드시 부드러운 애니메이션 효과 (옵션) -->
  <script>
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
