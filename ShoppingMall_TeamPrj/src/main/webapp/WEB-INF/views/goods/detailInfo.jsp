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
  <title>상품 상세 정보</title>
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f5f5f5;
    }
    .container {
      max-width: 600px;
      margin: 20px auto;
      padding: 20px;
      background: #fff;
      border: 1px solid #ddd;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    .product-image {
      text-align: center;
    }
    .product-image img {
      max-width: 100%;
      height: auto;
      border-radius: 8px;
    }
    .product-details {
      margin-top: 20px;
    }
    .product-details div {
      margin-bottom: 10px;
      font-size: 16px;
    }
    .cart-form {
      margin-top: 20px;
    }
    .cart-form input[type="number"] {
      width: 60px;
      padding: 5px;
      font-size: 16px;
      margin-right: 10px;
    }
    .cart-form button {
      background-color: #4CAF50;
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 4px;
      font-size: 16px;
      cursor: pointer;
    }
    .cart-form button:hover {
      background-color: #45a049;
    }
    .message {
      margin-top: 20px;
      color: #f00;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="product-image">
      <img alt="상품 이미지"
           src="${contextPath}/common/thumbnails.do?product_id=${goods.product_id}&fileName=${goods.fileName}">
    </div>
    <div class="product-details">
      <div>상품명: ${goods.product_name}</div>
      <div>가격: ${goods.price} 원</div>
      <div>사이즈: ${goods.size}</div>
    </div>
    <div class="cart-form">
      <form id="addCartForm" method="post" action="${contextPath}/cart/addMyCart.do">
        <input type="hidden" name="product_id" value="${goods.product_id}" />
        <label for="quantity">수량:</label>
        <input type="number" id="quantity" name="quantity" value="1" min="1" />
        <button type="submit">장바구니에 추가</button>
      </form>
    </div>
    <div class="message" id="message"></div>
  </div>

  <script>
    $(document).ready(function () {
      $("#addCartForm").on("submit", function (e) {
        e.preventDefault();
        $.ajax({
          url: $(this).attr("action"),
          method: "POST",
          data: $(this).serialize(),
          success: function (response) {
            let message = "";
            if (response === "add_success") {
              message = "장바구니에 상품이 추가되었습니다.";
            } else if (response === "quantity_updated") {
              message = "장바구니에 상품 수량이 업데이트되었습니다.";
            } else if (response === "login_required") {
              message = "로그인이 필요합니다.";
            }
            $("#message").text(message);
          },
          error: function () {
            $("#message").text("장바구니 추가 중 오류가 발생했습니다.");
          }
        });
      });
    });
  </script>
</body>
</html>
