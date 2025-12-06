<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="func" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 작물 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/addCrop.css">
</head>

<body>
	<%@ include file="/JSP/header.jsp" %>

	<div class="page-container">
		<div class="title-wrapper">
			<h2 class="page-title">작물 추가 🌱</h2>
			<p class="page-desc">나의 텃밭에 새로운 작물을 추가합니다.</p>
		</div>

		<form action="${pageContext.request.contextPath}/mycrop.do" method="post" class="crop-form">
			<div class="section-box">
				<h3 class="section-title">텃밭 선택</h3>

				<select class="input-select" name="selectedGarden">
					<c:forEach var="crop" items="${userGardenList}">
						<option value="${crop.gardenname}"
							<c:if test="${not empty selectedGarden and selectedGarden eq crop.gardenname}">selected</c:if>>
							${crop.gardenname}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="section-box">
				<h3 class="section-title">작물 선택</h3>

				<div class="search-row">
					<input type="search" class="input-search" name="keyword" value="${keyword}" placeholder="작물 검색">
					<button type="submit" name="action" value="cropSearchBtn" class="btn-search">검색</button>
				</div>

				<c:if test="${not empty keywordNullMessage}">
					<p class="warning">${keywordNullMessage}</p>
				</c:if>

				<c:if test="${not empty searchCropList}">
					<div class="crop-grid">
						<c:forEach var="crop" items="${searchCropList}">
							<button type="submit"
							        formaction="${pageContext.request.contextPath}/mycrop.do" 
									formmethod="get"
									class="crop-card ${selectedCrop eq crop.crop_title ? 'crop-card-active' : ''}"
									name="selectedCrop" value="${crop.crop_title}">
								🌱 ${crop.crop_title}
							</button>
						</c:forEach>
					</div>
				</c:if>

				<c:if test="${empty searchCropList}">
					<p class="recommend-title">추천 작물</p>
					<div class="crop-grid">
						<c:forEach var="crop" items="${recommendedMap}">
							<button type="submit"
									formaction="${pageContext.request.contextPath}/mycrop.do"
									formmethod="get"
									class="crop-card ${selectedCrop eq crop.key ? 'crop-card-active' : ''}"
									name="selectedCrop" value="${crop.key}">
								${crop.value}
							</button>
						</c:forEach>
					</div>
				</c:if>
				<input type="hidden" name="selectedCrop" value="${selectedCrop}">
			</div>

			<div class="section-box">
				<h3 class="section-title">작물 정보</h3>

				<label class="input-label">작물 이름 *</label>
				<input type="text" class="input-text" name="nickname" placeholder="예: 방울토마토">

				<label class="input-label">심은 날짜 *</label>
				<input type="date" class="input-text" name="planted_date">
			</div>
	
			<div class="btn-row">
				<button type="submit" name="action" value="cancel" class="btn-cancel">취소</button>
				<button type="submit" name="action" value="addCrop" class="btn-submit">작물 추가하기</button>
			</div>
		</form>
				
		<c:if test="${not empty nullMessage}">
			<p class="warning2">${nullMessage}</p>
		</c:if>
	</div>
	
	<%@ include file="/JSP/footer.jsp" %>
</body>
</html>
