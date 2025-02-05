<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.Map, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>장바구니</title>
    
    <!-- Google Fonts: Oswald (제목), Roboto (본문) -->
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@400;700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Material Symbols Outlined -->
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">

    <style>
        /* 기본 리셋 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Roboto', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            line-height: 1.6;
        }
        
        /* 공통 header (타일즈 header는 별도 include 되어 있음) */

        /* 메인 컨테이너 */
        .container {
            width: 80%;
            margin: 120px auto 30px auto;  /* 상단 여백를 늘려 타일즈 헤더와 겹치지 않도록 함 */
            padding: 20px 30px;
            background-color: #ffffff;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            opacity: 0;
            transform: translateY(30px);
            transition: opacity 0.8s ease, transform 0.8s ease;
        }
        
        /* 제목 스타일 */
        .container h1 {
            font-family: 'Oswald', sans-serif;
            text-align: center;
            font-size: 2.4rem; /* 약간 크기를 줄임 */
            margin-bottom: 30px;
            color: #222;
            position: relative;
        }
        .container h1::after {
            content: '';
            display: block;
            width: 80px;
            height: 4px;
            background-color: #d4af37;
            margin: 10px auto 0;
        }
        
        /* 메시지 스타일 */
        .message {
            color: #d9534f;
            text-align: center;
            margin-bottom: 20px;
            font-size: 1.1rem;
        }
        
        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }
        thead {
            background-color: #f4f7f6;
        }
        th, td {
            padding: 12px 14px;
            text-align: center;
            border-bottom: 1px solid #eaeaea;
        }
        th {
            color: #555;
            font-weight: 600;
        }
        td {
            background-color: #fff;
        }
        tr:hover td {
            background-color: #f9f9f9;
        }
        
        /* 수정/삭제 버튼 스타일 */
        td button, form button {
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            background-color: #333;
            color: #fff;
            font-size: 0.9rem;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 5px;
        }
        td button:hover, form button:hover {
            background-color: #555;
            transform: scale(1.03);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        
        /* 주문하기 버튼 */
        form.checkout button {
            width: 100%;
            padding: 14px;
            font-size: 1rem;
            margin-top: 20px;
        }
        
        /* 푸터 스타일 (공통 인클루드 푸터 적용 시 그대로 유지) */
        .footer {
            background-color: #f4f7f6;
            text-align: center;
            padding: 20px;
            margin-top: 40px;
        }
        
        /* 추가 시각적 효과: 배경 그라데이션, hover 애니메이션 등 */
        .container:hover {
            box-shadow: 0 12px 24px rgba(0,0,0,0.15);
        }
        
        /* 모바일 반응형 */
        @media (max-width: 768px) {
            .container {
                width: 95%;
                margin-top: 100px;
            }
            table, th, td {
                font-size: 0.85rem;
            }
            .header .logo a {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>

    <!-- 공통 header 인클루드 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <!-- 메인 컨테이너 -->
    <div class="container" id="mainContainer">
        <!-- 회원 정보 출력 -->
        <h1>
            <span class="material-symbols-outlined" style="font-size: 2rem; vertical-align: middle; color: #d4af37;">shopping_cart</span>
            ${sessionScope.memberInfo.user_name}님 장바구니입니다.
        </h1>
        
        <!-- (필요시) 메시지 출력 -->
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
        
        <!-- 장바구니 테이블 -->
        <table>
            <thead>
                <tr>
                    <th>상품명</th>
                    <th>가격</th>
                    <th>수량</th>
                    <th>총 가격</th>
                    <th>작업</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cartItem" items="${cartMap.myCartList}">
                    <!-- 상품 정보 찾기 -->
                    <c:set var="goodsItem" value="${null}" />
                    <c:forEach var="goods" items="${cartMap.myGoodsList}">
                        <c:if test="${goods.product_id == cartItem.product_id}">
                            <c:set var="goodsItem" value="${goods}" />
                        </c:if>
                    </c:forEach>
                    
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${empty goodsItem}">
                                    상품 정보 없음
                                </c:when>
                                <c:otherwise>
                                    ${goodsItem.product_name}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty goodsItem}">
                                    가격 정보 없음
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber value="${goodsItem.price}" type="currency" />
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="updateCartQuantity.do" method="post">
                                <input type="hidden" name="cart_id" value="${cartItem.cart_id}" />
                                <input type="number" name="quantity" value="${cartItem.quantity}" min="1" style="width: 60px; padding: 5px;"/>
                                <input type="hidden" name="product_id" value="${cartItem.product_id}" />
                                <button type="submit">
                                    <span class="material-icons" style="font-size:1.2rem;">edit</span>
                                    수정
                                </button>
                            </form>
                        </td>
                        <td>
                            <c:if test="${!empty goodsItem}">
                                <fmt:formatNumber value="${cartItem.quantity * goodsItem.price}" type="currency" />
                            </c:if>
                        </td>
                        <td>
                            <form action="deleteCartItem.do" method="post">
                                <input type="hidden" name="cart_id" value="${cartItem.cart_id}" />
                                <button type="submit">
                                    <span class="material-icons" style="font-size:1.2rem;">delete</span>
                                    삭제
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <!-- 장바구니 총 합계 -->
        <h2 style="text-align: right; margin-top: 20px;">
            총 합계: <fmt:formatNumber value="${totalPrice}" type="currency" />
        </h2>
        
        <!-- 주문하기 폼 -->
        <form class="checkout" action="checkout.do" method="post">
            <button type="submit">
                <span class="material-symbols-outlined" style="font-size:1.5rem;">local_mall</span>
                주문하기
            </button>
        </form>
    </div>
    
    <!-- 공통 footer 인클루드 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <!-- 페이지 로드시 컨테이너 애니메이션 효과 -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const container = document.getElementById('mainContainer');
            setTimeout(() => {
                container.style.opacity = 1;
                container.style.transform = "translateY(0)";
            }, 200);
        });
    </script>
</body>
</html>
