<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Noir - 회사 소개</title>
  
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
      background-color: #f4f4f4;
      color: #333;
      margin: 0;
      padding: 0;
      line-height: 1.6;
    }
    .company-intro {
      max-width: 1200px;
      margin: 0 auto;
      padding: 40px 20px;
      background: linear-gradient(135deg, #f4f4f4, #e0e0e0);
    }
    h2 {
      font-family: 'Oswald', sans-serif;
      font-size: 2.5rem;
      color: #222;
      text-transform: uppercase;
      margin-bottom: 20px;
      text-align: center;
      position: relative;
    }
    h2::after {
      content: '';
      display: block;
      width: 60px;
      height: 3px;
      background-color: #d4af37;
      margin: 10px auto 0;
    }
    p {
      font-size: 1.1rem;
      color: #555;
    }
    section {
      margin-bottom: 60px;
      padding: 30px;
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    section:hover {
      transform: translateY(-10px);
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
    }
    /* 회사 소개 섹션 텍스트 중앙 정렬 */
    .company-overview {
      text-align: center;
    }
    .company-info {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      gap: 20px;
    }
    .info-box {
      flex: 1 1 calc(33.333% - 20px);
      background-color: #f9f9f9;
      padding: 20px;
      border-radius: 10px;
      text-align: center;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .info-box:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }
    .info-box h3 {
      font-family: 'Oswald', sans-serif;
      font-size: 1.5rem;
      color: #222;
      margin-bottom: 10px;
    }
    .info-box p {
      font-size: 1rem;
      color: #666;
    }
    .history p {
      font-size: 1.1rem;
      color: #444;
      margin-bottom: 10px;
    }
    .history p strong {
      color: #d4af37;
      font-weight: bold;
    }
    .section-image {
      width: 100%;
      max-height: 300px;
      object-fit: cover;
      border-radius: 10px;
      margin-bottom: 20px;
    }
    @media (max-width: 768px) {
      .info-box {
        flex: 1 1 100%;
      }
    }
  </style>
</head>
<body>
  <div class="company-intro">
    <!-- 회사 소개 섹션 -->
    <section class="company-overview">
      <h2>회사 소개</h2>
      <img src="https://picsum.photos/1200/400?random=1" alt="회사 소개 이미지" class="section-image">
      <p>Noir는 현대 남성을 위한 프리미엄 패션을 추구하는 남성 전용 쇼핑몰입니다. 고객에게 최고 품질의 제품과 감각적인 디자인을 제공하여, 그들이 자신감을 가지고 자신의 개성을 표현할 수 있도록 돕습니다.</p>
      <p>우리의 목표는 단순한 패션 쇼핑몰을 넘어서, 남성의 라이프스타일 전반을 아우르는 브랜드로 자리매김하는 것입니다.</p>
    </section>

    <!-- 비전 & 미션 섹션 -->
    <section class="vision-mission">
      <h2>비전 &amp; 미션</h2>
      <div class="company-info">
        <div class="info-box">
          <span class="material-icons">visibility</span>
          <h3>비전</h3>
          <p>세계적인 남성 패션 리더로서, 혁신적 디자인과 탁월한 품질을 통해 고객에게 최고의 스타일 경험을 제공합니다.</p>
        </div>
        <div class="info-box">
          <span class="material-icons">flag</span>
          <h3>미션</h3>
          <p>남성들이 자신의 개성을 자신감 있게 표현할 수 있도록, 트렌드를 선도하는 프리미엄 제품과 맞춤형 서비스를 제공합니다.</p>
        </div>
      </div>
    </section>

    <!-- 연혁 섹션 -->
    <section class="history">
      <h2>연혁</h2>
      <img src="https://picsum.photos/1200/400?random=2" alt="연혁 이미지" class="section-image">
      <p><strong>2010년</strong> - Noir 창립, 남성 전용 패션 쇼핑몰 오픈</p>
      <p><strong>2012년</strong> - 국내 최초로 남성 패션 전문 온라인 플랫폼 구축</p>
      <p><strong>2015년</strong> - 해외 시장 진출 및 글로벌 브랜드와의 협업 시작</p>
      <p><strong>2020년</strong> - 프리미엄 남성 패션 리더로 자리매김</p>
    </section>

    <!-- 핵심 가치 섹션 -->
    <section class="core-values">
      <h2>핵심 가치</h2>
      <div class="company-info">
        <div class="info-box">
          <span class="material-symbols-outlined" style="font-size: 2.5rem; color: #d4af37;">lightbulb_outline</span>
          <h3>혁신</h3>
          <p>끊임없는 혁신을 통해 최신 트렌드와 기술을 선도합니다.</p>
        </div>
        <div class="info-box">
          <span class="material-icons" style="font-size: 2.5rem; color: #d4af37;">star_rate</span>
          <h3>품질</h3>
          <p>최고의 소재와 정교한 제작 공정을 통해 탁월한 품질을 보장합니다.</p>
        </div>
        <div class="info-box">
          <span class="material-symbols-outlined" style="font-size: 2.5rem; color: #d4af37;">handshake</span>
          <h3>신뢰</h3>
          <p>고객과의 신뢰를 바탕으로 지속 가능한 관계를 구축하며, 투명한 경영을 실천합니다.</p>
        </div>
      </div>
    </section>
  </div>
  
  <!-- JavaScript: 섹션 페이드인 애니메이션 -->
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const sections = document.querySelectorAll("section");
      const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.style.opacity = 1;
            entry.target.style.transform = "translateY(0)";
          }
        });
      }, { threshold: 0.1 });
      
      sections.forEach((section) => {
        section.style.opacity = 0;
        section.style.transform = "translateY(50px)";
        section.style.transition = "opacity 0.6s ease, transform 0.6s ease";
        observer.observe(section);
      });
    });
  </script>
</body>
</html>
	