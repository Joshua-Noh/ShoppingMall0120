<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
  request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>메인 페이지</title>
  <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
   <div>${goods.product_name }</div>
   <div>${goods.price }</div>
   <div>${goods.size }</div>
   	<div>
   		<figure>
			<img alt="HTML5 &amp; CSS3"
				src="${contextPath}/common/thumbnails.do?product_id=${goods.product_id}&fileName=${goods.fileName}">
		</figure>
	</div>
</body>
</html>