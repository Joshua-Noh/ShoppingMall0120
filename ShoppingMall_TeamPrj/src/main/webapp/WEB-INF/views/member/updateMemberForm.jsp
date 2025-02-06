<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <!-- Google Fonts 및 Material Icons -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        /* Reset 및 기본 스타일 */
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Roboto', sans-serif; background: #f0f4f8; color: #333; }
        .container { width: 90%; max-width: 900px; margin: 50px auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h2, h3 { text-align: center; margin-bottom: 20px; color: #4a90e2; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 10px; text-align: center; }
        form { display: flex; flex-direction: column; }
        .form-group { margin-bottom: 15px; }
        label { font-weight: bold; margin-bottom: 5px; display: block; }
        input, select { padding: 10px; border: 1px solid #ddd; border-radius: 4px; width: 100%; }
        input:focus, select:focus { border-color: #4a90e2; outline: none; }
        button { padding: 12px; background: #000000; color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; transition: background 0.3s; }
        button:hover { background: #333333; }
        .action-btn { padding: 6px 12px; font-size: 0.9rem; margin: 0 5px; }
        .edit-btn { background: #000000; }
        .edit-btn:hover { background: #333333; }
        .delete-btn { background: #f44336; }
        .delete-btn:hover { background: #da190b; }
        .center { text-align: center; }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // 폼 유효성 검사 등 필요한 스크립트를 추가할 수 있음
        });
    </script>
</head>
<body>
    <div class="container">
        <h2>회원 정보 수정</h2>
        <!-- 관리자용: 전체 회원 목록을 보여주면서 수정 링크 제공 -->
        <c:if test="${not empty membersList}">
            <h3>전체 회원 목록</h3>
            <table>
                <thead>
                    <tr>
                        <th>회원 ID</th>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>휴대전화번호</th>
                        <th>수정</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="member" items="${membersList}">
                        <tr>
                            <td>${member.user_id}</td>
                            <td>${member.user_name}</td>
                            <td>${member.email}</td>
                            <td>${member.phone_number}</td>
                            <td class="center">
                                <!-- 수정 링크: 수정 폼에 해당 회원의 user_id 전달 -->
                                <a href="${pageContext.request.contextPath}/member/updateMemberForm.do?user_id=${member.user_id}">
                                    <button type="button" class="action-btn edit-btn">수정</button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <!-- 일반 사용자용: 본인 정보 수정 폼 -->
        <c:if test="${not empty member}">
            <form id="memberForm" action="${pageContext.request.contextPath}/member/updateMember.do" method="post">
                <input type="hidden" name="user_id" value="${member.user_id}" />
                <div class="form-group">
                    <label for="user_name">이름:</label>
                    <input type="text" id="user_name" name="user_name" value="${member.user_name}" required />
                </div>
                <div class="form-group">
                    <label for="email">이메일:</label>
                    <input type="email" id="email" name="email" value="${member.email}" required />
                </div>
                <div class="form-group">
                    <label for="phone_number">휴대전화번호:</label>
                    <input type="text" id="phone_number" name="phone_number" value="${member.phone_number}" placeholder="예: 010-1234-5678" />
                </div>
                <div class="form-group">
                    <label for="address">주소:</label>
                    <input type="text" id="address" name="address" value="${member.address}" placeholder="예: 서울특별시 강남구 ..." />
                </div>
                <div class="form-group">
                    <label for="date_of_birth">생년월일:</label>
                    <input type="date" id="date_of_birth" name="date_of_birth" value="${member.date_of_birth}" />
                </div>
                <div class="form-group">
                    <label for="gender">성별:</label>
                    <select id="gender" name="gender">
                        <option value="Male" ${member.gender == 'Male' ? 'selected' : ''}>남성</option>
                        <option value="Female" ${member.gender == 'Female' ? 'selected' : ''}>여성</option>
                        <option value="Other" ${member.gender == 'Other' ? 'selected' : ''}>기타</option>
                    </select>
                </div>
                <button type="submit">수정</button>
            </form>
        </c:if>
    </div>
</body>
</html>
