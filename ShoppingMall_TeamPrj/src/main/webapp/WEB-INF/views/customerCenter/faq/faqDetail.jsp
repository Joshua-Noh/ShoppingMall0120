<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FAQ 상세보기</title>
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
      body { font-family: 'Roboto', sans-serif; background: #f4f4f4; }
      .container { max-width: 800px; margin: 20px auto; background: #fff; padding: 20px; }
      .detail { margin-bottom: 20px; }
      .detail strong { display: inline-block; width: 100px; }
      .back-link { margin-top: 20px; display: block; }
    </style>
</head>
<body>
  <div class="container">
    <h2>FAQ 상세보기</h2>
    <div class="detail">
      <strong>질문:</strong> ${faq.question}
    </div>
    <div class="detail">
      <strong>답변:</strong> ${faq.answer}
    </div>
    <a class="back-link" href="${pageContext.request.contextPath}/customerCenter/faq/list.do">목록으로 돌아가기</a>
  </div>
</body>
</html>
