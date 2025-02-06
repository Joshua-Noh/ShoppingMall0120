<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Material Icons (Tiles 템플릿 공통 헤더에 포함되지 않았다면 여기 추가) -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- 본문 영역 시작 -->
<div class="container">
  <h2>
    <!-- info 아이콘이 정상적으로 나오도록 텍스트 'info' 추가 -->
    <span class="material-icons" style="vertical-align:middle;">info</span>
    상담 문의 상세보기
  </h2>
  <div class="detail">
    <strong>제목:</strong> ${consultation.subject}
  </div>
  <div class="detail">
    <strong>문의 내용:</strong> ${consultation.message}
  </div>
  <div class="detail">
    <strong>답변:</strong> 
    <c:if test="${not empty consultation.reply}">
       ${consultation.reply}
    </c:if>
    <c:if test="${empty consultation.reply}">
       <em>답변 대기중</em>
    </c:if>
  </div>
  <div class="detail">
    <strong>상태:</strong> ${consultation.status}
  </div>
  <a class="back-link icon-btn" href="${pageContext.request.contextPath}/customerCenter/consultation/list.do">
    <!-- arrow_back 아이콘이 정상적으로 나오도록 텍스트 'arrow_back' 추가 -->
    <span class="material-icons" style="vertical-align:middle;">arrow_back</span> 목록으로 돌아가기
  </a>
</div>
<!-- 본문 영역 끝 -->

<!-- 페이지 전용 스타일 -->
<style>
  /* 기본 리셋 */
  * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
  }
  
  /* body에 대각선 배경: 상단/좌측 흰색, 하단/우측 검정 */
  body {
      font-family: 'Roboto', sans-serif;
      background: linear-gradient(135deg, #ffffff 50%, #000000 50%);
      color: #333;
      min-height: 100vh;
  }
  
  .container {
      max-width: 800px;
      margin: 30px auto;
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      opacity: 0;
      transform: translateY(20px);
      animation: fadeInUp 0.8s forwards;
  }
  
  h2 {
      font-family: 'Oswald', sans-serif;
      margin-bottom: 20px;
      text-align: center;
  }
  
  .detail {
      margin-bottom: 20px;
      padding: 10px;
      border-left: 4px solid #000;
      background-color: #f9f9f9;
      transition: background-color 0.3s ease;
  }
  
  /* .detail 호버 시 배경색 약간 변경 */
  .detail:hover {
      background-color: #f1f1f1;
  }
  
  .detail strong {
      display: inline-block;
      width: 100px;
      font-weight: 700;
  }
  
  .back-link {
      margin-top: 20px;
      display: inline-block;
      padding: 10px 15px;
      background-color: #000;
      color: #fff;
      text-decoration: none;
      border-radius: 4px;
      transition: background-color 0.3s ease, transform 0.2s ease;
  }
  
  .back-link:hover {
      background-color: #444;
      transform: translateY(-3px);
  }
  
  /* 아이콘 포함 버튼 스타일 */
  .icon-btn {
      display: inline-flex;
      align-items: center;
      gap: 5px;
      font-size: 1rem;
  }
  
  @keyframes fadeInUp {
      to {
          opacity: 1;
          transform: translateY(0);
      }
  }
  
  /* 반응형 디자인 */
  @media (max-width: 600px) {
      .detail strong {
          width: 80px;
      }
      .container {
          margin: 15px;
          padding: 15px;
      }
      h2 {
          font-size: 1.5rem;
      }
  }
</style>

<!-- 페이지 전용 JavaScript (필요 시 추가) -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
      console.log("상담 문의 상세보기 페이지의 본문이 로드되었습니다.");
      // 추가 JS 효과나 이벤트 처리 코드를 여기에 구현할 수 있습니다.
  });
</script>
