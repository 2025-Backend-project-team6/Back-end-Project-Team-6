<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 작물 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/addCrop.css">
</head>
<body>
	<%@ include file="/JSP/header.jsp" %>
	
	<h5>작물추가 🌱</h5>
	<p>새로운 작물을 추가합니다.</p>
	
	<form action="${pageContext.request.contextPath}/mycrop.do" method="post">
		<label>텃밭 선택</label>
		<select name="garden">
			<c:forEach var="crop" items="${sessionScope.UserGardenList}">
				<option value="${crop.gardenname}">${crop.gardenname}</option>
			</c:forEach>
		</select>
		<br>
		
		<label>작물 선택</label>
		<input type="search" name="keyword" 
			   value="${keyword}" placeholder="작물 검색">
		<button type="submit" name="action" value="cropSearchBtn">검색</button>
		
		<c:if test="${not empty nullMessage}">
			<p>${nullMessage}</p>
		</c:if>
		
		<c:if test="${not empty searchCropList}">
			<c:forEach var="crop" items="${searchCropList}">
				
			</c:forEach>
		</c:if>
	</form>
</body>
</html>