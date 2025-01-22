<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>상품 목록</title>
<style>
/* 상품 목록 그리드 */
#product-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
	/* 반응형 그리드 */
	gap: 20px; /* 카드 간격 */
	margin: 20px; /* 전체 마진 */
	padding: 10px;
}

/* 상품 카드 스타일 */
.product-card {
	border: 1px solid #ddd;
	border-radius: 8px;
	padding: 16px;
	text-align: center;
	background-color: #f9f9f9;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	transition: transform 0.2s ease-in-out;
}

/* 상품 이미지 */
.product-card img {
	max-width: 100%;
	height: auto;
	border-radius: 4px;
	margin-bottom: 10px;
}

/* 상품 이름 */
.product-card h3 {
	font-size: 1.2em;
	margin: 10px 0;
	color: #333;
}

/* 상품 가격 */
.product-card p {
	font-size: 1em;
	color: #666;
	margin: 10px 0;
}

/* 자세히 보기 링크 */
.detail-link {
	display: inline-block;
	margin-top: 10px;
	padding: 8px 12px;
	color: #fff;
	background-color: #007bff;
	text-decoration: none;
	border-radius: 4px;
	font-size: 0.9em;
}

.detail-link:hover {
	background-color: #0056b3;
}

/* 상품 카드 호버 효과 */
.product-card:hover {
	transform: translateY(-5px);
	box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
}

/* 페이징 스타일 */
#pagination {
	text-align: center;
	margin: 20px 0;
}

#pagination a {
	margin: 0 5px;
	text-decoration: none;
	color: #007bff;
}

#pagination a:hover {
	color: #0056b3;
}
</style>
</head>
<body>
	<main>
		<!-- 상품 목록을 감싸는 그리드 컨테이너 -->
		<div id="product-grid">
			<c:forEach var="product" items="${goodsList}">
				<div class="product-card">
					<!-- 한 개의 이미지 파일만 사용 -->
					<img
						src="<c:url value='/resources/images/product_image_1.jpg.jpg' />"
						alt="${product.product_name}" />
					<h3>${product.product_name}</h3>
					<p>${product.price}원</p>
					<a href="/goods/detailInfo.do?product_id=${product.product_id}"
						class="detail-link">자세히 보기</a>
				</div>
			</c:forEach>

		</div>
	</main>

	<!-- 페이징 처리 -->
	<div id="pagination">
		<c:forEach var="page" begin="1" end="${totalPages}">
			<a
				href="<c:url value='/goods/goodsList.do?page=${page}&category_id=${currentCategory}' />">${page}</a>
		</c:forEach>
		<a
			href="<c:url value='/goods/goodsList.do?page=${currentPage - 1}&category_id=${currentCategory}' />"
			style="visibility:${currentPage > 1 ? 'visible' : 'hidden'}">&lt;</a>
		<a
			href="<c:url value='/goods/goodsList.do?page=${currentPage + 1}&category_id=${currentCategory}' />"
			style="visibility:${currentPage < totalPages ? 'visible' : 'hidden'}">&gt;</a>
	</div>
</body>
</html>
