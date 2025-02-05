<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
/* 로고 및 소개 스타일 */
.logo-container {
    text-align: center;
    margin-bottom: 10px;
}
.logo-container a {
    display: inline-block;
}
.logo-container img {
    max-height: 200px;
    cursor: pointer;
    transition: transform 0.3s ease;
}
.logo-container img:hover {
    transform: scale(1.1);
}
.logo-container p {
    font-size: 14px;
    color: #666;
}
</style>

<!-- 푸터 -->
<div style="background-color: #000; color: #fff; padding: 20px; text-align: center; font-size: 14px; line-height: 1.6;">

    <!-- 로고 및 간단 소개 -->
    <div class="logo-container">
        <a href="${contextPath}/main/main.do">
            <img src="${contextPath}/resources/image/로고.png" alt="My Shop Logo">
        </a>
        <p>Noir - 당신의 스타일을 완성하는 남성 쇼핑몰</p>
    </div>

    <!-- 주요 링크 (MVC 방식) -->
    <div style="margin-bottom: 10px;">
        <a href="${contextPath}/company/introduction" style="color: #fff; text-decoration: none; margin: 0 10px;">회사소개</a>
        | 
        <a href="${contextPath}/company/terms" style="color: #fff; text-decoration: none; margin: 0 10px;">이용약관</a>
        | 
        <a href="${contextPath}/company/privacy" style="color: #fff; text-decoration: none; margin: 0 10px;">개인정보처리방침</a>
        | 
        <a href="${contextPath}/customerCenter" style="color: #fff; text-decoration: none; margin: 0 10px;">고객센터</a>
    </div>

    <!-- 회사 정보 -->
    <div style="font-size: 12px; color: #aaa;">
        <p>주소: 대전광역시 둔산동, 코리아IT</p>
        <p>대표전화: 1544-0000 | 팩스: 0505-123-4567</p>
        <p>사업자등록번호: 123-45-67890</p>
        <p>© 2025 Noir. All Rights Reserved.</p>
    </div>
</div>
