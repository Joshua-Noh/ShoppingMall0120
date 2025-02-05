<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>실시간 채팅</title>
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
      body { 
          font-family: 'Roboto', sans-serif; 
          background: #f4f4f4; 
          margin: 20px; 
      }
      .container { 
          max-width: 800px; 
          margin: 0 auto; 
          background: #fff; 
          padding: 20px; 
          box-shadow: 0 4px 12px rgba(0,0,0,0.08); 
      }
      .chat-history { 
          border: 1px solid #ccc; 
          padding: 10px; 
          height: 300px; 
          overflow-y: scroll; 
          background: #fafafa; 
      }
      .chat-message { 
          margin-bottom: 10px; 
      }
      .chat-form { 
          margin-top: 20px; 
      }
      .chat-form textarea { 
          width: 100%; 
          height: 80px; 
          padding: 8px; 
          box-sizing: border-box;
      }
      .chat-form button { 
          padding: 10px 20px; 
          background: #000; 
          color: #fff; 
          border: none; 
          cursor: pointer; 
          margin-top: 10px;
      }
    </style>
</head>
<body>
  <div class="container">
    <h2>실시간 채팅</h2>
    
    <!-- 채팅 기록 영역 (chatHistory) -->
    <div class="chat-history">
      <c:forEach var="chat" items="${chatHistory}">
        <div class="chat-message">
          <strong><c:out value="${chat.sender}"/>:</strong>
          <c:out value="${chat.message}"/>
          <span style="font-size:0.8rem; color:#999;">(${chat.sentAt})</span>
        </div>
      </c:forEach>
    </div>
    
    <!-- 채팅 메시지 전송 폼 -->
    <div class="chat-form">
      <form action="${pageContext.request.contextPath}/customerCenter/chat/send.do" method="post">
        <textarea name="message" placeholder="메시지를 입력하세요" required></textarea>
        <button type="submit">전송</button>
      </form>
    </div>
  </div>
</body>
</html>
