<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>기능 선택 - My Page</title>
    
    <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- Material Icons & Material Symbols -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">
    
    <style>
        /* 기본 리셋 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            background-color: #f5f5f5;
            color: #000;
            font-family: 'Roboto', sans-serif;
            line-height: 1.6;
        }
        /* 메뉴 컨테이너 스타일 */
        .menu-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: center;
        }
        .menu-container h1 {
            font-family: 'Oswald', sans-serif;
            margin-bottom: 20px;
        }
        .menu-item {
            display: block;
            margin: 10px 0;
            padding: 10px 0;
            background-color: #000;
            color: #fff;
            text-decoration: none;
            font-size: 1.2rem;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .menu-item:hover {
            background-color: #333;
        }
        /* 반응형 */
        @media (max-width: 768px) {
            .menu-container {
                width: 90%;
                margin: 30px auto;
                padding: 15px;
            }
            .menu-item {
                font-size: 1rem;
                padding: 8px 0;
            }
        }
    </style>
</head>
<body>
    <div class="menu-container">
        <h1>기능 선택</h1>
        <!-- 각 링크는 해당 기능의 컨트롤러 매핑 URL로 이동합니다. -->
        <a class="menu-item" href="${contextPath}/event/list.do">이벤트 기능</a>
        <a class="menu-item" href="${contextPath}/coupon/list.do">쿠폰 기능</a>
        <a class="menu-item" href="${contextPath}/membership/view.do">멤버십 기능</a>

    </div>
</body>
</html>
