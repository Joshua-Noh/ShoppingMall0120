<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>회원 ID 찾기</title>
    <style>
        .container { width: 400px; margin: 50px auto; padding: 20px; text-align: center; background: #fff; border: 1px solid #ddd; border-radius: 8px; }
        .result { font-size: 18px; margin: 20px 0; }
        .error { color: red; }
        .btn { display: inline-block; padding: 10px 20px; background: #333; color: #fff; text-decoration: none; border-radius: 4px; }
        .btn:hover { background: #555; }
    </style>
</head>
<body>
<div class="container">
    <h2>회원 ID 찾기 결과</h2>
    <c:choose>
        <c:when test="${not empty userId}">
            <div class="result">
                회원님의 ID는 <strong>${userId}</strong> 입니다.
            </div>
            <a href="<c:url value='/member/loginForm.do'/>" class="btn">로그인 하러 가기</a>
        </c:when>
        <c:otherwise>
            <div class="error">해당 번호로 등록된 회원이 없습니다.</div>
            <a href="<c:url value='/member/phoneVerificationForm.do'/>" class="btn">다시 시도하기</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
