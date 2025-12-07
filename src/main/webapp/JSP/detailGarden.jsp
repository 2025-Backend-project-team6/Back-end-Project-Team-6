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

	<div class="detail-container">
		<div class="garden-info">
			<h3 class="garden-title">${garden.gardenname}🌱</h3>
			<p class="garden-location">📍${garden.location} · 📅${garden.start_date}</p>
		</div>

		<div class="top-action-buttons">
			<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
				<input type="hidden" name="gardenid" value="${garden.gardenid}">
    			<button class="btn btn-green" type="submit" name="action" value="allWater">전체 물주기</button>
			</form>
			<form action="${pageContext.request.contextPath}/mycrop.do" method="get">
				<button class="btn btn-light" type="submit" name="action" value="addCropPageBtn">작물 추가</button>
			</form>
		</div>

		<h5 class="section-title">재배중인 작물 (${garden.crop_count})</h5>
		<div class="crop-list">
			<c:forEach var="crop" items="${gardenCropList}">
				<div class="crop-card">
					<p class="crop-nickname">${crop.nickname}</p>
					<p class="crop-title">${crop.crop_title}</p>
					<p class="crop-date">심은 날짜: ${crop.planted_date}</p>
					<p class="crop-last-water">
						💧 마지막 물주기  
						<c:choose>
							<c:when test="${not empty crop.last_watered_at}">
								${crop.last_watered_at}
							</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
					</p>

					<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get" class="crop-btn-area">
						<input type="hidden" name="id" value="${crop.id}">
						<input type="hidden" name="gardenid" value="${crop.gardenid}">

						<button class="btn btn-green" type="submit" name="action" value="water">
						    <c:choose>
						        <c:when test="${crop.last_watered_at != null 
						                       and crop.last_watered_at.toString() == today}">
						            물주기 완료
						        </c:when>
						        <c:otherwise>물주기</c:otherwise>
						    </c:choose>
						</button>
						<button class="btn btn-gray" type="submit" name="action" value="deleteCrop">작물 삭제</button>
					</form>
				</div>
			</c:forEach>
		</div>

		<div class="calendar-container">
		    <div class="calendar-top">
			    <h3 class="section-title">텃밭 관리 캘린더 📅</h3>
			    <button class="btn btn-light" type="button" id="openActivityModal">활동 기록 추가</button>
			</div>

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
	</div>

	<div id="activityModal" class="modal-overlay" style="display:none;">
	    <div class="modal-box">
	        <div class="modal-header">
	            <h2>활동 기록 추가</h2>
	            <span id="closeModal" class="close-btn">&times;</span>
	        </div>

	        <form action="${pageContext.request.contextPath}/gardenmanage.do" method="post">
	        	<input type="hidden" name="gardenid" value="${garden.gardenid}">

	            <label>활동 유형</label>
	            <select name="activity_type" required class="modal-input">
	                <option value="">활동 유형 선택</option>
	                <option value="water">물주기</option>
	                <option value="fertilizer">비료주기</option>
	                <option value="harvest">수확</option>
	                <option value="manage">관리 활동</option>
	            </select>

	            <label>작물 선택</label>
	            <select name="cropid" required class="modal-input">
	                <option value="">작물 선택</option>
	                <c:forEach var="crop" items="${gardenCropList}">
	                    <option value="${crop.id}">${crop.nickname}</option>
	                </c:forEach>
	            </select>

	            <label>날짜</label>
	            <input type="date" name="activity_date" class="modal-input"/>

	            <label>설명</label>
	            <textarea name="memo" class="modal-textarea" placeholder="내용을 입력하세요"></textarea>

	            <div class="modal-actions">
	                <button type="button" id="cancelModal" class="btn btn-gray">취소</button>
	                <button type="submit" name="action" value="saveActivity" class="btn btn-green">저장</button>
	            </div>
	        </form>
	    </div>
	</div>

	<%@ include file="/JSP/footer.jsp" %>


<!-- JS -->
<script>
	document.addEventListener("DOMContentLoaded", function () {

	    const modal = document.getElementById("activityModal");
	    const openBtn = document.getElementById("openActivityModal");
	    const closeBtn = document.getElementById("closeModal");
	    const cancelBtn = document.getElementById("cancelModal");

	    openBtn.addEventListener("click", () => modal.style.display = "flex");
	    closeBtn.addEventListener("click", () => modal.style.display = "none");
	    cancelBtn.addEventListener("click", () => modal.style.display = "none");

	    modal.addEventListener("click", e => {
	        if (e.target === modal) modal.style.display = "none";
	    });
	});
</script>

</body>
</html>
