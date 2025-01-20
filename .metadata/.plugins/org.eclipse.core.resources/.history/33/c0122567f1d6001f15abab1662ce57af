<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쇼핑몰 로그인</title>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f9f9f9;
}

.navbar {
    background-color: #333;
    color: white;
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #000;
    padding: 10px 20px;
    border-bottom: 2px solid #ffffff;
}
.navbar a {
    color: white;
    text-decoration: none;
    margin: 0 10px;
    font-size: 16px;
}

.navbar a:hover {
    text-decoration: underline;
}

.login-container {
    background-color: white;
    width: 100%;
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.login-container h2 {
    margin-bottom: 20px;
    font-size: 24px;
    color: #333;
}

.login-container input {
    width: calc(100% - 20px); /* padding과 margin을 고려한 크기 조정 */
    padding: 10px;
    margin: 10px auto;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
    display: block;
}

.login-container input::placeholder {
    color: #aaa;
}

.login-container button {
    width: 48%;
    padding: 10px;
    margin: 10px 1%;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
}

.login-btn {
    background-color: #333;
    color: white;
}

.reset-btn {
    background-color: #f5f5f5;
    color: #333;
    border: 1px solid #ccc;
}

.login-btn:hover {
    background-color: #555;
}

.reset-btn:hover {
    background-color: #e0e0e0;
}

.footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 10px 0;
    position: absolute;
    bottom: 0;
    width: 100%;
}
</style>
</head>
<body>
<div class="navbar">
    <div class="logo">
        <a href="<c:url value='/main/main.do'/>">쇼핑몰 로고</a>
    </div>
</div>

<div class="login-container">
    <h2>로그인</h2>
    <form action="<c:url value='/member/login.do'/>" method="post">
        <input type="email" name="email" placeholder="이메일을 작성해주세요" required>
        <input type="password" name="password" placeholder="비밀번호를 입력해주세요" required>
        <div>
            <button type="submit" class="login-btn">로그인</button>
            <button type="reset" class="reset-btn">초기화</button>
        </div>
    </form>
    <c:if test="${param.result == 'loginFailed'}">
        <p style="color: red;">로그인에 실패했습니다. 이메일과 비밀번호를 확인하세요.</p>
    </c:if>
</div>

</body>
</html>
