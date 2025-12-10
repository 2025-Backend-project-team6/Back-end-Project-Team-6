<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GardenLog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/index.css">
</head>
<body>

	<%@ include file="header.jsp" %>

    <div class="index-container">
        <aside class="sidebar">
            <div class="profile-card card-box">
                <img src="${pageContext.request.contextPath}/images/farmer.png" alt="프로필 사진" class="profile-img">
                
                <div class="username">${loginUser.username} 님</div>

                <c:choose>
                    <c:when test="${loginUser.level == 1}">
                        <span class="user-level level-badge level1">Lv.1 새싹 농부</span>
                    </c:when>
                    <c:when test="${loginUser.level == 2}">
                        <span class="user-level level-badge level2">Lv.2 초보 농부</span>
                    </c:when>
                    <c:otherwise>
                        <span class="user-level level-badge level-other">Lv.${loginUser.level} 숙련 농부</span>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="weather-card card-box">
                <h4 class="card-title">날씨 정보 ☀️</h4>
                <div class="weather-location">서울특별시 구로구</div>
                <div class="weather-temp">18°</div>
                <div class="weather-condition">맑음</div>
                <div class="weather-detail-box">
                    <span class="weather-detail">습도 65%</span>
                    <span class="weather-detail">풍속 2.5m/s</span>
                </div>
            </div>

            <div class="popular-card card-box">
                <h4 class="card-title">오늘의 인기글</h4>
                <ol class="popular-list">
                    <li><a href="#">토마토 키우기 성공 노하우 공유합...</a><span>조회 324</span></li>
                    <li><a href="#">병충해 관리 어떻게 하시나요?</a><span>조회 287</span></li>
                    <li><a href="#">초보 농부 질문있어요</a><span>조회 251</span></li>
                    <li><a href="#">상추가 너무 잘 자라요 ㅎㅎ</a><span>조회 198</span></li>
                    <li><a href="#">텃밭 가꾸기 1년차 후기</a><span>조회 176</span></li>
                </ol>
            </div>
        </aside>

        <main class="main-content">
            <section class="my-garden-section card-box">
                <div class="section-header">
                    <h3 class="section-title">나의 텃밭 🌱</h3>

                    <form action="${pageContext.request.contextPath}/index.do" method="get">
                        <select class="garden-select" name="selectedGarden" onchange="this.form.submit()">
                            <c:forEach var="garden" items="${userGardenList}">
                                <option value="${garden.gardenname}"
                                    <c:if test="${not empty selectedGarden and selectedGarden eq garden.gardenname}">selected</c:if>>
                                    ${garden.gardenname}
                                </option>
                            </c:forEach>
                        </select>
                    </form>
                </div>

                <p class="garden-count">총 ${currentGarden.crop_count}개의 작물을 재배중입니다</p>

                <div class="crop-status-row">
                    <c:forEach var="crop" items="${gardenCropList}">
                        <div class="crop-chip">${crop.nickname}</div>
                    </c:forEach>
                </div>

                <div class="garden-plot">
                    <div class="plant-icon plant1">🍅</div>
                    <div class="plant-icon plant2">🥬</div>
                    <div class="plant-icon plant3">🍆</div>
                    <div class="plant-icon plant4">🥕</div>
                    <div class="plant-icon plant5">🥒</div>
                </div>
                
	            <div class="summary-row">
	                <div class="summary-box water-box">
	                    <div class="summary-title">이번 주 물주기</div>
	                    <div class="summary-value">${totalWater}회</div>
	                </div>
	
	                <div class="summary-box crop-box">
	                    <div class="summary-title">총 재배 중</div>
	                    <div class="summary-value">${totalCrops}개</div>
	                </div>
	
	                <div class="summary-box days-box">
	                    <div class="summary-title">재배 경력</div>
	                    <div class="summary-value">${passedDays}일</div>
	                </div>
	            </div>
	            
			    <h3 class="crop-view-title">작물 보기</h3>
			    <div class="crop-card-grid">
					<c:forEach var="crop" items="${gardenCropList}">
							<div class="crop-card-body">
								<p class="crop-name">${crop.nickname}</p>
		                    	<p class="crop-date">
			                        <span class="date-icon">📅심은 날짜: </span>
			                        ${crop.planted_date}
		                    	</p>
							</div>
					</c:forEach>
				</div>
            </section>
        </main>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
