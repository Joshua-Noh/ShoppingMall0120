<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <title>배송조회 결과</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
  <!-- Google Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <style>
    /* CSS Variables */
    :root {
      --primary-color: #4285f4;
      --primary-hover: #3367d6;
      --background-color: #f9f9f9;
      --text-color: #333;
      --card-bg: #fff;
      --header-bg: #4285f4;
      --header-text: #fff;
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
    h2, h3 {
      text-align: center;
      margin-bottom: 20px;
      font-weight: 500;
    }
    
    /* 컨테이너 스타일 */
    .container {
      max-width: 800px;
      margin: 0 auto;
      background-color: var(--card-bg);
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }
    
    /* 배송요약 영역 */
    .summary {
      border-bottom: 1px solid var(--border-color);
      padding-bottom: 20px;
      margin-bottom: 20px;
    }
    .summary p {
      margin: 5px 0;
      font-size: 1rem;
    }
    .summary .status {
      font-size: 1.2rem;
      font-weight: bold;
      color: var(--primary-color);
    }
    
    /* 타임라인 스타일 */
    .timeline {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    .timeline-item {
      position: relative;
      padding-left: 40px;
      margin-bottom: 20px;
    }
    .timeline-item:before {
      content: "";
      position: absolute;
      left: 18px;
      top: 0;
      bottom: 0;
      width: 2px;
      background: var(--border-color);
    }
    .timeline-icon {
      position: absolute;
      left: 0;
      top: 0;
      width: 36px;
      height: 36px;
      background-color: var(--primary-color);
      color: #fff;
      border-radius: 50%;
      text-align: center;
      line-height: 36px;
      font-size: 1rem;
    }
    .timeline-content {
      background: #f7f7f7;
      padding: 10px 15px;
      border-radius: 4px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.05);
    }
    .timeline-time {
      font-size: 0.9rem;
      color: #666;
      margin-bottom: 5px;
    }
    
    /* Responsive */
    @media (max-width: 768px) {
      .summary p, .timeline-content { font-size: 0.9rem; }
      .timeline-icon { width: 30px; height: 30px; line-height: 30px; font-size: 0.9rem; }
    }
  </style>
  
</head>
<body>
  <div class="container">
    <h2>배송조회 결과</h2>
    <div class="summary" id="summary">
      <!-- 배송요약 정보가 JavaScript로 채워집니다. -->
    </div>
    <ul class="timeline" id="timeline">
      <!-- 진행 이력이 JavaScript로 생성됩니다. -->
    </ul>
  </div>
  
  <script>
    // trackingResult는 컨트롤러에서 전달된 JSON 문자열입니다.
    // escapeXml="false"로 하여 JSON 문자열을 원래대로 출력합니다.
    var trackingResultStr = '<c:out value="${trackingResult}" escapeXml="false"/>';
    console.log("trackingResultStr:", trackingResultStr);
    var trackingData;
    try {
      trackingData = JSON.parse(trackingResultStr);
    } catch (e) {
      document.getElementById('summary').innerHTML = '<p>배송조회 결과를 불러오지 못했습니다.</p>';
      console.error(e);
    }
    
    // 배송요약 정보 생성
    var summaryDiv = document.getElementById('summary');
    var summaryHtml = '';
    summaryHtml += '<p class="status">' + trackingData.status + '</p>';
    summaryHtml += '<p><strong>배송업체:</strong> ' + trackingData.company +
                   ' <a href="tel:+8215880011" style="color: var(--primary-color); text-decoration: none;">(전화하기)</a></p>';
    summaryHtml += '<p><strong>배송기사:</strong> ' + trackingData.deliveryWorker +
                   ' <a href="tel:01071666758" style="color: var(--primary-color); text-decoration: none;">(전화하기)</a></p>';
    summaryHtml += '<p><strong>배송시간:</strong> ' + trackingData.time + '</p>';
    summaryHtml += '<p><strong>배송지:</strong> ' + trackingData.location + '</p>';
    summaryHtml += '<p>' + trackingData.description + '</p>';
    summaryDiv.innerHTML = summaryHtml;
    
    // 타임라인(진행 이력) 생성
    var timelineUl = document.getElementById('timeline');
    if (trackingData.allProgress && trackingData.allProgress.length > 0) {
      trackingData.allProgress.forEach(function(progress, index) {
        var li = document.createElement('li');
        li.className = 'timeline-item';
        
        // 아이콘: 순번을 표시합니다.
        var icon = document.createElement('div');
        icon.className = 'timeline-icon';
        icon.innerText = index + 1;
        
        var contentDiv = document.createElement('div');
        contentDiv.className = 'timeline-content';
        var contentHtml = '<p class="timeline-time">' + progress.time + '</p>';
        contentHtml += '<p><strong>' + progress.status.text + '</strong></p>';
        contentHtml += '<p>' + progress.description + '</p>';
        contentHtml += '<p><strong>위치:</strong> ' + progress.location.name + '</p>';
        contentDiv.innerHTML = contentHtml;
        
        li.appendChild(icon);
        li.appendChild(contentDiv);
        timelineUl.appendChild(li);
      });
    } else {
      timelineUl.innerHTML = '<li><p>진행 이력이 없습니다.</p></li>';
    }
  </script>
</body>
</html>
