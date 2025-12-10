<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GardenLog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/index.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=3fy9khavdx&submodules=geocoder"></script>
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
                
                <div class="weather-icon-box">
                    <img id="w-icon" src="" alt="날씨 아이콘" style="display:none;">
                </div>

                <div class="weather-location" id="w-loc">위치 찾는 중...</div>
                <div class="weather-temp" id="w-temp">--°</div>
                <div class="weather-condition" id="w-desc">정보 로딩 중</div>
                
                <div class="weather-detail-box">
                    <span class="weather-detail" id="w-humid">습도 --%</span>
                    <span class="weather-detail" id="w-wind">풍속 --m/s</span>
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

    <script>
        $(document).ready(function() {
            const WEATHER_API_KEY = "db3eed2dbd0118448496db40a470092f";

            function onGeoOk(position) {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;

                
                const weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + WEATHER_API_KEY + "&units=metric&lang=kr";
                
                fetch(weatherUrl)
                    .then(res => res.json())
                    .then(data => {
                        $("#w-temp").text(Math.round(data.main.temp) + "°");
                        $("#w-desc").text(data.weather[0].description);
                        $("#w-humid").text("습도 " + data.main.humidity + "%");
                        $("#w-wind").text("풍속 " + data.wind.speed + "m/s");
                        
                        const iconCode = data.weather[0].icon;
                        const iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                        $("#w-icon").attr("src", iconUrl).show();
                    });

                
                naver.maps.Service.reverseGeocode({
                    coords: new naver.maps.LatLng(lat, lon),
                }, function(status, response) {
                    if (status === naver.maps.Service.Status.OK) {
                        const result = response.v2.results[0];
                        const si = result.region.area1.name;
                        const gu = result.region.area2.name;
                        $("#w-loc").text(si + " " + gu);
                    }
                });
            }

            function onGeoError() {
                onGeoOk({ coords: { latitude: 37.5665, longitude: 126.9780 } });
            }

            navigator.geolocation.getCurrentPosition(onGeoOk, onGeoError);
        });
    </script>
</body>
</html>