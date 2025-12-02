<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GardenLog - 농사 일지</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/journalList.css">
</head>
<body>

    <jsp:include page="./header.jsp" /> 

    <div class="journal-wrapper">
        
        <aside class="journal-sidebar">
            
            <div class="sidebar-widget calendar-widget">
                <div class="widget-header">
                    <span>📅 일지 캘린더</span>
                </div>
                <div class="mini-calendar">
                    <div class="calendar-nav">
                        <a href="journal.do?year=${currentMonth == 1 ? currentYear - 1 : currentYear}&month=${currentMonth == 1 ? 12 : currentMonth - 1}" class="nav-arrow">&lt;</a>
                        
                        <span class="current-month">${currentYear}년 ${currentMonth}월</span>
                        
                        <a href="journal.do?year=${currentMonth == 12 ? currentYear + 1 : currentYear}&month=${currentMonth == 12 ? 1 : currentMonth + 1}" class="nav-arrow">&gt;</a>
                    </div>
                    <div class="calendar-weekdays">
                        <span>일</span><span>월</span><span>화</span><span>수</span><span>목</span><span>금</span><span>토</span>
                    </div>
                    <div class="calendar-days">
                        <c:forEach begin="1" end="${startDayOfWeek - 1}">
                            <span class="day empty"></span>
                        </c:forEach>

                        <c:forEach var="day" begin="1" end="${lastDay}">
                            <c:set var="hasLog" value="false" />
                            <c:forEach var="d" items="${logDates}">
                                <c:if test="${d == day}">
                                    <c:set var="hasLog" value="true" />
                                </c:if>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${hasLog}">
                                    <span class="day has-log">${day}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="day">${day}</span>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="sidebar-widget insight-widget">
                <div class="widget-header">
                    <span>📊 이번 달 인사이트</span>
                </div>
                <div class="insight-list">
                    <div class="insight-item">
                        <span class="label">작성한 일지</span>
                        <span class="value">${monthlyTotal}개</span>
                    </div>
                    <div class="insight-item">
                        <span class="label">물주기</span>
                        <span class="value">${waterCount}번</span>
                    </div>
                    <div class="insight-item">
                        <span class="label">수확</span>
                        <span class="value">${harvestCount}번</span>
                    </div>
                </div>
                <div class="insight-footer">
                    <span>🌟 가장 많이 기록한 작물:</span>
                    <strong>${topCrop}</strong>
                </div>
            </div>

            <a href="journalWrite.do" class="new-log-box-btn">
                <div class="plus-circle">+</div>
                <span class="main-text">새 일지 작성</span>
                <span class="sub-text">오늘의 농사 기록하기</span>
            </a>

        </aside>

        <main class="journal-feed">
             <div class="feed-header">
                <div class="title-area">
                    <h2>농사 일지 📝</h2>
                    <p>나의 농사 이야기를 기록해보세요</p>
                </div>
                </div>
             
             <div class="log-list">
                <c:if test="${empty journalList}">
                    <div class="empty-log"><p>작성된 일지가 없습니다.</p></div>
                </c:if>
                <c:forEach var="log" items="${journalList}">
                    <div class="log-card">
                        <div class="card-top">
                            <div class="crop-info">
                                <div class="crop-icon-circle">🌱</div> 
                                <div class="text-info">
                                    <h3 class="log-title">${log.title}</h3>
                                    <span class="log-date">${log.logDate}</span>
                                </div>
                                <span class="weather-badge">${log.weather}</span>
                            </div>
                            <c:if test="${not empty log.cropNickname}">
                                <span class="d-day-badge">#${log.cropNickname}</span>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <p class="log-content">${log.content}</p>
                        </div>
                        <c:if test="${not empty log.logImg}">
                            <div class="photo-grid">
                                <div class="photo-item">
                                    <img src="${pageContext.request.contextPath}/uploads/${log.logImg}">
                                </div>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
             </div>
        </main>
    </div>
    <jsp:include page="./footer.jsp" />
</body>
</html>