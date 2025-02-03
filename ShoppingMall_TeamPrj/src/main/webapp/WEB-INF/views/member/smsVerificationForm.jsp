<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SMS 인증</title>
    <style>
        .container { width: 400px; margin: 50px auto; padding: 20px; background: #fff; border: 1px solid #ddd; border-radius: 8px; text-align: center; }
        .form-group { margin-bottom: 15px; text-align: left; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { display: block; width: 100%; padding: 10px; background: #333; color: #fff; border: none; border-radius: 4px; cursor: pointer; margin-top: 20px; }
        .btn:hover { background: #555; }
        .error { color: red; }
    </style>
</head>
<body>
    <div class="container">
        <h2>SMS 인증</h2>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <form action="<c:url value='/member/verifySmsCode.do'/>" method="post">
            <div class="form-group">
                <label for="code">인증 코드</label>
                <input type="text" id="code" name="code" placeholder="6자리 코드를 입력하세요" required />
            </div>
            <button type="submit" class="btn">인증 확인</button>
        </form>
    </div>
</body>
</html>
