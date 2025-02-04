<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쇼핑몰 로그인</title>
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
            left: 0;
            bottom: -4px;
            width: 0;
            height: 2px;
            background-color: white;
            transition: width 0.3s;
        }
        .navbar a:hover::after {
            width: 100%;
        }
        
        /* 로그인 컨테이너 */
        .login-container {
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
        .login-container h2 {
            margin-bottom: 30px;
            color: #333;
            font-size: 28px;
            text-align: center;
        }
    
        /* 입력 필드 */
        .login-container input {
            width: 100%;
            padding: 12px;
            margin: 8px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        .login-container input:focus {
            border-color: #000;
            box-shadow: 0 0 5px rgba(0,0,0,0.2);
            outline: none;
        }
    
        /* 비밀번호 보기/숨기기 토글 */
        .password-container {
            position: relative;
        }
        .password-toggle {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            color: #666;
            font-size: 14px;
            transition: color 0.3s;
        }
        .password-toggle:hover {
            color: #000;
        }
    
        /* 버튼 그룹 */
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        .login-btn {
            background-color: #000;
            color: white;
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: background-color 0.3s, transform 0.2s, box-shadow 0.2s;
        }
        .login-btn:hover {
            background-color: #333;
            transform: scale(1.02);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }
        .reset-btn {
            background-color: white;
            color: #000;
            flex: 1;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: background-color 0.3s, transform 0.2s, box-shadow 0.2s;
        }
        .reset-btn:hover {
            background-color: #f8f9fa;
            transform: scale(1.02);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
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
            margin: 10px auto 0;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    
        /* 소셜 로그인 */
        .social-login {
            margin-top: 30px;
            text-align: center;
        }
        .kakao-login {
            display: inline-block;
            width: 180px;
            transition: transform 0.2s, filter 0.2s;
            filter: grayscale(100%);
        }
        .kakao-login:hover {
            transform: translateY(-1px);
            filter: grayscale(0%);
        }
    
        /* 아이디/비밀번호 찾기 링크 */
        .find-links {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 15px;
        }
        .find-links a {
            color: #666;
            text-decoration: none;
            font-size: 14px;
            position: relative;
            transition: color 0.3s;
        }
        .find-links a:not(:last-child)::after {
            content: "|";
            margin-left: 15px;
            color: #ddd;
        }
        .find-links a:hover {
            color: #000;
        }
    
        /* 회원가입 링크 */
        .signup-link {
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
        }
        .signup-link a {
            color: #000;
            text-decoration: none;
            font-weight: 600;
            transition: text-decoration 0.3s;
        }
        .signup-link a:hover {
            text-decoration: underline;
        }
    
        /* 에러 메시지 */
        .error-message {
            color: #ff4444;
            text-align: center;
            margin: 15px 0;
            font-size: 14px;
        }
    
        /* 반응형 디자인 */
        @media (max-width: 480px) {
            .login-container {
                padding: 20px;
            }
            .button-group {
                flex-direction: column;
            }
            .login-btn, .reset-btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="logo">
            <a href="<c:url value='/main/main.do'/>" aria-label="쇼핑몰 메인 페이지로 이동">SHOPPING MALL</a>
        </div>
    </div>

    <div class="login-container">
        <h2>LOGIN</h2>
        <form id="loginForm" action="<c:url value='/member/login.do'/>" method="post">
            <input type="email" name="email" placeholder="이메일 주소" required aria-label="이메일 입력">
            <div class="password-container">
                <input type="password" name="password" id="password" placeholder="비밀번호" required aria-label="비밀번호 입력">
                <button type="button" class="password-toggle" onclick="togglePasswordVisibility()">보기</button>
            </div>

            <c:if test="${param.result == 'loginFailed'}">
                <div class="error-message">이메일 또는 비밀번호가 일치하지 않습니다</div>
            </c:if>

            <div class="button-group">
                <button type="submit" class="login-btn">로그인</button>
                <button type="reset" class="reset-btn">초기화</button>
            </div>
            <!-- 폼 전송 시 로딩 스피너 -->
            <div id="spinner" class="spinner"></div>
        </form>

        <!-- 소셜 로그인 영역 -->
        <div class="social-login">
            <a href="<c:url value='/member/kakaoLogin.do'/>" aria-label="카카오 로그인">
                <img class="kakao-login" src="<c:url value='/resources/image/kakao_login_button.png'/>" alt="카카오 로그인">
            </a>
        </div>

        <div class="find-links">
            <a href="<c:url value='/member/phoneVerificationForm.do'/>">아이디 찾기</a>
            <a href="<c:url value='/member/resetPassword.do'/>">비밀번호 찾기</a>
        </div>

        <div class="signup-link">
            아직 회원이 아니신가요? <a href="<c:url value='/member/addMemberForm.do'/>">회원가입</a>
        </div>
    </div>

    <script>
        // 비밀번호 보기/숨기기 토글 기능
        function togglePasswordVisibility() {
            const passwordInput = document.getElementById('password');
            const toggleButton = document.querySelector('.password-toggle');
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleButton.textContent = '숨기기';
            } else {
                passwordInput.type = 'password';
                toggleButton.textContent = '보기';
            }
        }
        
        // 로그인 폼 제출 시 로딩 스피너 표시
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            document.getElementById('spinner').style.display = 'block';
        });
    </script>
</body>
</html>
