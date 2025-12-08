<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 텃밭 관리</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/gardenManage.css">

</head>
<body>
	<%@ include file="/JSP/header.jsp" %>

    <div class="page-container">
        <div class="title-row">
            <div class="title-box">
                <h2>텃밭 관리🌾</h2>
                <p>총 ${totalGardenCount}개의 텃밭을 관리중입니다.</p>
            </div>

            <div class="action-buttons">
                <form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
                    <button type="submit" name="action" value="addGardenBtn" class="add-btn">+ 새 텃밭 추가</button>
                </form>
            </div>
        </div>

        <form class="search-box" action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
            <input type="search" name="keyword" value="${keyword}" placeholder="텃밭 검색">
            <button type="submit" name="action" value="searchGardenBtn" class="search-btn">검색</button>
        </form>

        <c:if test="${not empty nullMessage}">
            <p class="null-msg">${nullMessage}</p>
        </c:if>

        <div class="garden-list">
            <c:forEach var="garden" items="${searchGardenList}">
                <div class="garden-card">
                    <h3>${garden.gardenname}</h3>
                    <p>📍 ${garden.location}</p>
                    <p>🌱 ${garden.area}평 · 작물 ${garden.crop_count}</p>
                    <p>📅 ${garden.start_date}</p>

                    <form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
                        <input type="hidden" name="gardenid" value="${garden.gardenid}">
                        <button type="submit" name="action" value="detailGardenBtn" class="detail-btn">상세보기</button>
                    </form>
                </div>
            </c:forEach>

            <c:forEach var="garden" items="${userGardenList}">
                <div class="garden-card">
                    <h3>${garden.gardenname}</h3>
                    <p>📍 ${garden.location}</p>
                    <p>🌱 ${garden.area}평 · 작물 ${garden.crop_count}</p>
                    <p>📅 ${garden.start_date}</p>

                    <form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
                        <input type="hidden" name="gardenid" value="${garden.gardenid}">
                        <button type="submit" name="action" value="detailGardenBtn" class="detail-btn">상세보기</button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <div class="summary-row">
            <div class="summary-box">
                <p>총 텃밭 면적</p>
                <h3>${totalArea}평</h3>
            </div>
            <div class="summary-box">
                <p>총 재배 작물</p>
                <h3>${totalCropCount}개</h3>
            </div>
            <div class="summary-box">
                <p>관리중인 텃밭</p>
                <h3>${totalGardenCount}개</h3>
            </div>
        </div>
    </div>
    
    <%@ include file="/JSP/footer.jsp" %>
</body>
</html>