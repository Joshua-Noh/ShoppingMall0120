<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FAQ 등록/수정</title>
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
    <h2>FAQ 등록/수정</h2>
    <form action="${pageContext.request.contextPath}/customerCenter/faq/submit.do" method="post">
      <c:if test="${not empty faq}">
        <input type="hidden" name="faqId" value="${faq.faqId}" />
      </c:if>
      <label for="question">질문</label>
      <input type="text" id="question" name="question" value="<c:out value='${faq.question}'/>" required />
      
      <label for="answer">답변</label>
      <textarea id="answer" name="answer" rows="8" required><c:out value="${faq.answer}"/></textarea>
      
      <button type="submit">저장</button>
    </form>
  </div>
</body>
</html>
