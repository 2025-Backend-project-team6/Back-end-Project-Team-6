<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GardenLog - 마이페이지</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/mypage.css">
</head>
<body>

    <jsp:include page="./header.jsp" /> 

    <div class="title-row mypage-header page-header-wrapper"> 
        <div class="title-box">
            <h2 class="page-title">마이페이지 🧑‍🌾</h2>
            <p class="page-subtitle">나의 농장 기록과 정보를 한 눈에 확인하세요</p>
        </div>
    </div>
    <div class="mypage-container page-content-wrapper"> 
        
        <div class="sidebar">
            <div class="profile-card">
                <img src="${pageContext.request.contextPath}/images/profile_default.png" alt="프로필" class="profile-img">
                
                <div class="user-name">${loginUser.username} 님</div>
                
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
                
                <div class="level-progress-container">
                    <div class="level-progress-bar" style="width: ${progressPercent}%;"></div>
                </div>
                
                <p class="level-info-text">
                    <c:choose>
                        <c:when test="${loginUser.level >= 3}">
                            최고 레벨입니다! 🎉
                        </c:when>
                        <c:otherwise>
                            다음 레벨까지 일지 <strong>${remainingLogs}개</strong> 남음
                        </c:otherwise>
                    </c:choose>
                </p>

                <div class="stat-grid">
                    <div class="stat-item">
                        <strong>${cropCount}</strong>
                        <span>재배 중</span>
                    </div>
                    <div class="stat-item">
                        <strong>${totalVisitCount}</strong> 
                        <span>방문</span>
                    </div>
                    <div class="stat-item">
                        <strong>${journalCount}</strong>
                        <span>일지</span>
                    </div>
                </div>
            </div>

            <div class="side-menu">
                <form action="${pageContext.request.contextPath}/updateUser.do" method="get">
                    <button type="submit">🔒 개인정보 수정</button>
                </form>
            </div>
        </div>

        <div class="main-content">

            <div class="bottom-grid">
                
                <div class="dashboard-card">
                    <div class="calendar-header">
                        <a href="mypage.do?year=${currentMonth == 1 ? currentYear - 1 : currentYear}&month=${currentMonth == 1 ? 12 : currentMonth - 1}" class="nav-btn"> &lt; </a>
                        
                        <span class="section-title" style="margin:0;">🗓️ ${currentMonth}월 출석부</span>
                        
                        <a href="mypage.do?year=${currentMonth == 12 ? currentYear + 1 : currentYear}&month=${currentMonth == 12 ? 1 : currentMonth + 1}" class="nav-btn"> &gt; </a>
                    </div>
                    
                    <div class="calendar-grid">
                        <div>일</div><div>월</div><div>화</div><div>수</div><div>목</div><div>금</div><div>토</div>
                        
                        <c:forEach begin="1" end="${startDayOfWeek - 1}">
                            <div class="cal-day"></div>
                        </c:forEach>

                        <c:forEach var="day" begin="1" end="${lastDay}">
                            <c:set var="isVisited" value="false" />
                            <c:forEach var="vDay" items="${visitDays}">
                                <c:if test="${vDay == day}">
                                    <c:set var="isVisited" value="true" />
                                </c:if>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${isVisited}">
                                    <div class="cal-day visited">${day} ☘️</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="cal-day">${day}</div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>

                    <p style="text-align: center; margin-top: 15px; font-size: 0.9rem; color: #4a7c59;">
                        ${currentMonth}월은 텃밭에 <strong>${visitCount}번</strong> 방문했어요!
                    </p>
                </div>

                <div class="dashboard-card">
                    <div class="section-title">📹 내 농장 실시간 (CCTV)</div>
                    <div class="cctv-view">
                        <img src="${pageContext.request.contextPath}/images/cctv_placeholder.jpg" 
                             alt="CCTV 실시간 화면" 
                             class="cctv-img-full">
                    </div>
                </div>

            </div> 
        </div> 
    </div> 
    
    <jsp:include page="./footer.jsp" />
</body>
</html>