<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

    <div class="mypage-container">
        
        <div class="sidebar">
            <div class="profile-card">
                <img src="${pageContext.request.contextPath}/images/profile_default.png" alt="프로필" class="profile-img">
                
                <div class="user-name">${loginUser.username} 님</div>
                <span class="user-level">Lv.${loginUser.level} 새싹 농부</span>
                
                <div class="level-progress-container">
                    <div class="level-progress-bar" style="width: 60%;"></div>
                </div>
                <p class="level-info-text">다음 레벨까지 일지 3개 남음</p>

                <div class="stat-grid">
                    <div class="stat-item">
                        <strong>${myCropList.size()}</strong>
                        <span>재배 중</span>
                    </div>
                    <div class="stat-item">
                        <strong>12</strong> <span>방문</span>
                    </div>
                    <div class="stat-item">
                        <strong>28</strong>
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
            
            <div class="section-title">🌱 내 텃밭 현황</div>
            <div class="crop-list">
                
                <c:if test="${empty myCropList}">
                    <div class="crop-card" style="justify-content:center;">
                        <p>현재 키우고 있는 작물이 없어요. 작물을 등록해보세요!</p>
                    </div>
                </c:if>

                <c:forEach var="crop" items="${myCropList}">
                    <div class="crop-card">
                        <img src="${pageContext.request.contextPath}/images/crops/${crop.cropCode}.png" alt="작물" class="crop-img">
                        
                        <div class="crop-info">
                            <div class="crop-header">
                                <span class="crop-name">${crop.cropNickname} (${crop.cropName})</span>
                                <span class="d-day-badge">수확 D-${crop.dDay}</span>
                            </div>
                            
                            <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:5px;">
                                <span>성장률</span>
                                <span>${crop.growthRate}%</span>
                            </div>
                            <div class="growth-container">
                                <div class="growth-bar" style="width: ${crop.growthRate}%;"></div>
                            </div>
                            <div style="font-size: 0.85rem; color: #666; margin-top: 5px;">
                                파종일: ${crop.plantingDate}
                            </div>
                        </div>
                        

                        <form action="cropDetail.do" method="get">
                            <input type="hidden" name="cropId" value="${crop.cropId}">
                            <button type="submit" class="manage-btn">관리 ></button>
                        </form>
                    </div>
                </c:forEach>
            </div>

            <div class="bottom-grid">
                
                <div class="dashboard-card">
                    <div class="section-title">🗓️ 11월 출석부</div>
                    <div class="calendar-grid">
                        <div>일</div><div>월</div><div>화</div><div>수</div><div>목</div><div>금</div><div>토</div>
                        <div class="cal-day"></div><div class="cal-day"></div><div class="cal-day"></div> <div class="cal-day">1</div>
                        <div class="cal-day visited">2 ☘️</div> <div class="cal-day">3</div>
                        <div class="cal-day">4</div>
                        <div class="cal-day visited">5 ☘️</div>
                        <div class="cal-day">6</div>
                        <div class="cal-day">7</div>
                    </div>
                    <p style="text-align: center; margin-top: 15px; font-size: 0.9rem; color: #4a7c59;">
                        이번 달은 텃밭에 <strong>2번</strong> 방문했어요!
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
    <%@ include file="footer.jsp" %>
</body>
</html>