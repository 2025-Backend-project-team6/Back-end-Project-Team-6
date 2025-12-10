<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${empty userDetail}">
    <c:redirect url="${pageContext.request.contextPath}/admin/admin_User_List.jsp" />
</c:if>

<c:set var="isActive" value="${userDetail.user_status == 'ACTIVE'}" />
<c:set var="statusText" value="${isActive ? '활성 계정' : '정지 계정'}" />
<c:set var="statusColor" value="${isActive ? '#388E3C' : '#c62828'}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 상세 정보</title>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_User_Detail.css">
</head>
<body>

    <div class="detail-wrapper">
        
        <aside class="detail-sidebar">
            <div class="profile-header">
                <a href="${pageContext.request.contextPath}/admin/user.do" class="back-to-list-btn-alt" title="목록으로 돌아가기">
                    <span class="back-arrow-alt"><</span> 목록으로 돌아가기
                </a>
                
                <h4 class="detail-title-text">회원 상세 정보</h4>
                <p class="user-id-text">사용자 ID: ${userDetail.userid}</p>
            </div>
            
            <div class="profile-main">
                
                <div class="profile-layout-grid-new">
                    
                    <div class="profile-left-col">
                        <div class="profile-icon">👤</div>
                        
                        <div class="profile-name-text">
                            ${userDetail.username}
                        </div>
                        
                        <div class="profile-level">
                            Level <span style="color: #27ae60; font-weight: bold;">${userDetail.level}</span>
                        </div>
                        
                        <div class="status">
                            <span class="badge ${isActive ? 'status-active' : 'status-suspended'}">
                                ${statusText}
                            </span>
                        </div>
                    </div>

                    <ul class="user-info-detail-list-new">
                        <li class="info-item">
                            📧 <span class="detail-label">이메일:</span>
                            <span class="info-value">${userDetail.email}</span>
                        </li>
                        
                        <li class="info-item">
                            🔑 <span class="detail-label">권한:</span>
                            <span class="info-value">
                                <c:choose>
                                    <c:when test="${userDetail.role == 'ADMIN'}">
                                        <span style="color:red; font-weight:bold;">관리자</span>
                                    </c:when>
                                    <c:otherwise>일반회원</c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                        
                        <li class="info-item">📍 <span class="detail-label">주소:</span><span>${userDetail.location}</span></li>
                        <li class="info-item">📅 <span class="detail-label">가입일:</span>
    						<fmt:parseDate value="${userDetail.created_at}" var="parsedDate" pattern="yyyy-MM-dd'T'HH:mm:ss"/>
    						<span><fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd"/></span>
						</li>
                    </ul>
                </div>
            </div>

            <div class="user-bio-alt">
                도시 텃밭에서 토마토와 상추를 키우고 있습니다.<br>
                초보 농부의 일상을 기록합니다.
            </div>

            <div class="admin-menu-section">
                <h4>회원 관리</h4>
                
                <div class="action-form">
                    <a href="${pageContext.request.contextPath}/admin/user.do?command=edit&userId=${userDetail.userid}" 
                       class="menu-item-btn btn-edit-green" 
                       style="text-decoration: none;">
                       <span style="margin-right: 5px;">✏️</span> 회원 정보 수정 페이지로 이동
                    </a>
                </div>
                    

                <form action="${pageContext.request.contextPath}/admin/user.do" method="post" class="action-form">
                    <input type="hidden" name="command" value="delete">
                    <input type="hidden" name="userId" value="${userDetail.userid}">
                    <button type="submit" class="menu-item-btn btn-delete-alt" onclick="return confirm('정말로 탈퇴시키시겠습니까?');">
                        <span style="margin-right: 5px;">🗑️</span> 강제 탈퇴 처리
                    </button>
                </form>

            </div> 
        </aside>

        <main class="detail-content">
            <div class="activity-stats">
                <h4 style="font-size: 16px; color: #666; margin-bottom: 15px;">📊 활동 통계</h4>
                <div class="stats-grid">
                    <div class="stat-item"><div class="stat-icon" style="color:#2ecc71;">📍</div><div class="stat-value">${empty totalGardenCount ? 0 : totalGardenCount}</div><div class="stat-label">텃밭</div></div>
                    <div class="stat-item"><div class="stat-icon" style="color:#3498db;">🌱</div><div class="stat-value">${empty totalCropCount ? 0 : totalCropCount}</div><div class="stat-label">작물</div></div>
                    <div class="stat-item"><div class="stat-icon" style="color:#e67e22;">📃</div><div class="stat-value">15</div><div class="stat-label">게시글</div></div>
                </div>
            </div>

        <div class="garden-list-card">
        <h4 style="font-size: 16px; color: #666; margin-bottom: 15px;">🏡 텃밭 목록</h4>

        <c:choose>
            <c:when test="${empty userGardenList}">
                <p style="text-align: center; color: #777; padding: 20px;">등록된 텃밭이 없습니다.</p>
            </c:when>
            <c:otherwise>
                <%-- 💡 Step 2: 텃밭 목록(gardenList)을 반복하며 출력 --%>
                <c:forEach var="garden" items="${userGardenList}">
                    
                    <div class="garden-item">
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <%-- 텃밭 이름 출력 --%>
                            <div class="garden-name">${garden.gardenname}</div> 
                            <%-- 작물 개수 출력 (작물 개수 필드가 garden 객체에 있다고 가정) --%>
                            <span class="crop-count-tag">${garden.crop_count}개 작물</span> 
                        </div>
                        <div class="garden-details-grid">
                            <%-- 위치, 크기 출력 --%>
                            <div class="detail-item"><span class="detail-label">위치:</span>${garden.location}</div>
                            <div class="detail-item"><span class="detail-label">크기:</span>${garden.area}평</div>
                        </div>
                    </div>
                    
                </c:forEach>
            </c:otherwise>
        </c:choose>
        
    </div>
        </main>
    </div>

</body>
</html>