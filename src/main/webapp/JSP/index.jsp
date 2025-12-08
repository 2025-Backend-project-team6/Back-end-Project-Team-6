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

<div class="container">

<aside class="sidebar-left">

<div class="card profile-card">
<img src="${pageContext.request.contextPath}/images/farmer.png" alt="프로필 사진">
<div class="username">${loginUser.username} 님</div>
<c:choose>
<c:when test="${loginUser.level == 1}">
<span class="user-level">Lv.1 새싹 농부</span>
</c:when>
<c:when test="${loginUser.level == 2}">
<span class="user-level">Lv.2 초보 농부</span>
</c:when>
<c:otherwise>
<span class="user-level">Lv.${loginUser.level} 숙련 농부</span>
</c:otherwise>
</c:choose>
</div>

<div class="card weather-card">
<h4>날씨 정보 ☀️</h4>

<%-- ⚠️ JSTL 초기 로직은 비동기 통신 방식과 맞지 않으므로 주석 처리합니다. --%>
<%-- 날씨 정보는 JS가 API 응답을 받아 비동기로 채웁니다. --%>
<div class="location">
    날씨 정보 로딩 중...
</div>

<div class="temp">
    --°
</div>

<div class="condition">
    정보를 가져오는 중...
</div>

<div class="weather-details">
    <span>잠시만 기다려 주세요.</span>
</div>

</div>

<div class="card popular-posts-card">
<h4>오늘의 인기글</h4>
<ol>
<li>
<a href="#">토마토 키우기 성공 노하우 공유합...</a>
<span>조회 324</span>
</li>
<li>
<a href="#">병충해 관리 어떻게 하시나요?</a>
<span>조회 287</span>
</li>
<li>
<a href="#">초보 농부 질문있어요</a>
<span>조회 251</span>
</li>
<li>
<a href="#">상추가 너무 잘 자라요 ㅎㅎ</a>
<span>조회 198</span>
</li>
<li>
<a href="#">텃밭 가꾸기 1년차 후기</a>
<span>조회 176</span>
</li>
</ol>
</div>

</aside>

<main class="main-content">

<section class="card my-garden-card">
<h3>
나의 텃밭 🌱
<button class="harvest-btn">상추 예상 수확일 D-15</button>
</h3>
<p>총 5개의 작물을 재배중입니다</p>

<div class="alert-box">
<span>💧</span>
<div>
<strong>오늘은 물을 줘야해요!</strong>
<p>상추, 토마토에 물을 주세요</p>
</div>
</div>

<div class="garden-plot">
<div class="plant-icon" style="left: 15%; top: 40%;">🍅</div>
<div class="plant-icon" style="left: 30%; top: 60%;">🥬</div>
<div class="plant-icon" style="left: 50%; top: 45%;">🍆</div>
<div class="plant-icon" style="left: 70%; top: 70%;">🥕</div>
<div class="plant-icon" style="left: 85%; top: 30%;">🥒</div>
</div>

<div class="crop-status-bar">
<div class="crop-status-item">
<span>🍅</span>
<div>토마토</div>
<div class="d-day">D-45</div>
</div>
<div class="crop-status-item">
<span>🥬</span>
<div>상추</div>
<div class="d-day">D-15</div>
</div>
<div class="crop-status-item">
<span>🥒</span>
<div>오이</div>
<div class="d-day">D-30</div>
</div>
<div class="crop-status-item">
<span>🥕</span>
<div>당근</div>
<div class="d-day">D-60</div>
</div>
<div class="crop-status-item">
<span>🍆</span>
<div>가지</div>
<div class="d-day">D-50</div>
</div>
</div>
</section>

<section class="crop-log-section">
<h3>작물 별 일지 보러가기</h3>

<div class="crop-log-grid">
<div class="log-card">
<img src="${pageContext.request.contextPath}/images/tomato.png" alt="토마토">
<div class="log-card-content">
<h4>🍅 토마토</h4>
<p>📅 2025-03-15</p>
<p>🌱 성장 중</p>
<button class="view-log-btn">일지 보기</button>
</div>
</div>
<div class="log-card">
<img src="${pageContext.request.contextPath}/images/lettuce.png" alt="상추">
<div class="log-card-content">
<h4>🥬 상추</h4>
<p>📅 2025-04-01</p>
<p>🌱 성장 중</p>
<button class="view-log-btn">일지 보기</button>
</div>
</div>
<div class="log-card">
<img src="${pageContext.request.contextPath}/images/Cucumber.png" alt="오이">
<div class="log-card-content">
<h4>🥒 오이</h4>
<p>📅 2025-03-20</p>
<p>🌱 성장 중</p>
<button class="view-log-btn">일지 보기</button>
</div>
</div>
</div>
</section>

</main>

</div>

<%@ include file="footer.jsp" %>

<script>

// 1. 함수 정의 (함수가 다른 코드보다 먼저 인식되어야 합니다.)
function requestWeatherUpdate(lat, lon) {

console.log("DEBUG(Client): Attempting fetch to /weather.do with lat:", lat, "lon:", lon);

const weatherUrl = "${pageContext.request.contextPath}/weather.do?lat=" + lat + "&lon=" + lon;


fetch(weatherUrl)
.then(response => {
if (response.ok) {
return response.json();
} else {
throw new Error('Servlet 응답 오류: ' + response.status);
}
})
.then(data => {
// 서버에서 받은 JSON 데이터로 화면 업데이트
updateWeatherCard(data);
})
.catch(error => {
console.error("날씨 정보 비동기 호출 실패:", error);
// 오류 발생 시 오류 메시지 표시
document.querySelector('.location').innerText = "정보 획득 실패";
document.querySelector('.temp').innerText = "--°";
document.querySelector('.condition').innerText = "통신 오류";
document.querySelector('.weather-details').innerHTML = "<span>잠시 후 다시 시도해주세요.</span>";
});
}


// 2. 🌤️ 날씨 카드 업데이트 함수
function updateWeatherCard(data) {
    const weatherCard = document.querySelector('.weather-card');

    if (!data.weatherList || data.weatherList.length === 0) {
        // 데이터가 없는 경우 처리
        weatherCard.querySelector('.location').innerText = data.locationName || '위치 정보'; // ⚠️ 서버 데이터 사용
        weatherCard.querySelector('.temp').innerText = "--°";
        weatherCard.querySelector('.condition').innerText = "날씨 정보 없음";
        weatherCard.querySelector('.weather-details').innerHTML = "<span>예보 데이터를 찾을 수 없습니다.</span>";
        return;
    }

    const current = data.weatherList[0];


    // DAO에서 변환된 skyStatus 사용
    let skyStatusText = current.skyStatus;
    
    // 강수 형태
    let ptyText = current.precipitationType;

    // DOM 요소에 데이터 삽입
    weatherCard.querySelector('.location').innerText = data.locationName; // 💡 수정 부분: 서버에서 온 locationName 사용
    weatherCard.querySelector('.temp').innerText = `${current.temperature}°`;
    weatherCard.querySelector('.condition').innerText = skyStatusText;


    weatherCard.querySelector('.weather-details').innerHTML = `
        <span>습도 ${current.humidity || '--'}%</span>
        <span>강수 형태: ${ptyText || '없음'}</span>
    `;


    // 카드 아이콘 업데이트
    const icon = (skyStatusText.includes('맑음')) ? '☀️' :
        (skyStatusText.includes('구름')) ? '🌤️' :
        (skyStatusText.includes('흐림')) ? '☁️' : '❓';
    weatherCard.querySelector('h4').innerHTML = `날씨 정보 ${icon}`;
}


// 3. 이벤트 리스너: 페이지 로드 후 위치 요청 시작
document.addEventListener('DOMContentLoaded', function() {

console.log("테스트 모드: Geolocation을 건너뛰고 기본 위치로 요청합니다.");

// 동양미래대 좌표 (37.4939, 126.8530)로 즉시 호출
requestWeatherUpdate(37.4939, 126.8530);
});
</script>
</body>

</html>