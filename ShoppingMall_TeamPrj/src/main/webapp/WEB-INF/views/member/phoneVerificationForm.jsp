<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>휴대폰 인증</title>
    <style>
        .container { width: 400px; margin: 50px auto; padding: 20px; background: #fff; border: 1px solid #ddd; border-radius: 8px; }
        .container h2 { text-align: center; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { display: block; width: 100%; padding: 10px; background: #333; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #555; }
    </style>
</head>
<body>
<div class="container">
    <h2>휴대폰 인증</h2>
    <form action="<c:url value='/member/sendSmsCode.do'/>" method="post">
        <div class="form-group">
            <label for="phone">휴대폰 번호</label>
            <input type="text" id="phone" name="phone" placeholder="예: 01012345678" required />
        </div>
        <button type="submit" class="btn">인증 코드 전송</button>
    </form>
</div>
</body>
</html>
