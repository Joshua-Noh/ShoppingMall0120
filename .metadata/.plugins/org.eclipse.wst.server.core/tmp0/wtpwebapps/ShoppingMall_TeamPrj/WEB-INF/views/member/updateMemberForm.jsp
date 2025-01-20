<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>회원 정보 수정</h2>
<form action="${pageContext.request.contextPath}/member/updateMember.do" method="post">
    <input type="hidden" name="user_id" value="${member.user_id}" />
    이름: <input type="text" name="user_name" value="${member.user_name}" required /><br>
    이메일: <input type="email" name="email" value="${member.email}" required /><br>
    휴대전화번호: <input type="text" name="phone_number" value="${member.phone_number}" /><br>
    주소: <input type="text" name="address" value="${member.address}" /><br>
    생년월일: <input type="date" name="date_of_birth" value="${member.date_of_birth}" /><br>
    성별: 
    <select name="gender">
        <option value="Male" ${member.gender == 'Male' ? 'selected' : ''}>남성</option>
        <option value="Female" ${member.gender == 'Female' ? 'selected' : ''}>여성</option>
        <option value="Other" ${member.gender == 'Other' ? 'selected' : ''}>기타</option>
    </select><br>
    <button type="submit">수정</button>
</form>
