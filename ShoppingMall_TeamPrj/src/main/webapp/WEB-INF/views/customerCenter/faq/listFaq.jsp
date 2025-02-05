<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FAQ 게시판</title>
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
       body { font-family: 'Roboto', sans-serif; background: #f4f4f4; }
       .container { max-width: 800px; margin: 20px auto; background: #fff; padding: 20px; }
       table { width: 100%; border-collapse: collapse; }
       th, td { padding: 10px; border: 1px solid #ccc; text-align: left; }
       th { background: #000; color: #fff; }
       a { text-decoration: none; color: blue; }
    </style>
</head>
<body>
  <div class="container">
    <h2>FAQ 게시판</h2>
    <p><a href="${pageContext.request.contextPath}/customerCenter/faq/form.do">새 FAQ 등록</a></p>
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>질문</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="faq" items="${faqList}" varStatus="status">
          <tr>
            <td>${status.index + 1}</td>
            <td><a href="${pageContext.request.contextPath}/customerCenter/faq/detail.do?faqId=${faq.faqId}">${faq.question}</a></td>
            <td>${faq.createdAt}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>
