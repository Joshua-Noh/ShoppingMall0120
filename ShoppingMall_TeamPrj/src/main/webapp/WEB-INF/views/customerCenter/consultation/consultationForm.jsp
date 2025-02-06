<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Google Fonts & Material Icons (필요하다면 템플릿이 아닌 이 페이지에 직접 삽입) -->
<link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- 본문 영역 시작 -->
<div class="container">
  <h2>
    <span class="material-icons icon">create</span>
    상담 문의 작성
  </h2>
  <form action="${pageContext.request.contextPath}/customerCenter/consultation/submit.do" method="post">
    <label for="subject">제목</label>
    <input type="text" id="subject" name="subject" placeholder="문의 제목을 입력하세요" required />
    
    <label for="message">문의 내용</label>
    <textarea id="message" name="message" rows="8" placeholder="문의 내용을 입력하세요" required></textarea>
    
    <button type="submit">
      <span class="material-icons icon">send</span>
      문의 등록
    </button>
  </form>
</div>
<!-- 본문 영역 끝 -->

<!-- 페이지 전용 JavaScript -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    console.log("상담 문의 작성 페이지의 본문이 로드되었습니다.");
    // 필요한 추가 JS 효과나 이벤트를 여기에 구현하세요.
  });
</script>

<!-- 페이지 별 전용 스타일 -->
<style>
  /* 기본 리셋 */
  * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
  }
  
  /* body: Google Fonts를 적용하고, 대각선으로 절반 흰색, 절반 검정 배경 설정 */
  body {
      font-family: 'Roboto', sans-serif;
      background: linear-gradient(135deg, #ffffff 50%, #000000 50%);
      color: #333;
      min-height: 100vh;
  }
  
  .container {
      max-width: 800px;
      margin: 100px auto 30px auto; /* 상단 여백 100px, 하단 여백 30px */
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      animation: fadeInUp 0.8s forwards;
  }
  
  h2 {
      font-family: 'Oswald', sans-serif;
      text-align: center;
      margin-bottom: 20px;
  }
  
  form label {
      display: block;
      margin-bottom: 8px;
      font-weight: bold;
  }
  
  form input[type="text"],
  form textarea {
      width: 100%;
      padding: 10px;
      margin-bottom: 15px;
      border: 1px solid #ccc;
      border-radius: 4px;
      transition: border-color 0.3s;
  }
  
  form input[type="text"]:focus,
  form textarea:focus {
      border-color: #000;
  }
  
  button {
      display: inline-flex;
      align-items: center;
      padding: 10px 20px;
      background: #000;
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background 0.3s;
  }
  
  button:hover {
      background: #444;
  }
  
  .icon {
      margin-right: 5px;
  }
  
  @keyframes fadeInUp {
      from {
          opacity: 0;
          transform: translateY(20px);
      }
      to {
          opacity: 1;
          transform: translateY(0);
      }
  }
  
  /* 반응형 디자인 */
  @media (max-width: 600px) {
      .container {
          margin: 50px 15px 15px; /* 모바일에서는 상단 여백 조정 */
          padding: 15px;
      }
      h2 {
          font-size: 1.5rem;
      }
  }
</style>
