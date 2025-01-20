<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 등록</title>
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

    .navbar a {
        color: white;
        text-decoration: none;
        margin: 0 10px;
        font-size: 16px;
    }

    .navbar a:hover {
        text-decoration: underline;
    }

    .register-container {
        background-color: white;
        width: 500px;
        margin: 50px auto;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        text-align: left;
    }

    .register-container h2 {
        text-align: center;
        margin-bottom: 20px;
        font-size: 20px;
        color: #333;
    }

    .register-container form {
        font-size: 14px;
    }

    .form-group {
        display: flex;
        align-items: center;
        margin-bottom: 15px;
    }

    .form-group label {
        width: 100px;
        font-size: 14px;
        color: #333;
    }

    .form-group input, .form-group select {
        flex: 1;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
    }

    .register-container button {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: none;
        border-radius: 4px;
        font-size: 14px;
        cursor: pointer;
        background-color: #333;
        color: white;
    }

    .register-container button:hover {
        background-color: #555;
    }
</style>



</head>
<body>
<div class="navbar">
<div class="logo">
<a href="<c:url value='/main/main.do'/>">쇼핑몰 로고</a>
</div>
</div>


<div class="register-container">
    <h2>회원 등록</h2>
    <form action="${pageContext.request.contextPath}/member/addMember.do" method="post">
        <div class="form-group">
            <label for="user_name">이름:</label>
            <input type="text" id="user_name" name="user_name" required />
        </div>
        <div class="form-group">
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required />
        </div>
        <div class="form-group">
            <label for="email">이메일:</label>
            <input type="email" id="email" name="email" required />
        </div>
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
        <div class="form-group">
            <label for="gender">성별:</label>
            <select id="gender" name="gender" required>
                <option value="Male">남성</option>
                <option value="Female">여성</option>
                <option value="Other">기타</option>
            </select>
        </div>
        <button type="submit">등록</button>
    </form>
</div>



</body>
</html>