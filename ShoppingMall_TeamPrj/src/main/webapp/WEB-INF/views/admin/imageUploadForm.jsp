<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
  request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>상품 이미지 업로드</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <style>
        /* ✅ 전체 배경을 연한 회색으로 설정 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4; /* 연한 회색 배경 */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        
        /* ✅ 중앙 정렬 + 박스 스타일 + 검정 테두리 */
        .upload-container {
            background-color: white;
            padding: 25px; /* ✅ 내부 여백 확보 */
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px; /* ✅ 박스 크기 조정 */
            border: 2px solid #333; /* ✅ 검정 테두리 */
        }

        h1 {
            color: #333;
            font-size: 22px;
            margin-bottom: 20px;
        }

        /* ✅ 폼 요소 스타일 */
        select, input, button {
            display: block;
            width: 100%;
            margin: 15px 0;
            padding: 12px; /* ✅ 입력 필드 크기 확대 */
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        select, input[type="file"] {
            background-color: #fff;
            color: #333;
        }

        select:focus, input[type="file"]:focus {
            outline: none;
            border: 2px solid #888;
        }

        /* ✅ 버튼 스타일 */
        .upload-btn {
            background-color: #333;
            color: white;
            cursor: pointer;
            transition: 0.3s;
            border: none;
        }

        .upload-btn:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div class="upload-container">
        <h1>상품 이미지 업로드</h1>
        <form method="post" action="${contextPath}/admin/upload" enctype="multipart/form-data">
            <!-- ✅ 카테고리 선택 -->
            <label>카테고리:</label>
            <select name="category" required>
                <option value="" disabled selected>카테고리를 선택하세요</option>
                <option value="신상품">신상품</option>
                <option value="티셔츠">티셔츠</option>
                <option value="니트/스웨터">니트/스웨터</option>
                <option value="팬츠">팬츠</option>
                <option value="재킷">재킷</option>
                <option value="코트">코트</option>
                <option value="오버핏">오버핏</option>
                <option value="정장">정장</option>
                <option value="스포츠웨어">스포츠웨어</option>
            </select>

            <!-- ✅ 파일 업로드 필드 -->
            <label>이미지 선택:</label>
            <input type="file" name="file" required>

            <!-- ✅ 업로드 버튼 -->
            <button type="submit" class="upload-btn">이미지 업로드</button>
        </form>
    </div>
</body>
</html>
