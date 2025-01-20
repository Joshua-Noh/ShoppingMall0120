<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>회원 목록</h2>
<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>이름</th>
            <th>비밀번호</th>
            <th>이메일</th>
            <th>휴대전화번호</th>
            <th>주소</th>
            <th>생년월일</th>
            <th>가입일</th>
            <th>성별</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="member" items="${membersList}">
            <tr>
                <td>${member.user_id}</td>
                <td>${member.user_name}</td>
                <td>${member.password}</td>
                <td>${member.email}</td>
                <td>${member.phone_number}</td>
                <td>${member.address}</td>
                <td>${member.date_of_birth}</td>
                <td>${member.join_date}</td>
                <td>${member.gender}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
