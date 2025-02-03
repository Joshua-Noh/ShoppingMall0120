<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 이메일 확인</title>
    <style>
        .container { width: 400px; margin: 50px auto; padding: 20px; text-align: center; background: #fff; border: 1px solid #ddd; border-radius: 8px; }
        .error { color: red; }
    </style>
</head>
<body>
    <div class="container">
        <h2>회원 이메일 확인</h2>
        <c:choose>
            <c:when test="${not empty email}">
                <p>회원 이메일: <strong>${email}</strong></p>
            </c:when>
            <c:otherwise>
                <p class="error">${error}</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>