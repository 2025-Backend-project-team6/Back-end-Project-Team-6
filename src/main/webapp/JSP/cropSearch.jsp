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
	
	<div class="main-content">
	
		<div class="page-title-box">
			<h2 class="page-title">작물 백과사전 📖</h2>
			<p class="page-subtitle">재배법, 수확 관리 정보를 확인하세요</p>
		</div>
	
		<div class="search-filter-box search-filter-line">
			<form class="search-bar" action="${pageContext.request.contextPath}/cropsearch.do" method="get">
				<input type="search" name="keyword" class="search-input"
					   value="${keyword}" placeholder="작물명 검색">
				<button type="submit" name="action" value="cropSearchBtn" class="search-btn">🔍</button>
			</form>
			
			<div class="select-row">
				<form action="${pageContext.request.contextPath}/cropsearch.do" method="get">
					<input type="hidden" name="action" value="category">
					<select name="categorySelect" class="filter-select" onchange="this.form.submit()">
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
					<select name="levelSelect" class="filter-select" onchange="this.form.submit()">
						<option value="">전체 난이도</option>
						<option value="초급" <c:if test="${selectedLevel == '초급'}">selected</c:if>>초급</option>
						<option value="중급" <c:if test="${selectedLevel == '중급'}">selected</c:if>>중급</option>
						<option value="상급" <c:if test="${selectedLevel == '상급'}">selected</c:if>>상급</option>
					</select>
				</form>
			</div>
		</div>
	
		<div class="empty-msg">
			<c:if test="${not empty SearchCropnullMessage}">
				<p>${SearchCropnullMessage}</p>
			</c:if>
		</div>
	
		<div class="crop-grid">
			<c:if test="${not empty searchCropList}">
				<c:forEach var="crop" items="${searchCropList}">
					<div class="crop-card">
						<h4 class="crop-name">${crop.crop_title}</h4>
						<div class="crop-tags">
							<span class="tag tag-category">${crop.category_name}</span>
							<span class="tag tag-level">${crop.difficulty_level}</span>
						</div>
						<div class="crop-info-row">
							<span>📅 ${crop.period_text}</span>
							<span>💧 ${crop.water_cycle}</span>
							<span>☀️ ${crop.sunlight_hours}</span>
						</div>
					</div>
				</c:forEach>
			</c:if>
	
			<c:if test="${empty searchCropList && not empty levelList}">
				<c:forEach var="crop" items="${levelList}">
					<div class="crop-card">
						<h4 class="crop-name">${crop.crop_title}</h4>
						<div class="crop-tags">
							<span class="tag tag-category">${crop.category_name}</span>
							<span class="tag tag-level">${crop.difficulty_level}</span>
						</div>
						<div class="crop-info-row">
							<span>📅 ${crop.period_text}</span>
							<span>💧 ${crop.water_cycle}</span>
							<span>☀️ ${crop.sunlight_hours}</span>
						</div>
					</div>
				</c:forEach>
			</c:if>
	
			<c:if test="${empty searchCropList && empty levelList && not empty selectCategoryList}">
				<c:forEach var="crop" items="${selectCategoryList}">
					<div class="crop-card">
						<h4 class="crop-name">${crop.crop_title}</h4>
						<div class="crop-tags">
							<span class="tag tag-category">${crop.category_name}</span>
							<span class="tag tag-level">${crop.difficulty_level}</span>
						</div>
						<div class="crop-info-row">
							<span>📅 ${crop.period_text}</span>
							<span>💧 ${crop.water_cycle}</span>
							<span>☀️ ${crop.sunlight_hours}</span>
						</div>
					</div>
				</c:forEach>
			</c:if>
	
			<c:if test="${empty searchCropList && empty selectCategoryList && empty levelList}">
				<c:forEach var="crop" items="${allCropList}">
					<div class="crop-card">
						<h4 class="crop-name">${crop.crop_title}</h4>
						<div class="crop-tags">
							<span class="tag tag-category">${crop.category_name}</span>
							<span class="tag tag-level">${crop.difficulty_level}</span>
						</div>
						<div class="crop-info-row">
							<span>📅 ${crop.period_text}</span>
							<span>💧 ${crop.water_cycle}</span>
							<span>☀️ ${crop.sunlight_hours}</span>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<%@ include file="/JSP/footer.jsp"%>
</body>
</html>
