<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:choose>
  <c:when test="${not empty sessionScope.user}">
    <c:set var="memberId" value="${sessionScope.user.id}" />
    <c:set var="memberName" value="${sessionScope.user.name}" />
  </c:when>
  <c:otherwise>
    <c:set var="memberId" value="${pageContext.request.remoteAddr}" />
    <c:set var="memberName" value="${pageContext.request.remoteAddr}" />
  </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Center</title>
  
  <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <!-- Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <!-- Material Symbols Outlined (필요시) -->
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">
  
  <style>
    /* 기본 리셋 및 전체 스타일 */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    body {
      font-family: 'Roboto', sans-serif;
      background: #f4f4f4;
      color: #333;
    }
    .container {
      max-width: 1200px;
      margin: 20px auto;
      padding: 20px;
      background: #fff;
      border-radius: 8px;
    }
    /* 탭 메뉴 스타일 */
    .tab-menu {
      list-style: none;
      display: flex;
      border-bottom: 2px solid #000;
      margin-bottom: 20px;
    }
    .tab-menu li {
      margin-right: 10px;
    }
    .tab-menu li a {
      display: inline-block;
      padding: 12px 20px;
      text-decoration: none;
      color: #000;
      border: 1px solid #000;
      border-bottom: none;
      border-radius: 5px 5px 0 0;
      background: #fff;
      transition: background 0.3s, color 0.3s;
    }
    .tab-menu li a.active {
      background: #000;
      color: #fff;
    }
    /* 탭 콘텐츠 스타일 */
    .tab-content {
      border: 1px solid #000;
      padding: 20px;
      border-radius: 0 5px 5px 5px;
      background: #fff;
    }
    .tab-content > div {
      display: none;
    }
    .tab-content > div.active {
      display: block;
    }
    /* 섹션 제목 스타일 */
    h2.section-title {
      font-family: 'Oswald', sans-serif;
      font-size: 2.5rem;
      color: #222;
      text-transform: uppercase;
      margin-bottom: 20px;
      text-align: center;
      position: relative;
    }
    h2.section-title::after {
      content: '';
      display: block;
      width: 60px;
      height: 3px;
      background: #d4af37;
      margin: 10px auto 0;
    }
    /* FAQ 탭용 테이블 스타일 */
    table {
      width: 100%;
      border-collapse: separate;
      border-spacing: 0 8px;
      margin-bottom: 20px;
      font-size: 0.95rem;
    }
    thead {
      background: #333;
      color: #fff;
    }
    thead th {
      padding: 12px;
      border: 1px solid #333;
    }
    tbody tr {
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 2px 4px rgba(0,0,0,0.05);
    }
    tbody td {
      background: #fff;
      padding: 12px;
      text-align: center;
      border: 1px solid #ccc;
      color: #000;
    }
    tbody tr:hover td {
      background: #f9f9f9;
    }
    /* 기존 버튼 스타일 (btn 클래스) */
    .btn {
      display: inline-flex;
      align-items: center;
      gap: 5px;
      background-color: #000000;
      color: #fff;
      padding: 10px 16px;
      border: none;
      border-radius: 5px;
      text-decoration: none;
      font-size: 1rem;
      transition: background-color 0.3s, transform 0.2s;
    }
    .btn:hover {
      background-color: #333333;
      transform: translateY(-2px);
    }
    /* 커스텀 채널 버튼 스타일 */
    .custom-button-1 {
      font-family: 'Roboto', sans-serif;
      font-size: 1rem;
      color: #FFD700; /* 느와르 느낌의 노란색 */
      background: linear-gradient(135deg, #000000 0%, #434343 100%); /* 블랙 기반 그라데이션 */
      border: none;
      border-radius: 50px;
      padding: 12px 30px;
      cursor: pointer;
      transition: all 0.3s ease;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.4);
    }
    .custom-button-1:hover {
      transform: translateY(-3px);
      box-shadow: 0 6px 15px rgba(0, 0, 0, 0.5);
    }
    .custom-button-1:focus {
      outline: none;
    }
    /* 버튼 컨테이너: 수평 배치를 위한 Flexbox */
    .btn-container {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin: 40px 0;
    }
    /* 채팅 영역 스타일 (필요 시) */
    .chat-history {
      border: 1px solid #ccc;
      padding: 10px;
      height: 300px;
      overflow-y: scroll;
      background: #fafafa;
      margin-bottom: 20px;
    }
    .chat-message {
      margin-bottom: 10px;
    }
    .chat-form textarea {
      width: 100%;
      height: 80px;
      padding: 8px;
    }
    .chat-form button {
      margin-top: 10px;
    }
    /* fade-in 애니메이션 (옵션) */
    .fade-in {
      animation: fadeInUp 0.8s forwards;
    }
    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(30px); }
      to { opacity: 1; transform: translateY(0); }
    }
    /* 반응형 디자인 */
    @media (max-width: 768px) {
      h2.section-title {
        font-size: 2rem;
      }
      .tab-menu li a {
        padding: 10px 16px;
        font-size: 0.9rem;
      }
      table thead th, table tbody td {
        padding: 10px;
      }
    }
  </style>
  
  <!-- jQuery (최신 버전 권장) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script>
    $(document).ready(function() {
      // 탭 클릭 이벤트 처리
      $('.tab-menu a').click(function(e) {
        e.preventDefault();
        $('.tab-menu a').removeClass('active');
        $(this).addClass('active');
        var target = $(this).attr('href');
        $('.tab-content > div').removeClass('active');
        $(target).addClass('active');
      });
      // 초기 탭 설정: 첫 번째 탭 활성화
      $('.tab-menu a:first').addClass('active');
      $('.tab-content > div:first').addClass('active');
    });
  </script>
  
  <!-- 네이버 지도 API (필요 시) -->
  <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=z1jfmxsy6g"></script>
  <script>
    function initDirectionMap() {
      var directionLocation = new naver.maps.LatLng(36.35107, 127.3797);
      var mapOptions = { center: directionLocation, zoom: 15 };
      var map = new naver.maps.Map('map', mapOptions);
      new naver.maps.Marker({ position: directionLocation, map: map, title: "대전광역시 미라클빌딩" });
    }
    window.onload = function() { initDirectionMap(); };
  </script>
  
  <!-- ChannelIO SDK 로드 및 boot 호출 -->
  <script type="text/javascript">
    (function() {
      var w = window;
      if (w.ChannelIO) {
        return w.console.error("ChannelIO script included twice.");
      }
      var ch = function() { ch.c(arguments); };
      ch.q = [];
      ch.c = function(args) { ch.q.push(args); };
      w.ChannelIO = ch;
      function l() {
        if (w.ChannelIOInitialized) { return; }
        w.ChannelIOInitialized = true;
        var s = document.createElement("script");
        s.type = "text/javascript";
        s.async = true;
        s.src = "https://cdn.channel.io/plugin/ch-plugin-web.js";
        var x = document.getElementsByTagName("script")[0];
        if (x.parentNode) { x.parentNode.insertBefore(s, x); }
      }
      if (document.readyState === "complete") {
        l();
      } else {
        w.addEventListener("DOMContentLoaded", l);
        w.addEventListener("load", l);
      }
    })();

    $(document).ready(function() {
      ChannelIO('boot', {
        "pluginKey": "984e0d08-004b-4539-9e62-1fd9a7b4cc91",  // 플러그인 키
        "memberHash": "984e0d08-004b-4539-9e62-1fd9a7b4cc91", // 억세스 시크릿
        "memberId": "${memberId}",                            // 회원이면 DB 아이디, 비회원이면 IP 주소
        "profile": {
          "name": "${memberName}"                             // 회원이면 이름, 비회원이면 IP 주소
        },
        "customLauncherSelector": ".custom-button-1",         // 커스텀 버튼 CSS 선택자
        "hideChannelButtonOnBoot": true                       // 기본 채널 버튼 숨기기
      });
    });
  </script>
  
</head>
<body>
  <div class="container fade-in">
    <h1 style="text-align: center; font-family: 'Oswald', sans-serif; font-size: 2.8rem;">고객센터</h1>
    
    <!-- 탭 메뉴 (FAQ, 1:1 상담 게시판, 실시간 채팅) -->
    <ul class="tab-menu">
      <li><a href="#tab1">자주 묻는 질문</a></li>
      <li><a href="#tab2">1:1 상담 게시판</a></li>
      <li><a href="#tab3">실시간 채팅</a></li>
    </ul>
    
    <!-- 탭 콘텐츠 영역 -->
    <div class="tab-content">
      <!-- FAQ 탭 -->
      <div id="tab1">
        <h2 class="section-title">자주 묻는 질문</h2>
        <p style="text-align: center;">
          <a class="btn" href="${contextPath}/customerCenter/faq/list.do">
            <span class="material-icons" style="font-size: 1.2rem;">list</span>
            FAQ 보러가기
          </a>
        </p>
      </div>
      
      <!-- 1:1 상담 게시판 탭 -->
      <div id="tab2">
        <h2 class="section-title">1:1 상담 게시판</h2>
        <div class="btn-container">
          <a class="btn" href="${contextPath}/customerCenter/consultation/form.do">
            <span class="material-icons" style="font-size: 1.2rem;">create</span>
            상담하기
          </a>
          <a class="btn" href="${contextPath}/customerCenter/consultation/list.do">
            <span class="material-icons" style="font-size: 1.2rem;">list</span>
            상담 내역
          </a>
        </div>
      </div>
      
      <!-- 실시간 채팅 탭 -->
      <div id="tab3">
        <h2 class="section-title">실시간 채팅</h2>
        <!-- 커스텀 채널 버튼 -->
        <div style="text-align: center; margin-bottom: 20px;">
          <button class="custom-button-1">채팅하기</button>
        </div>
        <p style="text-align: center; margin-top: 20px;">채팅 위젯은 화면 우측 하단에 나타납니다.</p>
      </div>
    </div>
  </div>
</body>
</html>
