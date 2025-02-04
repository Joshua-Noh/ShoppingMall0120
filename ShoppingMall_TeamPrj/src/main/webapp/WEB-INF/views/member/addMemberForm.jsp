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
                <label for="address">주소</label>
                <input type="text" id="address" name="address" required />
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
</body>
</html>
