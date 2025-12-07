<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 텃밭 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/detailGarden.css">
</head>
<body>
	<%@ include file="/JSP/header.jsp" %>
	
	<h3>${garden.gardenname}🌱</h3>
	<p>📍${garden.location} · 📅${garden.start_date}
	
	<h5>재배중인 작물 (${garden.crop_count})</h5>
	<div>
		<c:forEach var="crop" items="${gardenCropList}">
			<p>${crop.nickname}</p>
			<p>${crop.crop_title}</p>
			<p>${crop.planted_date}</p>
			<p>
				💧마지막 물주기
				<c:choose>
					<c:when test="${not empty crop.last_watered_at}">	${crop.last_watered_at}</c:when>
					<c:otherwise>	-</c:otherwise>	
				</c:choose>
			</p>

			<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
				<input type="hidden" name="id" value="${crop.id}">
				<input type="hidden" name="gardenid" value="${crop.gardenid}">
				<button type="submit" name="action" value="water">
					<c:choose>
						<c:when test="${waterStatus[crop.id]}">물주기 완료</c:when>
						<c:otherwise>물주기</c:otherwise>
					</c:choose>
				</button>
				<button type="submit" name="action" value="deleteCrop">작물 삭제</button>
			</form>
		</c:forEach>
	</div>
	
	<div>
		<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
			<button type="submit" name="action" value="allWater">전체 물주기</button>
			<button type="submit" name="action" value="activity">활동 기록 추가</button>
		</form>
		<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
			<button type="submit" name="action" value="addCropPageBtn">작물 추가</button>
		</form>
	</div>
	
	<div class="calendar-container">
	    <h3>텃밭 관리 캘린더 📅</h3>
	
	    <div class="calendar-header">
	        <span>${year}년 ${month}월</span>
	    </div>
	
	    <div class="calendar-grid">
	        <c:forEach var="day" items="${days}">
	            <div class="calendar-day">
	                <span class="day-number">${day}</span>
	
	                <c:if test="${not empty calendarMap[day]}">
	                    <c:forEach var="activity" items="${calendarMap[day]}">
	                        <span class="dot ${activity.activity_type}"></span>
	                    </c:forEach>
	                </c:if>
	            </div>
	        </c:forEach>
	    </div>
	
	    <div class="legend">
	        <span class="dot water"></span> 물주기
	        <span class="dot fertilizer"></span> 비료주기
	        <span class="dot harvest"></span> 수확
	        <span class="dot manage"></span> 관리 활동
	    </div>
	</div>

	<%@ include file="/JSP/footer.jsp" %>
</body>
</html>