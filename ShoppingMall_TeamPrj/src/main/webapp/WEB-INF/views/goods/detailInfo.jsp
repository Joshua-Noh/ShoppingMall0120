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

/* 상품 상세 컨테이너 */
.product-detail-container {
    margin: 40px auto;
    max-width: 900px;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

/* 상품 메인 영역: 이미지와 정보 나란히 배치 */
.product-main {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}

/* 상품 이미지 영역 */
.product-image {
    flex: 1 1 300px;
    text-align: center;
}

.product-image img {
    width: 100%;
    max-width: 400px;
    height: auto;
    border-radius: 8px;
    transition: transform 0.3s ease;
}

.product-image img:hover {
    transform: scale(1.05);
}

/* 상품 정보 영역 */
.product-info {
    flex: 1 1 300px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.product-info h2 {
    font-size: 28px;
    margin-bottom: 15px;
}

.product-info .price {
    font-size: 22px;
    color: #e91e63;
    font-weight: bold;
    margin-bottom: 15px;
}

.product-info .detail-item {
    font-size: 16px;
    margin-bottom: 8px;
}

/* 장바구니 폼 */
.cart-form {
    margin-top: 20px;
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
    margin-bottom: 15px;
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
    to { opacity: 1; }
}

/* 반응형 (필요시 추가) */
@media (max-width: 600px) {
    .product-detail-container {
        padding: 15px;
        margin: 20px;
    }
    .product-main {
        flex-direction: column;
        align-items: center;
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

    <!-- 상품 메인 정보 영역 (이미지 + 정보) -->
    <div class="product-main">
        <!-- 상품 이미지 영역 -->
        <div class="product-image">
            <img alt="상품 이미지" src="${contextPath}/common/largeImage.do?product_id=${goods.product_id}&fileName=${goods.fileName}">
        </div>

        <!-- 상품 정보 영역 -->
        <div class="product-info">
            <h2>${goods.product_name}</h2>
            <div class="price">${goods.price} 원</div>
            <div class="detail-item">사이즈: ${goods.size}</div>
            <div class="detail-item">
                재고: 100
                <!-- 하드코딩: 실제 재고 값은 GoodsVO에 stock 속성을 추가 후 수정 필요 -->
            </div>
            <div class="detail-item">
                제조사: <c:out value="제조사 정보 없음" />
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
        </div>
    </div>

    <!-- 메시지 영역 -->
    <div class="message" id="message"></div>

    <!-- 상품 상세 설명 영역 -->
    <div class="product-description">
        <h2>상품 상세 정보</h2>
        <p>
            본 제품은 이태리 명품 스타일과 느와르 시크 룩의 정수를 담아낸 프리미엄 남성복입니다. 단순한 의류를 넘어, 남성 고급 패션의 새로운 패러다임을 제시하는 이 제품은 이태리 럭셔리 남성복의 전통과 현대적 감각이 조화를 이루어 탄생하였습니다. 클래식 명품 정장의 우아한 라인과 현대적 느와르 감성이 만나, 세련된 남성 정장을 완성하였으며, 이태리 디자인 정장 특유의 정교한 재단과 디테일이 돋보입니다.
        </p>
        <p>
            제품은 고급 감성 남성복의 새로운 기준을 제시하며, 어반 느와르 스타일과 이태리 감성 패션의 매력을 동시에 담고 있습니다. 특히, 럭셔리 스모킹 수트에서 영감을 받은 섬세한 봉제와 마감 처리, 시크한 명품 룩을 연출할 수 있는 슬림하면서도 여유 있는 실루엣은 남성 정장 트렌드를 선도합니다. 또한, 이태리 감성 수트와 고급 남성 캐주얼의 조화로운 만남을 통해, 모던하면서도 클래식한 매력을 동시에 느낄 수 있습니다.
        </p>
        <p>
            이 제품은 모던 느와르 패션의 강렬한 인상과 클래식 이태리 스타일의 전통적인 우아함을 결합하였습니다. 세련된 럭셔리 남성의 이미지를 극대화하기 위해, 감각적인 디테일과 정교한 소재 선택이 돋보이며, 감각적인 명품 정장의 아이코닉한 요소들을 모두 갖추고 있습니다. 특히, 이태리 감성 도시 남성들을 위해 디자인된 이 제품은, 도시적 감각과 동시에 느와르 무드 수트 특유의 미묘한 어둠의 매력을 선사합니다.
        </p>
        <p>
            더불어, 럭셔리 도시 감성을 구현한 디자인 요소와 이태리 명품 캐주얼의 자유로운 스타일링은 일상에서도 손쉽게 고급스러운 분위기를 연출할 수 있도록 도와줍니다. 이 제품은 단순한 의류가 아니라, 남성 스타일 아이콘으로 자리매김할 수 있는 완벽한 선택지로, 현대적 고급 남성복의 트렌드를 반영하여 제작되었습니다. 시크한 이태리 정장과 클래식 럭셔리 룩의 결합은 단연 돋보이며, 모든 남성에게 세련된 남성 감성을 선사합니다.
        </p>
        <p>
            특히, 제품의 디테일에는 장인의 정성이 깃들어 있으며, 최고급 원단과 최첨단 재단 기술을 적용해 편안하면서도 완벽한 핏을 자랑합니다. 이로써, 평범한 일상은 물론, 특별한 자리에서도 돋보이는 스타일을 연출할 수 있습니다. 이태리 명품 느와르의 정수를 담은 이 제품은, 당신의 스타일에 독보적인 품격을 더해줄 뿐만 아니라, 남성 패션의 새로운 아이콘으로 자리매김할 것입니다.
        </p>
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

<!-- jQuery 라이브러리 포함 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    // 장바구니 AJAX 처리
    $("#addCartForm").on("submit", function(e) {
        e.preventDefault(); // 기본 폼 제출 막기
        $.ajax({
            url: $(this).attr("action"),
            type: "POST",
            data: $(this).serialize(),
            dataType: "text", // 텍스트 응답을 기대
            success: function(response) {
                // response가 단순 문자열인 경우 메시지 영역에 표시
                $("#message").text(response).fadeIn().delay(2000).fadeOut();
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
