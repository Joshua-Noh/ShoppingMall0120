<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
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
</head>
<body>
<div class="container">
    <h2>추가 정보 입력</h2>
    <form action="<c:url value='/member/updateSupplementInfo.do'/>" method="post">
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
</body>
</html>
