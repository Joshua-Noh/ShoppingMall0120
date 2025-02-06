<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <!-- FAQ 등록 폼 콘텐츠 시작 -->
    <div class="faq-form-container">
      <h1>FAQ 등록</h1>
      <form action="${pageContext.request.contextPath}/customerCenter/faq/form.do" method="post">
        <div class="form-group">
          <label for="question">예상 질문</label>
          <input type="text" id="question" name="question" placeholder="예상 질문을 입력하세요" required />
        </div>
        <div class="form-group">
          <label for="answer">답변</label>
          <textarea id="answer" name="answer" rows="6" placeholder="답변을 입력하세요" required></textarea>
        </div>
        <button type="submit" class="btn">등록</button>
      </form>
    </div>
    <!-- FAQ 등록 폼 콘텐츠 끝 -->

    <style>
      /* 기본 리셋 */
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      /* FAQ 등록 폼 컨테이너 */
      .faq-form-container {
        max-width: 600px;
        margin: 40px auto;
        padding: 30px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      }
      .faq-form-container h1 {
        font-family: 'Oswald', sans-serif;
        font-size: 2.5rem;
        text-align: center;
        margin-bottom: 20px;
        color: #222;
      }
      .faq-form-container form {
        display: flex;
        flex-direction: column;
        gap: 20px;
      }
      .form-group {
        display: flex;
        flex-direction: column;
      }
      .form-group label {
        font-weight: bold;
        margin-bottom: 8px;
        color: #333;
      }
      .form-group input[type="text"],
      .form-group textarea {
        font-size: 1rem;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 4px;
        transition: border 0.3s;
      }
      .form-group input[type="text"]:focus,
      .form-group textarea:focus {
        border-color: #000;
        outline: none;
      }
      .btn {
        align-self: center;
        background-color: #000;
        color: #fff;
        padding: 12px 20px;
        border: none;
        border-radius: 4px;
        font-size: 1.1rem;
        cursor: pointer;
        transition: background-color 0.3s, transform 0.2s;
      }
      .btn:hover {
        background-color: #333;
        transform: translateY(-2px);
      }
      /* 반응형 디자인 */
      @media (max-width: 600px) {
        .faq-form-container {
          margin: 20px;
          padding: 20px;
        }
        .faq-form-container h1 {
          font-size: 2rem;
        }
        .btn {
          font-size: 1rem;
          padding: 10px 16px;
        }
      }
    </style>

    <script>
      // 페이지 로드 후 실행할 추가 JS (필요시)
      document.addEventListener('DOMContentLoaded', function() {
        console.log("FAQ 등록 폼 페이지 로드 완료");
      });
    </script>

