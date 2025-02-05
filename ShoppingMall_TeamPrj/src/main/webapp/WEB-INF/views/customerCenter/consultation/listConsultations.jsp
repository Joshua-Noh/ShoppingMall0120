<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>1:1 상담 게시판</title>
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
       body { font-family: 'Roboto', sans-serif; background: #f4f4f4; margin: 20px; }
       .container { max-width: 800px; margin: 0 auto; background: #fff; padding: 20px; }
       table { width: 100%; border-collapse: collapse; }
       th, td { padding: 10px; border: 1px solid #ccc; text-align: left; }
       th { background: #000; color: #fff; }
       a { text-decoration: none; color: blue; }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script>
      $(document).ready(function(){
          // URL 파라미터에서 error 파라미터를 확인
          var params = new URLSearchParams(window.location.search);
          if (params.get("error") === "loginRequired") {
              alert("상담 문의를 작성하려면 로그인이 필요합니다.");
          }
      });
    </script>
</head>
<body>
  <div class="container">
    <h2>1:1 상담 게시판</h2>
    <!-- 새 상담 문의 작성 링크 -->
    <p><a href="${pageContext.request.contextPath}/customerCenter/consultation/form.do">새 상담 문의 작성</a></p>
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>상태</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="consultation" items="${consultationList}" varStatus="status">
          <tr>
            <td>${status.index + 1}</td>
            <td>
              <a href="${pageContext.request.contextPath}/customerCenter/consultation/detail.do?consultationId=${consultation.consultationId}">
                ${consultation.subject}
              </a>
            </td>
            <td>${consultation.userId}</td>
            <td>${consultation.createdAt}</td>
            <td>${consultation.status}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>
