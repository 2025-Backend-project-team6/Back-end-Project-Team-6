<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 텃밭 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/myCrop.css">
</head>
<body>
	<%@ include file="/JSP/header.jsp"%>
	
	<h5>작물 관리🥕</h5>
	<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
		<button type="submit" name="action" value="addCropBtn">+ 새 작물 추가</button>
		<br>
		
		<input type="search" name="keyword"
			   value="${keyword}"
			   placeholder="작물 검색">
		<button type="submit" name="action" value="searchCropBtn">검색</button>
		<br>
		
		<button name="category" value="allCrop">전체</button>
		<c:forEach var="category" items="${sessionScope.cropCategoryList}">
			<button name="category" value="${category.crop_nm}">${category.crop_nm}</button>
		</c:forEach>
	</form>
	
	<c:if test="${not empty searchNullMessage}">
		<p>${searchNullMessage}</p>
	</c:if>
	
	<c:if test="${not empty searchMyCropList}">
		<c:forEach var="crop" items="${searchMyCropList}">
			<h3>${crop.nickname}</h3>
			<p>${crop.category}</p>
			<p>텃밭: ${crop.gardenname}</p>
			<p>📅 심은 날짜: ${crop.planted_date}</p>
			
			<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
				<button type="submit" name="action" value="viewDiaryBtn">농사 일지</button>
				<button type="submit" name="action" value="alertSettingBtn">알림 설정</button>
			</form>
		</c:forEach>
	</c:if>
	
	<c:if test="${not empty findByCategoryList}">
		<c:forEach var="crop" items="${findByCategoryList}">
			<h3>${crop.nickname}</h3>
			<p>${crop.category}</p>
			<p>텃밭: ${crop.gardenname}</p>
			<p>📅 심은 날짜: ${crop.planted_date}</p>
			
			<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
				<button type="submit" name="action" value="viewDiaryBtn">농사 일지</button>
				<button type="submit" name="action" value="alertSettingBtn">알림 설정</button>
			</form>
		</c:forEach>
	</c:if>
	
	<c:if test="${not empty sessionScope.allMyCropList}">
		<c:forEach var="crop" items="${sessionScope.allMyCropList}">
			<h3>${crop.nickname}</h3>
			<p>${crop.category}</p>
			<p>텃밭: ${crop.gardenname}</p>
			<p>📅 심은 날짜: ${crop.planted_date}</p>
			
			<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
				<button type="submit" name="action" value="viewDiaryBtn">농사 일지</button>
				<button type="submit" name="action" value="alertSettingBtn">알림 설정</button>
			</form>
		</c:forEach>
	</c:if>
	
</body>
</html>