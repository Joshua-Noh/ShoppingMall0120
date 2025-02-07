<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- 생년월일(Date)를 yyyy-MM-dd 형식으로 포맷 -->
<fmt:formatDate value="${memberInfo.date_of_birth}" pattern="yyyy-MM-dd" var="formattedBirthDate" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <title>내 상세 정보 수정</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
  
  <!-- Google Material Icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <!-- jQuery (최신 버전) -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
  <style>
    /* Global Styles */
    body {
      font-family: 'Roboto', sans-serif;
      background-color: #f7f7f7;
      margin: 0;
      padding: 0;
      color: #333;
    }
    .container {
      max-width: 800px;
      margin: 40px auto;
      background: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      padding: 20px;
    }
    h3 {
      text-align: center;
      font-weight: 500;
      margin-bottom: 24px;
    }
    /* 테이블 대신 flexbox를 사용하여 form-row 스타일 적용 */
    .form-row {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      padding: 12px 0;
      border-bottom: 1px solid #e0e0e0;
    }
    .form-row:last-child {
      border-bottom: none;
    }
    .form-label {
      flex: 0 0 150px;
      font-weight: 500;
      margin-bottom: 8px;
    }
    .form-input {
      flex: 1;
      display: flex;
      align-items: center;
    }
    .form-input input[type="text"],
    .form-input input[type="password"],
    .form-input input[type="date"] {
      width: 100%;
      padding: 8px 12px;
      font-size: 1rem;
      border: 1px solid #ccc;
      border-radius: 4px;
      outline: none;
    }
    .form-input input[type="button"] {
      background-color: #4285f4;
      color: #fff;
      border: none;
      padding: 8px 16px;
      margin-left: 12px;
      border-radius: 4px;
      font-size: 0.9rem;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    .form-input input[type="button"]:hover {
      background-color: #3367d6;
    }
    /* 라디오 그룹 스타일 */
    .radio-group {
      display: flex;
      align-items: center;
    }
    .radio-group label {
      margin-right: 16px;
      cursor: pointer;
    }
    /* 버튼 그룹 */
    .btn-group {
      text-align: center;
      margin-top: 24px;
    }
    .btn-group input[type="button"] {
      background-color: #aaa;
      color: #fff;
      border: none;
      padding: 10px 20px;
      border-radius: 4px;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.3s ease;
      margin: 0 8px;
    }
    .btn-group input[type="button"]:hover {
      background-color: #888;
    }
    /* 반응형 */
    @media (max-width: 600px) {
      .form-row {
        flex-direction: column;
        align-items: flex-start;
      }
      .form-label {
        margin-bottom: 4px;
      }
      .form-input input[type="button"] {
        margin-left: 0;
        margin-top: 8px;
      }
    }
  </style>
  
  <script>
    function fn_modify_member_info(attribute) {
      var frm_mod_member = document.frm_mod_member;
      var value = "";
      
      if (attribute === 'password') {
          value = frm_mod_member.password.value;
      } else if (attribute === 'gender') {
          var radios = frm_mod_member.gender;
          for (var i = 0; i < radios.length; i++) {
              if (radios[i].checked) {
                  value = radios[i].value;
                  break;
              }
          }
      } else if (attribute === 'date_of_birth') {
          value = frm_mod_member.date_of_birth.value;
      } else if (attribute === 'phone_number') {
          value = frm_mod_member.phone_number.value;
      } else if (attribute === 'email') {
          value = frm_mod_member.email.value;
      } else if (attribute === 'address') {
          value = frm_mod_member.address.value;
      }
      
      console.log("수정할 항목: " + attribute + ", 값: " + value);
      
      $.ajax({
          type: "post",
          async: false,
          url: "${contextPath}/mypage/modifyMyInfo.do",
          data: {
              attribute: attribute,
              value: value
          },
          success: function(data, textStatus) {
              if (data.trim() === 'mod_success') {
                  alert("회원 정보를 수정했습니다.");
              } else if (data.trim() === 'failed') {
                  alert("다시 시도해 주세요.");
              }
          },
          error: function(data, textStatus) {
              alert("에러가 발생했습니다." + data);
          }
      });
    }
  </script>
</head>
<body>
  <div class="container">
    <h3>
      <i class="material-icons" style="vertical-align: middle;">person</i>
      내 상세 정보 수정
    </h3>
    <form name="frm_mod_member">
      
      <!-- 아이디 (수정 불가) -->
      <div class="form-row">
        <div class="form-label">아이디</div>
        <div class="form-input">
          <input name="user_id" type="text" value="${memberInfo.user_id}" disabled />
        </div>
      </div>
      
      <!-- 비밀번호 -->
      <div class="form-row">
        <div class="form-label">비밀번호</div>
        <div class="form-input">
          <input name="password" type="password" value="${memberInfo.password}" />
          <input type="button" value="수정" onClick="fn_modify_member_info('password')" />
        </div>
      </div>
      
      <!-- 이름 (수정 불가) -->
      <div class="form-row">
        <div class="form-label">이름</div>
        <div class="form-input">
          <input name="user_name" type="text" value="${memberInfo.user_name}" disabled />
        </div>
      </div>
      
      <!-- 성별 -->
      <div class="form-row">
        <div class="form-label">성별</div>
        <div class="form-input">
          <div class="radio-group">
            <c:choose>
              <c:when test="${memberInfo.gender == '101'}">
                <label>
                  <input type="radio" name="gender" value="101" checked /> 남성
                </label>
                <label>
                  <input type="radio" name="gender" value="102" /> 여성
                </label>
              </c:when>
              <c:otherwise>
                <label>
                  <input type="radio" name="gender" value="101" /> 남성
                </label>
                <label>
                  <input type="radio" name="gender" value="102" checked /> 여성
                </label>
              </c:otherwise>
            </c:choose>
          </div>
          <input type="button" value="수정" onClick="fn_modify_member_info('gender')" />
        </div>
      </div>
      
      <!-- 생년월일 -->
      <div class="form-row">
        <div class="form-label">생년월일</div>
        <div class="form-input">
          <input type="date" name="date_of_birth" value="${formattedBirthDate}" />
          <input type="button" value="수정" onClick="fn_modify_member_info('date_of_birth')" />
        </div>
      </div>
      
      <!-- 전화번호 -->
      <div class="form-row">
        <div class="form-label">전화번호</div>
        <div class="form-input">
          <input type="text" name="phone_number" value="${memberInfo.phone_number}" />
          <input type="button" value="수정" onClick="fn_modify_member_info('phone_number')" />
        </div>
      </div>
      
      <!-- 이메일 -->
      <div class="form-row">
        <div class="form-label">이메일</div>
        <div class="form-input">
          <input type="text" name="email" value="${memberInfo.email}" />
          <input type="button" value="수정" onClick="fn_modify_member_info('email')" />
        </div>
      </div>
      
      <!-- 주소 -->
      <div class="form-row">
        <div class="form-label">주소</div>
        <div class="form-input">
          <input type="text" name="address" size="50" value="${memberInfo.address}" />
          <input type="button" value="수정" onClick="fn_modify_member_info('address')" />
        </div>
      </div>
      
      <!-- 버튼 그룹 (수정 취소 등) -->
      <div class="btn-group">
        <input type="hidden" name="command" value="modify_my_info" />
        <input name="btn_cancel_member" type="button" value="수정 취소" />
      </div>
      
    </form>
  </div>
</body>
</html>
