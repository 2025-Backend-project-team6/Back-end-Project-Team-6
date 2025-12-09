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
                <h4>오늘의 날씨</h4>
                <c:choose>
        			<c:when test="${not empty weatherDataList}">
            			
            			<c:set var="current_weather" value="${weatherDataList[0]}" />

            			<div class="location">
                			${locationName}
            			</div>
            
			            <%-- 기온 표시 --%>
			            <div class="temp">
			                ${current_weather.temperature}°
			            </div>
			            
			            <%-- 하늘 상태 표시 --%>
			            <div class="condition">
			            <c:choose>
                                <c:when test="${current_weather.skyStatus == '1'}">맑음</c:when>
                                <c:when test="${current_weather.skyStatus == '3'}">구름 많음</c:when>
                                <c:when test="${current_weather.skyStatus == '4'}">흐림</c:when>
                                <c:otherwise>날씨 코드(${current_weather.skyStatus})</c:otherwise>
                            </c:choose>
			            </div>
			            
			            <div class="weather-details">
			            	<%-- 습도 표시 --%>
        					<span>습도 ${current_weather.humidity}%</span>
        					<%-- 강수 형태 표시 --%>
    						<span>강수 형태: 
                                <c:choose>
                                    <c:when test="${current_weather.precipitationType == '1'}">비</c:when>
                                    <c:when test="${current_weather.precipitationType == '2'}">비/눈</c:when>
                                    <c:when test="${current_weather.precipitationType == '3'}">눈</c:when>
                                    <c:when test="${current_weather.precipitationType == '4'}">소나기</c:when>
                                    <c:otherwise>없음</c:otherwise>
                                </c:choose>
                            </span>
        				</div>
			          </c:when>
			        	<c:otherwise>
				            <div class="location">날씨 정보 없음</div>
				            <div class="temp">--°</div>
				            <div class="condition">정보를 불러올 수 없습니다.</div>
				            <div class="weather-details">
				                <span>API 호출 오류</span>
				            </div>
        				</c:otherwise>
        		</c:choose>
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
</body>
</html>