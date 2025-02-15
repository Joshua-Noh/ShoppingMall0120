<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 등록</title>
    <style>
        /* 전체 페이지 페이드인 효과 */
        body {
            margin: 0;
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #333;
            animation: fadeIn 1s ease-in;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
        /* 네비게이션 바 */
        .navbar {
            background-color: #000;
            padding: 15px 30px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar a {
            color: white;
            text-decoration: none;
            font-weight: 500;
            font-size: 18px;
            position: relative;
        }
        .navbar a::after {
            content: '';
            position: absolute;
            width: 0;
            height: 2px;
            background-color: white;
            left: 0;
            bottom: -4px;
            transition: width 0.3s;
        }
        .navbar a:hover::after {
            width: 100%;
        }
        
        /* 회원 등록 컨테이너 */
        .register-container {
            background-color: white;
            width: 100%;
            max-width: 400px;
            margin: 50px auto;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            opacity: 0;
            transform: translateY(20px);
            animation: slideIn 0.6s forwards;
            animation-delay: 0.5s;
        }
        @keyframes slideIn {
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        .register-container h2 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
            color: #333;
        }
        
        /* 폼 그룹 */
        .form-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 15px;
            position: relative;
        }
        .form-group label {
            margin-bottom: 5px;
            font-size: 14px;
            color: #333;
            transition: color 0.3s;
        }
        .form-group input,
        .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        .form-group input:focus,
        .form-group select:focus {
            border-color: #000;
            box-shadow: 0 0 5px rgba(0,0,0,0.2);
            outline: none;
        }
        .form-group input:focus + label,
        .form-group select:focus + label {
            color: #000;
        }
        
        /* 등록 버튼 */
        .register-container button {
            width: 100%;
            padding: 12px;
            margin: 20px 0;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            background-color: #000;
            color: white;
            font-weight: 600;
            transition: background-color 0.3s, transform 0.2s, box-shadow 0.2s;
        }
        .register-container button:hover {
            background-color: #333;
            transform: scale(1.02);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }
        
        /* 구분선 */
        .divider {
            margin: 30px 0;
            text-align: center;
            position: relative;
        }
        .divider::before,
        .divider::after {
            content: "";
            position: absolute;
            top: 50%;
            width: 40%;
            height: 1px;
            background-color: #ddd;
        }
        .divider::before {
            left: 0;
        }
        .divider::after {
            right: 0;
        }
        .divider span {
            display: inline-block;
            background-color: white;
            padding: 0 10px;
            font-size: 14px;
            color: #666;
        }
        
        /* 카카오 소셜 회원가입 버튼 */
        .kakao-signup {
            display: block;
            width: 100%;
            max-width: 250px;
            margin: 0 auto;
            filter: grayscale(100%);
            transition: transform 0.2s, filter 0.2s;
        }
        .kakao-signup:hover {
            transform: scale(1.05);
            filter: grayscale(0%);
        }
        
        /* 로딩 스피너 */
        .spinner {
            display: none;
            border: 4px solid #f3f3f3;
            border-top: 4px solid #000;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            animation: spin 1s linear infinite;
            margin: 0 auto;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        
        /* 반응형 디자인 */
        @media (max-width: 480px) {
            .register-container {
                padding: 20px;
            }
        }
        
        <!-- Daum 우편번호 API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo">
            <a href="<c:url value='/main/main.do'/>">SHOPPING MALL</a>
        </div>
    </div>

    <div class="register-container">
        <h2>회원 등록</h2>
        <!-- 일반 회원가입 폼 -->
        <form id="registerForm" action="${pageContext.request.contextPath}/member/addMember.do" method="post">
            <div class="form-group">
                <label for="user_name">이름</label>
                <input type="text" id="user_name" name="user_name" required />
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required />
            </div>
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" required />
            </div>
            <div class="form-group">
                <label for="phone_number">휴대전화번호</label>
                <input type="text" id="phone_number" name="phone_number" required />
            </div>
            <div class="form-group">
    <label for="sample6_postcode">우편번호</label>
    <input type="text" id="sample6_postcode" name="postcode" placeholder="우편번호" readonly required />
    <button type="button" onclick="sample6_execDaumPostcode()">우편번호 찾기</button>
</div>
<div class="form-group">
    <label for="address">주소</label>
    <input type="text" id="sample6_address" name="address" placeholder="주소" readonly required />
</div>
<div class="form-group">
    <label for="sample6_detailAddress">상세주소</label>
    <input type="text" id="sample6_detailAddress" name="detailAddress" placeholder="상세주소" required />
</div>
<div class="form-group">
    <label for="sample6_extraAddress">참고항목</label>
    <input type="text" id="sample6_extraAddress" name="extraAddress" placeholder="참고항목" required />
</div>


            <div class="form-group">
                <label for="date_of_birth">생년월일</label>
                <input type="date" id="date_of_birth" name="date_of_birth" required />
            </div>
            <div class="form-group">
                <label for="gender">성별</label>
                <select id="gender" name="gender" required>
                    <option value="Male">남성</option>
                    <option value="Female">여성</option>
                    <option value="Other">기타</option>
                </select>
            </div>
            <button type="submit">등록</button>
            <!-- 로딩 스피너 (폼 전송 시 표시) -->
            <div id="spinner" class="spinner"></div>
        </form>
        
        <!-- 구분선 -->
        <div class="divider">
            <span>또는</span>
        </div>
        
        <!-- 카카오 소셜 회원가입 버튼 -->
        <div>
            <a href="<c:url value='/member/kakaoLogin.do'/>">
                <img class="kakao-signup" src="<c:url value='/resources/image/kakao_add_button.png'/>" alt="카카오로 회원가입" />
            </a>
        </div>
    </div>

    <script>
        // 폼 전송 시 로딩 스피너 표시
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            document.getElementById('spinner').style.display = 'block';
        });
    </script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
    
</body>
</html>
