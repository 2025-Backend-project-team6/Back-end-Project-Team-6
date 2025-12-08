<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 작물 검색</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/cropSearch.css">
</head>
<body>
	<%@ include file="/JSP/header.jsp"%>
	
	<div>
		<h5>작물 백과사전📖</h5>
		<p>재배법, 수확 관리 정보를 확인하세요.</p>
	</div>
	
	<div>
		<form action="${pageContext.request.contextPath}/cropsearch.do" method="get">
			<input type="search" name="keyword"
				   value="${keyword}"
				   placeholder="작물명 검색">
			<button type="submit" name="action" value="cropSearchBtn">검색</button>
		</form>
		
		<form action="${pageContext.request.contextPath}/cropsearch.do" method="get">
			<input type="hidden" name="action" value="category">
			<select name="categorySelect" onchange="this.form.submit()">
				<option value="">전체 카테고리</option>
				<c:forEach var="category" items="${categoryList}">
					<option value="${category.crop_code}"
						<c:if test="${category.crop_code == selectedCategory}">selected</c:if>>
						${category.crop_nm}
					</option>
				</c:forEach>
			</select>
		</form>
		
		<form action="${pageContext.request.contextPath}/cropsearch.do" method="get">
			<input type="hidden" name="action" value="level">
			<select name="levelSelect" onchange="this.form.submit()">
				<option value="">전체 난이도</option>
					<option value="초급"
						<c:if test="${selectedLevel == '초급'}">selected</c:if>>초급</option>
					<option value="중급"
						<c:if test="${selectedLevel == '중급'}">selected</c:if>>중급</option>
					<option value="상급"
						<c:if test="${selectedLevel == '상급'}">selected</c:if>>상급</option>
			</select>
		</form>
	</div>
	
	<div>
		<c:if test="${not empty SearchCropnullMessage}">
			<p>${SearchCropnullMessage}</p>
		</c:if>
	</div>
	
	<div>
		<c:if test="${not empty searchCropList}">
			<c:forEach var="crop" items="${searchCropList}">
				<h5>${crop.crop_title}</h5>
				<p>${crop.category_name}</p>
				<p>${crop.difficulty_level}</p>
				<p>${crop.period_text}</p>
				<p>${crop.water_cycle}</p>
				<p>${crop.sunlight_hours}</p>
				
				<form action="${pageContext.request.contextPath}/detailcrop.do" method="get">
					<input type="hidden" name="cropid" value="${crop.cropid}">
					<button type="submit" name="action" value="detailCropBtn">자세히 보기</button>
				</form>
			</c:forEach>
		</c:if>
	</div>
	
	<div>
		<c:if test="${empty searchCropList && not empty levelList}">
			<c:forEach var="crop" items="${levelList}">
				<h5>${crop.crop_title}</h5>
				<p>${crop.category_name}</p>
				<p>${crop.difficulty_level}</p>
				<p>${crop.period_text}</p>
				<p>${crop.water_cycle}</p>
				<p>${crop.sunlight_hours}</p>
				
				<form action="${pageContext.request.contextPath}/detailcrop.do" method="get">
					<input type="hidden" name="cropid" value="${crop.cropid}">
					<button type="submit" name="action" value="detailCropBtn">자세히 보기</button>
				</form>
			</c:forEach>
		</c:if>
	</div>
	
	<div>
		<c:if test="${empty searchCropList && empty levelList && not empty selectCategoryList}">
			<c:forEach var="crop" items="${selectCategoryList}">
				<h5>${crop.crop_title}</h5>
				<p>${crop.category_name}</p>
				<p>${crop.difficulty_level}</p>
				<p>${crop.period_text}</p>
				<p>${crop.water_cycle}</p>
				<p>${crop.sunlight_hours}</p>
				
				<form action="${pageContext.request.contextPath}/detailcrop.do" method="get">
					<input type="hidden" name="cropid" value="${crop.cropid}">
					<button type="submit" name="action" value="detailCropBtn">자세히 보기</button>
				</form>
			</c:forEach>
		</c:if>
	</div>
	
	<div>
		<c:if test="${empty searchCropList && empty selectCategoryList && empty levelList}">
			<c:forEach var="crop" items="${allCropList}">
				<h5>${crop.crop_title}</h5>
				<p>${crop.category_name}</p>
				<p>${crop.difficulty_level}</p>
				<p>${crop.period_text}</p>
				<p>${crop.water_cycle}</p>
				<p>${crop.sunlight_hours}</p>
				
				<form action="${pageContext.request.contextPath}/detailcrop.do" method="get">
					<input type="hidden" name="cropid" value="${crop.cropid}">
					<button type="submit" name="action" value="detailCropBtn">자세히 보기</button>
				</form>
			</c:forEach>
		</c:if>
	</div>
	
	
	<%@ include file="/JSP/footer.jsp"%>
</body>
</html>