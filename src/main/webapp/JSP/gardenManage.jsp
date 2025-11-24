<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>텃밭 관리</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css">

</head>
<body>
	<%@ include file="/JSP/header.jsp" %>
	
	<h5>텃밭 관리</h5>
	<p>총 ${totalGardenCount}개의 텃밭을 관리중입니다.</p>
	
	<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
		<button type="submit" name="action" value="addGardenBtn">+ 새텃밭 추가</button>
		<button type="submit" name="action" value="viewGardenBtn">텃밭 둘러보기</button>
		<br>

		<input type="search" name="keyword" 
			   value="${keyword}"
			   placeholder="텃밭 검색">
		<button type="submit" name="action" value="searchGardenBtn">검색</button>
	</form>
	
	<c:if test="${not empty nullMessage}">
		<p>${nullMessage}</p>
	</c:if>

	<c:if test="${not empty searchGardenList}">
		<c:forEach var="garden" items="${searchGardenList}">
			<h3>${garden.gardenname}</h3>
			<p>🗺️ ${garden.location}</p>
			<p>🌱 ${garden.area}평 · 작물 ${garden.crop_count}</p>
			<p>📅 ${garden.start_date}</p>
			<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
				<input type="hidden" name="gardenid" value="${garden.gardenid}">
				<button type="submit" name="action" value="detailGardenBtn">상세보기</button>
			</form>
		</c:forEach>	
	</c:if>
	
	<c:if test="${not empty userGardenList}">
		<c:forEach var="garden" items="${userGardenList}">
			<h3>${garden.gardenname}</h3>
			<p>🗺️ ${garden.location}</p>
			<p>🌱 ${garden.area}평 · 작물 ${garden.crop_count}</p>
			<p>📅 ${garden.start_date}</p>
			<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
				<input type="hidden" name="gardenid" value="${garden.gardenid}">
				<button type="submit" name="action" value="detailGardenBtn">상세보기</button>
			</form>
		</c:forEach>	
	</c:if>
	
	<div>
		<p>총 텃밭 면적</p>
		<p>${totalArea}평</p>
	</div>
	<div>
		<p>총 재배 작물</p>
		<p>${totalCropCount}개</p>
	</div>
	<div>
		<p>관리중인 텃밭</p>
		<p>${totalGardenCount}개</p>
	</div>
	
	
</body>
</html>

