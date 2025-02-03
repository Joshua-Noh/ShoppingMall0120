<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var loopSearch=true;
	function keywordSearch(){
		if(loopSearch==false)
			return;
	 var value=document.frmSearch.searchWord.value;
		$.ajax({
			type : "get",
			async : true, //false인 경우 동기식으로 처리한다.
			url : "${contextPath}/goods/keywordSearch.do",
			data : {keyword:value},
			success : function(data, textStatus) {
			    var jsonInfo = JSON.parse(data);
				displayResult(jsonInfo);
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다."+data);
			},
			complete : function(data, textStatus) {
				//alert("작업을완료 했습니다");
				
			}
		}); //end ajax	
	}
	</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<style>
/* 공통 헤더 스타일 */
.header {
    display: flex;
    flex-direction: column;
    background-color: #ffffff; /* 헤더 배경 흰색 */
    border-bottom: 1px solid #eaeaea; /* 헤더 하단 구분선 */
    width: 100%;
    padding: 10px 0;
}

/* 상단 섹션 */
.top-header {
    display: flex;
    justify-content: space-between; /* 로고 왼쪽, 검색/로그인 오른쪽 */
    align-items: center; /* 수직 중앙 정렬 */
    padding: 0 280px; /* 좌우 공백을 40px로 설정 (이전보다 증가) */
}

.logo img {
    max-height: 50px; /* 로고 크기 조정 */
    cursor: pointer;
    transition: transform 0.3s ease;
}

.logo img:hover {
    transform: scale(1.1); /* 로고 확대 효과 */
}

/* 검색창 */
.search-bar {
    display: flex;
    align-items: center;
    position: relative; /* 돋보기를 검색창 내부에 배치 */
    width: 300px;
    height: 40px;
    margin-left: auto; /* 검색창을 오른쪽으로 */
}

/* 검색 입력창 */
.search-bar input {
    flex: 1;
    padding: 10px 15px 10px 40px; /* 왼쪽에 돋보기 공간 확보 */
    border: 1px solid #ccc;
    border-radius: 20px;
    outline: none;
    font-size: 14px;
    height: 100%;
}

/* 돋보기 버튼 */
.search-bar button {
    position: absolute;
    left: 10px; /* 돋보기를 검색창 내부 왼쪽에 고정 */
    top: 28%;
    transform: translateY(-50%); /* 수직 가운데 정렬 */
    background: none;
    border: none;
    cursor: pointer;
}

.search-bar button i {
    font-size: 18px; /* 돋보기 아이콘 크기 */
    color: #333;
}



/* 로그인/회원가입 버튼 */
.auth-buttons {
    display: flex;
    gap: 8px;
}

.auth-buttons button {
    padding: 8px 15px;
    border: 1px solid #333;
    background-color: transparent;
    color: #333;
    border-radius: 20px;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.auth-buttons button:hover {
    background-color: #333;
    color: #fff;
}

/* 하단 섹션 */
.bottom-header {
    display: flex;
    justify-content: flex-start; /* 왼쪽 정렬 */
    align-items: center;
    padding: 8px 20px;
    background-color: #ffffff;
    padding: 8px 280px; /* 좌우 공백을 늘림 (기존 20px에서 60px로 조정) */
}

.nav-buttons {
    display: flex;
    gap: 15px; /* 카테고리 간격 */
}

.nav-buttons a {
    color: #000; /* 텍스트 검정색 */
    text-decoration: none;
    font-size: 16px;
    font-weight: bold;
    position: relative;
    padding-bottom: 5px;
}

.nav-buttons a:hover {
    color: #000; /* 텍스트 색상 유지 */
}

.nav-buttons a::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 2px;
    background-color: #000; /* 밑줄 검정색 */
    transition: width 0.3s ease;
}

.nav-buttons a:hover::after {
    width: 100%; /* 밑줄 확장 */
}

</style>
<body>
<div class="header">
    <!-- 상단 섹션 -->
    <div class="top-header">
        <!-- 로고 -->
        <div class="logo">
            <a href="<%= request.getContextPath() %>/main/main.do">
                <img src="<%= request.getContextPath() %>/resources/image/하얀 로고.png" alt="My Shop Logo">
            </a>
        </div>

	<div class="search-bar">
    <!-- 검색어 입력 및 제출 -->
    <form name="frmSearch" action="<%= request.getContextPath() %>/goods/searchGoods.do" method="get">
        <!-- 검색 버튼 -->
        <button type="submit" name="search" class="btn1">
            <i class="fas fa-search"></i> <!-- 아이콘 추가 -->
        </button>
        <!-- 검색어 입력 -->
        <input 
            name="searchWord" 
            class="main_input" 
            type="text" 
            placeholder="검색어를 입력하세요" 
            onKeyUp="keywordSearch()" 
        >
    	</form>
	 </div>
        <!-- 로그인/회원가입 -->
        <div class="auth-buttons">
            <c:choose>
                <c:when test="${isLogOn}">
                    <span>환영합니다, ${memberInfo.user_name}님!</span>
                    <button onclick="location.href='${contextPath}/mypage/myPageMain.do'">내 정보</button>
                    <button onclick="location.href='${contextPath}/member/logout.do'">로그아웃</button>
                </c:when>
                <c:otherwise>
                    <button onclick="location.href='${contextPath}/member/loginForm.do'">로그인</button>
                    <button onclick="location.href='${contextPath}/member/addMemberForm.do'">회원가입</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <!-- 하단 섹션 -->
    <div class="bottom-header">
        <div class="nav-buttons">
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=0">신상품</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=1">상의</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=2">하의</a>
            <a href="<%= request.getContextPath() %>/category.do?category_id=4">아우터</a>
            <a href="<%= request.getContextPath() %>/category.do?category_id=5">신발</a>
            <a href="<%= request.getContextPath() %>/category.do?category_id=6">특가세일</a>
            <a href="<%= request.getContextPath() %>/cart/myCartList.do">장바구니</a> <!-- 장바구니 추가 -->
        </div>
    </div>
</div>

</body>
</html>
