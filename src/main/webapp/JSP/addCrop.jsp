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
	
	<div clsass="page-title-box">
		<h5>작물추가 🌱</h5>
		<p>새로운 작물을 추가합니다.</p>
	</div>

	<form action="${pageContext.request.contextPath}/mycrop.do" method="post">
		<div class="section-box">
			<h6>텃밭 선택</h6>

			<select name="selectedGarden">
				<c:forEach var="crop" items="${userGardenList}">
					<option value="${crop.gardenname}"
						<c:if test="${not empty selectedGarden and selectedGarden eq crop.gardenname}">selected</c:if>>
						${crop.gardenname}
					</option>
				</c:forEach>
			</select>
		</div>

		<div class="section-box">
			<h6>작물 선택</h6>
			<p>일반적인 작물을 선택하거나 직접 입력할 수 있습니다</p>

			<input type="search" name="keyword" 
				   value="${keyword}" placeholder="작물 검색">
			<button type="submit" name="action" value="cropSearchBtn">검색</button>

			<c:if test="${not empty nullMessage}">
				<p>${nullMessage}</p>
			</c:if>
			
			<c:if test="${not empty keywordNullMessage}">
				<p>${keywordNullMessage}</p>
			</c:if>

			<c:if test="${not empty searchCropList}">
				<div class="crop-button-box">
					<c:forEach var="crop" items="${searchCropList}">
						<button type="submit"  
								formaction="${pageContext.request.contextPath}/mycrop.do" 
								formmethod="get"
								name="selectedCrop" value="${crop.crop_title}">
							🌱 ${crop.crop_title}
						</button>
					</c:forEach>
				</div>
			</c:if>

			<c:if test="${empty searchCropList}">
				<div class="crop-button-box">
					<c:forEach var="crop" items="${recommendedMap}">
						<button type="submit"
								formaction="${pageContext.request.contextPath}/mycrop.do"
								formmethod="get"
								name="selectedCrop" value="${crop.key}">
							${crop.value}
						</button>
					</c:forEach>
				</div>
			</c:if>

			<input type="hidden" name="selectedCrop" value="${selectedCrop}">
		</div>

		<div class="section-box">
			<h6>작물 정보</h6>

			<label>작물 이름 *</label>
			<input type="text" name="nickname" placeholder="예: 방울토마토">

			<label>심은 날짜 *</label>
			<input type="date" name="planted_date">
		</div>

		<div class="btn-wrap">
			<button type="submit" name="action" value="cancel" class="cancel-btn">취소</button>
			<button type="submit" name="action" value="addCrop" class="add-btn">작물 추가하기</button>
		</div>

	</form>

</body>
</html>
