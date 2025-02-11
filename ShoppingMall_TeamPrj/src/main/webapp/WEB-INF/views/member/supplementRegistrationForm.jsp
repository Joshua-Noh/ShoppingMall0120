<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>추가 정보 입력</title>
  <style>
    /* 간단한 스타일 예시 */
    .container {
        width: 500px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
    }
    .container h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
        display: block;
        margin-bottom: 5px;
    }
    .form-group input {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .btn {
        width: 100%;
        padding: 10px;
        background-color: #333;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    .btn:hover {
        background-color: #555;
    }
  </style>
  <!-- jQuery 라이브러리 포함 -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
  <div class="container">
      <h2>추가 정보 입력</h2>
      <!-- 폼에 id 추가 -->
      <form id="updateSupplementForm" action="<c:url value='/member/updateSupplementInfo.do'/>" method="post">
          <div class="form-group">
              <label for="phone_number">휴대전화번호:</label>
              <input type="text" id="phone_number" name="phone_number" required />
          </div>
          <div class="form-group">
              <label for="address">주소:</label>
              <input type="text" id="address" name="address" required />
          </div>
          <div class="form-group">
              <label for="date_of_birth">생년월일:</label>
              <input type="date" id="date_of_birth" name="date_of_birth" required />
          </div>
          <button type="submit" class="btn">정보 업데이트</button>
      </form>
  </div>

  <script>
    $(document).ready(function(){
      $("#updateSupplementForm").submit(function(e){
        e.preventDefault(); // 기본 폼 제출 막기
        $.ajax({
          type: "POST",
          url: $(this).attr("action"),
          data: $(this).serialize(),
          dataType: "text",
          success: function(response){
            // 업데이트 성공 후 메인 페이지로 이동
            alert("정보 업데이트가 완료되었습니다.");
            window.location.href = "${contextPath}/main/main.do"; // 메인 페이지 URL (환경에 맞게 수정)
          },
          error: function(){
            alert("정보 업데이트 중 오류가 발생했습니다.");
          }
        });
      });
    });
  </script>
</body>
</html>
