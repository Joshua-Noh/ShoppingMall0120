<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

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
  <!-- Material Symbols Outlined -->
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">
  
  <style>
    /* 기본 리셋 및 전체 스타일 */
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { font-family: 'Roboto', sans-serif; background: #f4f4f4; color: #333; }
    .container { max-width: 1200px; margin: 20px auto; padding: 20px; background: #fff; }
    
    /* 탭 메뉴 스타일 */
    .tab-menu {
      list-style: none;
      display: flex;
      border-bottom: 2px solid #000;
      margin-bottom: 20px;
    }
    .tab-menu li { margin-right: 10px; }
    .tab-menu li a {
      display: block;
      padding: 10px 20px;
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
    .tab-content > div { display: none; }
    .tab-content > div.active { display: block; }
    
    /* 공통 섹션 스타일 (1:1 상담, FAQ, 실시간 채팅 내 목록 및 폼 등) */
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
      background: #d4af37;
      margin: 10px auto 0;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
      font-size: 0.95rem;
    }
    table thead { background: #000; color: #fff; }
    table thead th { padding: 10px; border: 1px solid #000; }
    table tbody tr { border: 1px dotted #000; }
    table tbody td { padding: 10px; text-align: center; border: 1px solid #000; background: #fff; color: #000; }
    table tbody tr:hover td { background: #f2f2f2; }
    a { text-decoration: none; color: blue; }
    button, input[type="button"] {
      padding: 8px 16px; background: #000; color: #fff; border: 1px solid #000; border-radius: 4px; cursor: pointer;
      transition: background 0.3s, color 0.3s;
    }
    button:hover, input[type="button"]:hover { background: #fff; color: #000; }
    .total-price { font-size: 1.2rem; font-weight: bold; color: #000; text-align: right; margin-bottom: 30px; }
    
    /* 채팅 영역 스타일 */
    .chat-history {
      border: 1px solid #ccc;
      padding: 10px;
      height: 300px;
      overflow-y: scroll;
      background: #fafafa;
      margin-bottom: 20px;
    }
    .chat-message { margin-bottom: 10px; }
    .chat-form textarea { width: 100%; height: 80px; padding: 8px; }
    .chat-form button { margin-top: 10px; }
    
    /* fade-in 애니메이션 (옵션) */
    .fade-in {
      animation: fadeInUp 0.8s forwards;
    }
    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(30px); }
      to { opacity: 1; transform: translateY(0); }
    }
  </style>
  
  <!-- jQuery (Spring 3.1.1과 Java 1.6 환경에 맞춰 오래된 버전 사용, 예: 1.7.2) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
  <script>
    $(document).ready(function(){
      // 탭 클릭 이벤트 처리
      $('.tab-menu a').click(function(e){
        e.preventDefault();
        $('.tab-menu a').removeClass('active');
        $(this).addClass('active');
        var target = $(this).attr('href');
        $('.tab-content > div').removeClass('active');
        $(target).addClass('active');
      });
      // 초기 탭 설정
      $('.tab-menu a:first').addClass('active');
      $('.tab-content > div:first').addClass('active');
    });
  </script>
  
  <!-- 네이버 지도 API 스크립트 (Client ID: z1jfmxsy6g) -->
  <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=z1jfmxsy6g"></script>
  <script>
    // 찾아오시는 길 섹션 전용 지도 초기화 함수
    function initDirectionMap() {
      // 대전광역시 미라클빌딩 좌표 (위도: 36.35107, 경도: 127.3797)
      var directionLocation = new naver.maps.LatLng(36.35107, 127.3797);
      var mapOptions = {
        center: directionLocation,
        zoom: 15
      };
      var map = new naver.maps.Map('map', mapOptions);
      var marker = new naver.maps.Marker({
        position: directionLocation,
        map: map,
        title: "대전광역시 미라클빌딩"
      });
    }
    
    // API 로드 완료 후 찾아오시는 길 지도 초기화
    window.onload = function() {
      initDirectionMap();
    };
  </script>
  
</head>
<body>
<div class="container fade-in">
  <h1 style="text-align:center; font-family: 'Oswald', sans-serif; font-size: 2.8rem;">고객센터</h1>
  
  <!-- 탭 메뉴 -->
  <ul class="tab-menu">
    <li><a href="#tab1">1:1 상담 게시판</a></li>
    <li><a href="#tab2">자주 묻는 질문</a></li>
    <li><a href="#tab3">실시간 채팅</a></li>
  </ul>
  
  <!-- 탭 콘텐츠 영역 -->
  <div class="tab-content">
    <!-- 1:1 상담 게시판 탭 -->
    <div id="tab1">
      <h2>1:1 상담 게시판</h2>
      <!-- 상담 게시글 목록 (모델: consultationList) -->
      <c:if test="${not empty consultationList}">
        <table>
          <thead>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>작성자</th>
              <th>작성일</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="consultation" items="${consultationList}" varStatus="status">
              <tr>
                <td>${status.index + 1}</td>
                <td><a href="${contextPath}/customerCenter/consultation/detail.do?consultationId=${consultation.consultationId}">${consultation.subject}</a></td>
                <td>${consultation.userId}</td>
                <td>${consultation.createdAt}</td>
                <td>${consultation.status}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>
      <c:if test="${empty consultationList}">
        <p>등록된 상담 문의가 없습니다.</p>
      </c:if>
      <p><a href="${contextPath}/customerCenter/consultation/form.do">새 상담 문의 작성</a></p>
    </div>
    
    <!-- FAQ 탭 -->
    <div id="tab2">
      <h2>자주 묻는 질문</h2>
      <c:if test="${not empty faqList}">
        <table>
          <thead>
            <tr>
              <th>번호</th>
              <th>질문</th>
              <th>등록일</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="faq" items="${faqList}" varStatus="status">
              <tr>
                <td>${status.index + 1}</td>
                <td><a href="${contextPath}/customerCenter/faq/detail.do?faqId=${faq.faqId}">${faq.question}</a></td>
                <td>${faq.createdAt}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>
      <c:if test="${empty faqList}">
        <p>등록된 FAQ가 없습니다.</p>
      </c:if>
      <p><a href="${contextPath}/customerCenter/faq/form.do">새 FAQ 등록</a></p>
    </div>
    
    <!-- 실시간 채팅 탭 -->
    <div id="tab3">
      <h2>실시간 채팅</h2>
      <!-- 채팅 기록 영역 (모델: chatHistory) -->
      <div class="chat-history">
        <c:if test="${not empty chatHistory}">
          <c:forEach var="chat" items="${chatHistory}">
            <div class="chat-message">
              <strong>${chat.sender}:</strong> ${chat.message} 
              <span style="font-size:0.8rem; color:#999;">(${chat.sentAt})</span>
            </div>
          </c:forEach>
        </c:if>
        <c:if test="${empty chatHistory}">
          <p>채팅 기록이 없습니다.</p>
        </c:if>
      </div>
      <!-- 채팅 메시지 전송 폼 -->
      <div class="chat-form">
        <form action="${contextPath}/customerCenter/chat/send.do" method="post">
          <textarea name="message" placeholder="메시지를 입력하세요" required style="width:100%; height:80px; padding:8px;"></textarea>
          <button type="submit" style="padding:10px 20px; background:#000; color:#fff; border:none; margin-top:10px;">전송</button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
