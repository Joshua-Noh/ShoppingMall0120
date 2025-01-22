<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <style>
  header {
    display: flex;
    flex-direction: column;
    background-color: black; /* 헤더 배경 흰색 */
    border-bottom: 1px solid #eaeaea; /* 헤더 하단 구분선 */
    width: 100%;
    padding: 10px 0;
}
.top-header {
    display: flex;
    justify-content: space-between; /* 로고 왼쪽, 검색/로그인 오른쪽 */
    align-items: center; /* 수직 중앙 정렬 */
    padding: 0 280px; /* 좌우 공백을 40px로 설정 (이전보다 증가) */
}

.logo img {
    max-height: 50px; /* 로고 크기 조정 */
    cursor: pointer;
    transition: transform 0.3s ease;
}

.logo img:hover {
    transform: scale(1.1); /* 로고 확대 효과 */
}
  .list_view {
	width: 100%; color: rgb(51, 51, 51); font-size: 0.75em; margin-top: 20px; border-top-color: rgb(51, 51, 51); border-top-width: 2px; border-top-style: solid; border-collapse: collapse;
}

.list_view tr {
	margin: 0px; padding: 10px 0px; border-bottom-color: rgb(153, 153, 153); border-bottom-width: 1px; border-bottom-style: dotted; 
}

  .list_view td {
	margin: 0px; padding: 10px 0px; border-bottom-color: rgb(153, 153, 153); border-bottom-width: 1px; border-bottom-style: dotted;
}
  </style>
  
  <c:if test="${message=='cancel_order'}">
	<script>
	window.onload=function()
	{
	  init();
	}
	
	function init(){
		alert("주문을 취소했습니다.");
	}
	</script>
</c:if>
<script>
function fn_cancel_order(order_id){
	var answer=confirm("주문을 취소하시겠습니까?");
	if(answer==true){
		var formObj=document.createElement("form");
		var i_order_id = document.createElement("input"); 
	    
	    i_order_id.name="order_id";
	    i_order_id.value=order_id;
		
	    formObj.appendChild(i_order_id);
	    document.body.appendChild(formObj); 
	    formObj.method="post";
	    formObj.action="${contextPath}/mypage/cancelMyOrder.do";
	    formObj.submit();	
	}
}

</script>
</head>
<body>
<header>
<div class="top-header">
        <!-- 로고 -->
        <div class="logo">
            <a href="<%= request.getContextPath() %>/main/main.do">
                <img src="<%= request.getContextPath() %>/resources/image/NOIR_LOGO.png" alt="My Shop Logo">
            </a>
        </div></header>
   	<h1>최근주문내역</h1>
   	<table class="list_view" align=center>
			<tr style="background:black" color:"white" >
				<td>주문번호</td>
				<td>주문일자</td>
				<td>주문상품</td>
				<td>주문상태</td>
				<td>주문취소</td>
			</tr>
      <c:choose>
         <c:when test="${ empty myOrderList  }">
		  <tr>
		    <td colspan=5 class="fixed">
				  <strong>주문한 상품이 없습니다.</strong>
		    </td>
		  </tr>
        </c:when>
        <c:otherwise>
	      <c:forEach var="item" items="${myOrderList }"  varStatus="i">
	       	<td>${item.order_id }</td>
	       	<td>${item.order_date }</td>
	       	<td>${item.product_name }</td>
	       	<td>
			  <c:choose>
			    <c:when test="${item.delivery_state=='delivery_prepared' }">
			       배송준비중
			    </c:when>
			    <c:when test="${item.delivery_state=='cancel_order' }">
			       주문취소
			    </c:when>
		    </c:choose>
		    	<td>
				  <c:choose>
				   <c:when test="${item.delivery_state=='delivery_prepared'}">
				       <input  type="button" onClick="fn_cancel_order('${item.order_id}')" value="주문취소"  />
				   </c:when>
				   <c:otherwise>
				      <input  type="button" onClick="fn_cancel_order('${item.order_id}')" value="주문취소" disabled />
				   </c:otherwise>
				  </c:choose>
				</td>
          </c:forEach>
        </c:otherwise>
      </c:choose>
</body>
</html>