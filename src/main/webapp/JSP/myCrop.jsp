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

	<div class="page-container">
		<div class="top-row">
			<div class="page-title">
				<h2>작물 관리🥕</h2>
				<p>총 ${totalCropCount}개의 작물을 관리중입니다</p>
			</div>

			<div class="action-buttons">
				<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
					<button type="submit" name="action" value="addCropPageBtn" class="btn-add">
						+ 새 작물 추가
					</button>
				</form>
			</div>
		</div>

		<form action="${pageContext.request.contextPath}/mycrop.do" method="get" class="search-form">
			<div class="search-area">
				<input type="search" name="keyword"
					   value="${keyword}"
					   class="input-search-wide"
					   placeholder="나의 작물 검색">
				<button type="submit" name="action" value="searchCropBtn" class="btn-search-wide">🔍</button>
			</div>

			<div class="category-row">
				<button name="category" value="allCrop" class="category-btn">전체</button>

				<c:forEach var="category" items="${cropCategoryList}">
					<button name="category" value="${category.crop_nm}" class="category-btn">
						${category.crop_nm}
					</button>
				</c:forEach>
			</div>
		</form>

		<c:if test="${not empty searchNullMessage}">
			<p class="warning">${searchNullMessage}</p>
		</c:if>

		<div class="crop-grid">
			<c:if test="${not empty searchMyCropList}">
				<c:forEach var="crop" items="${searchMyCropList}">
					<div class="crop-card">
						<div class="title-row">
							<h3>${crop.nickname}</h3>
							<p class="category-badge">${crop.category}</p>
						</div>
						<div class="divider"></div>
						
						<p>🌾텃밭: ${crop.gardenname}</p>
						<p>📅 심은 날짜: ${crop.planted_date}</p>
						
						<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
							<input type="hidden" name="gardenid" value="${crop.gardenid}">	
							<input type="hidden" name="id" value="${crop.id}">
							<button type="submit" name="action" value="deleteCropBtn" class="sub-btn">작물 삭제</button>
						</form>
					</div>
				</c:forEach>
			</c:if>

			<c:if test="${empty searchMyCropList}">
				<c:forEach var="crop" items="${findByCategoryList}">
					<div class="crop-card">
						<div class="title-row">
							<h3>${crop.nickname}</h3>
							<p class="category-badge">${crop.category}</p>
						</div>
						<div class="divider"></div>
						
						<p>🌾텃밭: ${crop.gardenname}</p>
						<p>📅 심은 날짜: ${crop.planted_date}</p>

						<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
							<input type="hidden" name="gardenid" value="${crop.gardenid}">	
							<input type="hidden" name="id" value="${crop.id}">
							<button type="submit" name="action" value="deleteCropBtn" class="sub-btn">작물 삭제</button>
						</form>
					</div>
				</c:forEach>
			</c:if>

			<c:if test="${not empty allMyCropList}">
				<c:forEach var="crop" items="${allMyCropList}">
					<div class="crop-card">
						<div class="title-row">
							<h3>${crop.nickname}</h3>
							<p class="category-badge">${crop.category}</p>
						</div>
						<div class="divider"></div>
						
						<p>🌾텃밭: ${crop.gardenname}</p>
						<p>📅 심은 날짜: ${crop.planted_date}</p>

						<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
							<input type="hidden" name="gardenid" value="${crop.gardenid}">	
							<input type="hidden" name="id" value="${crop.id}">
							<button type="submit" name="action" value="deleteCropBtn" class="sub-btn">작물 삭제</button>
						</form>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>

	<%@ include file="/JSP/footer.jsp"%>
</body>
</html>
