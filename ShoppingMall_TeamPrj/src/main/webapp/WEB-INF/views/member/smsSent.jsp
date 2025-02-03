<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>인증 코드 전송 완료</title>
    <style>
        .container { width: 400px; margin: 50px auto; padding: 20px; text-align: center; background: #fff; border: 1px solid #ddd; border-radius: 8px; }
        .btn { display: block; width: 100%; padding: 10px; background: #333; color: #fff; border: none; border-radius: 4px; cursor: pointer; margin-top: 20px; }
        .btn:hover { background: #555; }
    </style>
</head>
<body>
    <div class="container">
        <h2>인증 코드 전송 완료</h2>
        <p>입력하신 휴대폰 번호로 인증 코드가 전송되었습니다.<br>
           전송된 코드를 확인 후 다음 단계로 진행해주세요.</p>
       <a href="<c:url value='/member/smsVerificationForm.do'/>" class="btn">인증 코드 입력하기</a>

    </div>
</body>
</html>
