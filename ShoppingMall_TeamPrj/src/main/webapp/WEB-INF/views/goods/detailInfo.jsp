<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
    request.setCharacterEncoding("utf-8");
%>

<!-- 이 파일은 타일즈 템플릿의 body 영역에 삽입됩니다. -->
<style>
    /* 기본 스타일 */
    body {
        margin: 0;
        background-color: #f8f9fa;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
        line-height: 1.6;
    }
    .product-detail-container {
        margin: 40px auto;
        max-width: 800px;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    }
    /* 상품 이미지 */
    .product-image {
        text-align: center;
        margin-bottom: 20px;
    }
    .product-image img {
        max-width: 100%;
        height: auto;
        border-radius: 8px;
        transition: transform 0.3s ease;
    }
    .product-image img:hover {
        transform: scale(1.05);
    }
    /* 상품 정보 */
    .product-info {
        font-size: 16px;
        margin-bottom: 20px;
    }
    .product-info div {
        margin-bottom: 10px;
    }
    .product-info .price {
        color: #e91e63;
        font-weight: bold;
    }
    /* 장바구니 폼 */
    .cart-form {
        margin-bottom: 20px;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
    }
    .cart-form label {
        font-size: 16px;
        margin-right: 10px;
    }
    .cart-form input[type="number"] {
        width: 70px;
        padding: 8px;
        font-size: 16px;
        margin-right: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        transition: border-color 0.3s;
    }
    .cart-form input[type="number"]:focus {
        border-color: #4CAF50;
    }
    .cart-form button {
        background-color: #4CAF50;
        color: #fff;
        border: none;
        padding: 10px 20px;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s, transform 0.2s;
    }
    .cart-form button:hover {
        background-color: #45a049;
        transform: scale(1.02);
    }
    /* 메시지 영역 */
    .message {
        margin-top: 20px;
        color: #f00;
        font-size: 16px;
    }
    /* 상품 상세 설명 */
    .product-description {
        margin-top: 30px;
        border-top: 1px solid #ddd;
        padding-top: 20px;
    }
    .product-description h2 {
        font-size: 20px;
        margin-bottom: 10px;
        border-bottom: 1px solid #ddd;
        padding-bottom: 5px;
    }
    .product-description p {
        font-size: 15px;
    }
    /* 탭 메뉴 영역 */
    .tab-menu {
        margin-top: 30px;
        border-bottom: 1px solid #ddd;
        display: flex;
    }
    .tab-menu button {
        flex: 1;
        padding: 10px;
        background: none;
        border: none;
        border-bottom: 3px solid transparent;
        font-size: 16px;
        cursor: pointer;
        transition: border-bottom 0.3s, color 0.3s;
    }
    .tab-menu button.active {
        border-bottom: 3px solid #000;
        color: #000;
    }
    .tab-content {
        display: none;
        margin-top: 20px;
        animation: fadeIn 0.5s ease;
    }
    .tab-content.active {
        display: block;
    }
    @keyframes fadeIn {
        from { opacity: 0; }
        to   { opacity: 1; }
    }
    /* 반응형 (필요시 추가) */
    @media (max-width: 600px) {
        .product-detail-container {
            padding: 15px;
            margin: 20px;
        }
        .cart-form {
            flex-direction: column;
            align-items: flex-start;
        }
        .cart-form input[type="number"], .cart-form button {
            margin-bottom: 10px;
        }
    }
</style>

<div class="product-detail-container">

    <!-- 상품 이미지 영역 -->
    <div class="product-image">
        <img alt="상품 이미지"
             src="${contextPath}/common/thumbnails.do?product_id=${goods.product_id}&fileName=${goods.fileName}">
    </div>

    <!-- 상품 기본 정보 영역 -->
    <div class="product-info">
        <div>상품명: <strong>${goods.product_name}</strong></div>
        <div>가격: <span class="price">${goods.price}</span> 원</div>
        <div>사이즈: ${goods.size}</div>
        <div>재고: 100 <!-- 하드코딩: 실제 재고 값은 GoodsVO에 stock 속성을 추가 후 수정 필요 --></div>
        <div>제조사: <c:out value="제조사 정보 없음" /></div>
    </div>

    <!-- 장바구니 폼 -->
    <div class="cart-form">
        <form id="addCartForm" method="post" action="${contextPath}/cart/addMyCart.do">
            <input type="hidden" name="product_id" value="${goods.product_id}" />
            <label for="quantity">수량:</label>
            <input type="number" id="quantity" name="quantity" value="1" min="1">
            <button type="submit">장바구니에 추가</button>
        </form>
    </div>

    <!-- 메시지 영역 -->
    <div class="message" id="message"></div>

    <!-- 상품 상세 설명 영역 -->
    <div class="product-description">
        <h2>상품 상세 정보</h2>
        <!-- 하드코딩된 설명; GoodsVO에 description 속성 추가 후 ${goods.description}로 변경 가능 -->
        <p>이 상품은 고품질 소재로 제작되었으며, 최신 트렌드를 반영한 디자인을 자랑합니다. 자세한 사양은 추후 업데이트 예정입니다.</p>
    </div>

    <!-- 탭 메뉴 영역 (리뷰, Q&A, 관련 상품) -->
    <div class="tab-menu">
        <button class="tab-btn active" data-tab="reviews">상품 리뷰</button>
        <button class="tab-btn" data-tab="qa">Q&A</button>
        <button class="tab-btn" data-tab="related">관련 상품</button>
    </div>
    <div id="reviews" class="tab-content active">
        <p>리뷰 내용이 여기에 표시됩니다.</p>
    </div>
    <div id="qa" class="tab-content">
        <p>Q&A 내용이 여기에 표시됩니다.</p>
    </div>
    <div id="related" class="tab-content">
        <p>관련 상품 내용이 여기에 표시됩니다.</p>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    // 장바구니 AJAX 처리
   
    	$("#addCartForm").on("submit", function(e) {
    	    e.preventDefault();
    	    $.ajax({
    	        url: $(this).attr("action"),
    	        method: "POST",
    	        data: $(this).serialize(),
    	        dataType: "json", // JSON 응답을 기대
    	        success: function(response) {
    	            $("#message").text(response.message).fadeIn().delay(2000).fadeOut();
    	        },
    	        error: function() {
    	            $("#message").text("장바구니 추가 중 오류가 발생했습니다.").fadeIn().delay(2000).fadeOut();
    	        }
    	    });
    	});

        // 탭 메뉴 기능: 버튼 클릭 시 해당 탭 콘텐츠를 표시
        $(".tab-btn").click(function() {
            var tabId = $(this).data("tab");
            $(".tab-btn").removeClass("active");
            $(this).addClass("active");
            $(".tab-content").removeClass("active").fadeOut(300, function() {
                $("#" + tabId).fadeIn(300).addClass("active");
            });
        });
    });
</script>
