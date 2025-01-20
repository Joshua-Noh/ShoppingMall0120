<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 관리</title>
<style>
table {
	border-collapse: collapse;
	width: 80%;
	margin: 20px auto;
}

th, td {
	border: 1px solid #ddd;
	text-align: center;
	padding: 10px;
}

th {
	background-color: #f2f2f2;
}

.form-container {
	width: 80%;
	margin: 20px auto;
	padding: 20px;
	border: 1px solid #ddd;
}

.form-container input, .form-container button {
	margin: 5px;
}
</style>
</head>
<body>
	<h2 style="text-align: center;">회원 관리 페이지</h2>

	<!-- 회원 등록 -->
	<!-- 회원 등록 -->
	<div class="form-container">
		<h3>회원 등록</h3>
		<form action="${pageContext.request.contextPath}/member/addMember.do"
			method="post">
			이름: <input type="text" name="user_name" required /><br> 비밀번호: <input
				type="password" name="password" required /><br>
			<!-- 비밀번호 필드 추가 -->

			이메일: <input type="email" name="email" required /><br> 연락처: <input
				type="text" name="phone_number" required /><br> 주소: <input
				type="text" name="address" /><br> 생년월일: <input type="date"
				name="date_of_birth" /><br> 성별: <select name="gender">
				<option value="Male">남성</option>
				<option value="Female">여성</option>
				<option value="Other">기타</option>
			</select><br>
			<button type="submit">등록</button>
		</form>
	</div>


	<!-- 회원 목록 -->
	<div>
		<h3 style="text-align: center;">회원 목록</h3>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>이름</th>
					<th>이메일</th>
					<th>연락처</th>
					<th>주소</th>
					<th>생년월일</th>
					<th>가입일</th>
					<th>성별</th>
					<th>수정</th>
					<th>삭제</th>
					<!-- 삭제 열 추가 -->
				</tr>
			</thead>
			<tbody>
				<c:forEach var="member" items="${membersList}">
					<tr>
						<td>${member.user_id}</td>
						<td>${member.user_name}</td>
						<td>${member.email}</td>
						<td>${member.phone_number}</td>
						<td>${member.address}</td>
						<td>${member.date_of_birth}</td>
						<td>${member.join_date}</td>
						<td>${member.gender}</td>
						<td>
							<form
								action="${pageContext.request.contextPath}/member/updateMember.do"
								method="post">
								<input type="hidden" name="user_id" value="${member.user_id}" />
								이름: <input type="text" name="user_name"
									value="${member.user_name}" required /><br> 이메일: <input
									type="email" name="email" value="${member.email}" required /><br>
								연락처: <input type="text" name="phone_number"
									value="${member.phone_number}" /><br> 주소: <input
									type="text" name="address" value="${member.address}" /><br>
								생년월일: <input type="date" name="date_of_birth"
									value="${member.date_of_birth}" /><br> 성별: <select
									name="gender">
									<option value="Male"
										${member.gender == 'Male' ? 'selected' : ''}>남성</option>
									<option value="Female"
										${member.gender == 'Female' ? 'selected' : ''}>여성</option>
									<option value="Other"
										${member.gender == 'Other' ? 'selected' : ''}>기타</option>
								</select><br>
								<button type="submit">수정</button>
							</form>
						</td>
						<td>
							<!-- 삭제 버튼 추가 --> <a
							href="${pageContext.request.contextPath}/member/removeMember.do?id=${member.user_id}"
							onclick="return confirm('정말로 삭제하시겠습니까?');"> 삭제 </a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>
