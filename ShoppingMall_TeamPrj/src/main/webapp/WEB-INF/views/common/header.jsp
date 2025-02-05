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
<style>
/* ✅ 헤더 전체 구조 */
.header {
    display: flex;
    flex-direction: column;
    background-color: #ffffff;
    border-bottom: 1px solid #eaeaea;
    width: 100%;
    position: relative; /* 검색창을 절대 위치로 놓기 위한 기준 */
}

/* ✅ 상단 섹션 (로고, 검색창, 로그인) */
.top-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 280px;
}

/* ✅ 로고 */
.logo img {
    max-height: 50px;
    cursor: pointer;
    transition: transform 0.3s ease;
}

.logo img:hover {
    transform: scale(1.1);
}

/* ✅ 검색창 */
.search-bar {
 	position: absolute; /* 절대 위치 설정 */
 	 left: 50%; /* 왼쪽에서 50% 위치 */
    display: flex;
    align-items: center;
    transform: translateX(-50%); /* 가운데 정렬 */
    border: 1px solid #ccc;
    border-radius: 5px;
    overflow: hidden;
    width: 300px;

}

.search-bar input {
    flex: 1;
    padding: 10px;
    border: none;
    outline: none;
}

.search-bar button {
    background-color: black;
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
}

.search-bar button:hover {
    background-color: #333;
}

/* ✅ 로그인 & 회원가입 */
.auth-buttons {
    display: flex;
    gap: 10px;
}

.auth-buttons button {
    padding: 8px 15px;
    border: 1px solid #333;
    background-color: transparent;
    color: #333;
    border-radius: 5px;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.auth-buttons button:hover {
    background-color: #333;
    color: #fff;
}
.auth-buttons span {
    font-family: 'Pretendard', 'Noto Sans KR', sans-serif; /* 네비게이션과 동일한 폰트 */
    font-size: 16px; /* 글자 크기 조절 */
    font-weight: 600; /* 글자를 조금 두껍게 */
    color: #333; /* 글자 색상 */
}



/* ✅ 하단 섹션 (카테고리) */
.bottom-header {
    display: flex;
    align-items: center;
    padding: 10px 280px;
    background-color: #ffffff;
}

/* ✅ 카테고리 정렬 */
.nav-buttons {
    display: flex;
    gap: 20px;
}

.nav-buttons a {
    color: #000;
    text-decoration: none;
    font-size: 16px;
    font-weight: bold;
    position: relative;
    padding-bottom: 5px;
}

.nav-buttons a:hover {
    color: #000;
}

/* ✅ hover 시 밑줄 효과 */
.nav-buttons a::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 2px;
    background-color: #000;
    transition: width 0.3s ease;
}

.nav-buttons a:hover::after {
    width: 100%;
}
</style>
</head>
<body>

<div class="header">
    <!-- ✅ 상단 섹션 -->
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
            <i class="fas fa-search"></i> <!-- 돋보기 아이콘 -->
        </button>
        <!-- 검색어 입력 -->
        <input 
            name="searchWord" 
            class="main_input" 
            type="text" 
            placeholder="검색어를 입력하세요" 
            onkeyup="keywordSearch()" 
            autocomplete="off"
        >
        <!-- 자동완성 결과를 표시할 영역 -->
        <div id="searchResults" class="autocomplete-results"></div>
    </form>
</div>
        <!-- 로그인/회원가입 -->
        <div class="auth-buttons">
            <c:choose>
                <c:when test="${isLogOn}">
                    <span class="welcome-message">환영합니다, ${memberInfo.user_name}님!</span>
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

    <!-- ✅ 하단 섹션 (카테고리 메뉴) -->
    <div class="bottom-header">
        <div class="nav-buttons">
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=0">신상품</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=1">티셔츠</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=2">셔츠/블라우스</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=3">니트/스웨터</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=4">팬츠</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=5">스커트</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=6">재킷</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=7">코트</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=8">원피스</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=9">정장</a>
            <a href="<%= request.getContextPath() %>/goods/goodsList.do?category_id=10">스포츠웨어</a>
            <a href="<%= request.getContextPath() %>/cart/myCartList.do">장바구니</a>
        </div>
    </div>
</div>

</body>
</html>
