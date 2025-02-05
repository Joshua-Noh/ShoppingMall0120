<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상담 문의 작성</title>
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
      body { font-family: 'Roboto', sans-serif; background: #f4f4f4; }
      .container { max-width: 800px; margin: 20px auto; background: #fff; padding: 20px; }
      label { display: block; margin-bottom: 8px; }
      input[type="text"], textarea { width: 100%; padding: 8px; margin-bottom: 10px; }
      button { padding: 10px 20px; background: #000; color: #fff; border: none; cursor: pointer; }
    </style>
</head>
<body>
  <div class="container">
    <h2>상담 문의 작성</h2>
    <form action="${pageContext.request.contextPath}/customerCenter/consultation/submit.do" method="post">
      <label for="subject">제목</label>
      <input type="text" id="subject" name="subject" required />
      
      <label for="message">문의 내용</label>
      <textarea id="message" name="message" rows="8" required></textarea>
      
      <button type="submit">문의 등록</button>
    </form>
  </div>
</body>
</html>
