<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <a href="${pageContext.request.contextPath}/admin/user.do" class="back-to-list-btn" title="목록으로 돌아가기">
                    <span class="back-arrow"><</span> 목록으로 돌아가기
                </a>
                
                <h4 style="margin: 0; margin-top: 15px;">회원 상세 정보</h4>
                <p class="user-id-text">사용자 ID: ${userDetail.userid}</p>
            </div>
            
            <div class="profile-main">
                <div class="profile-icon">👤</div>
                
                <div class="profile-layout-grid">
                    <div class="profile-summary">
                        <div class="profile-name-text">
                            ${userDetail.username}
                        </div>
                        
                        <div class="profile-level">
                            Level <span style="color: #27ae60; font-weight: bold;">${userDetail.level}</span>
                        </div>
                        
                        <div style="margin-top: 10px;">
                            <span class="badge ${isActive ? 'status-active' : 'status-suspended'}">
                                ${statusText}
                            </span>
                        </div>
                    </div>

                    <ul class="user-info-detail-list">
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
                    </ul>
                </div>
            </div>

            <div class="user-bio">
                도시 텃밭에서 토마토와 상추를 키우고 있습니다.<br>
                초보 농부의 일상을 기록합니다.
            </div>

            <ul class="user-info-list">
                <li class="info-item">📍 <span class="detail-label">주소:</span><span>서울 구로구</span></li>
                <li class="info-item">📅 <span class="detail-label">가입일:</span><span>2024.03.15</span></li>
            </ul>

            <div class="admin-menu-section">
                <h4>회원 관리</h4>
                
                <div class="action-form">
                    <a href="${pageContext.request.contextPath}/admin/user.do?command=edit&userId=${userDetail.userid}" 
                       class="menu-item-btn btn-primary" 
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
            </main>
    </div>

</body>
</html>