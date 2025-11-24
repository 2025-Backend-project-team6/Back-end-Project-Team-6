<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 유저 정보가 없으면 목록으로 튕겨내기 -->
<c:if test="${empty userDetail}">
    <c:redirect url="${pageContext.request.contextPath}/admin/admin_User_List.jsp" />
</c:if>

<!-- 상태에 따른 텍스트/색상 변수 설정 (c:set 사용) -->
<c:set var="isActive" value="${userDetail.user_status == 'ACTIVE'}" />
<c:set var="statusText" value="${isActive ? '✔️ 활성 계정' : '🚫 정지 계정'}" />
<c:set var="statusColor" value="${isActive ? '#e8f5e9; color:#388E3C;' : '#ffebee; color:#c62828;'}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 상세 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_User_Detail.css">
</head>
<body>

    <div class="detail-wrapper">
        
        <aside class="detail-sidebar">
            <div class="profile-header">
                <a href="${pageContext.request.contextPath}/admin/user.do" style="text-decoration: none;">
                    <p style="text-align: left; font-size: 12px; color: #666;">⬅️ 돌아가기</p>
                </a>
                <h4 style="margin: 0;">회원 상세 정보</h4>
                <!-- EL로 데이터 출력 -->
                <p style="font-size: 12px; color: #999;">사용자 ID: ${userDetail.userid}</p>
            </div>
            
            <form action="${pageContext.request.contextPath}/admin/user.do" method="post">
                <input type="hidden" name="command" value="update">
                <input type="hidden" name="userId" value="${userDetail.userid}">

                <div class="profile-main">
                    <div class="profile-icon">👤</div>
                    <div class="profile-name">
                        <input type="text" name="username" value="${userDetail.username}" class="edit-input" style="text-align: center; font-weight: bold;">
                    </div>
                    <div class="profile-level">
                        Level <input type="number" name="level" value="${userDetail.level}" class="edit-input" style="width: 50px;">
                    </div>
                    <div style="margin-top: 5px;">
                      
                        <select name="user_status" class="edit-input">
                            <option value="ACTIVE" ${userDetail.user_status == 'ACTIVE' ? 'selected' : ''}>활성</option>
                            <option value="SUSPENDED" ${userDetail.user_status == 'SUSPENDED' ? 'selected' : ''}>정지</option>
                        </select>
                    </div>
                </div>

                <div class="user-bio">
                    도시 텃밭에서 토마토와 상추를 키우고 있습니다.<br>
                    초보 농부의 일상을 기록합니다.
                </div>

                <ul class="user-info-list">
                    <li class="info-item">📧<span class="detail-label">이메일:</span>
                        <input type="email" name="email" value="${userDetail.email}" class="edit-input">
                    </li>
                    <li class="info-item">🔒<span class="detail-label">비번:</span>
                        <input type="password" name="password" value="${userDetail.password}" class="edit-input">
                    </li>
                    <li class="info-item">🔑<span class="detail-label">권한:</span>
                        <select name="role" class="edit-input">
                            <option value="USER" ${userDetail.role == 'USER' ? 'selected' : ''}>일반회원</option>
                            <option value="ADMIN" ${userDetail.role == 'ADMIN' ? 'selected' : ''}>관리자</option>
                        </select>
                    </li>
                    
                    <li class="info-item">📍<span class="detail-label">주소:</span><span>서울 구로구</span></li>
                    <li class="info-item">📅<span class="detail-label">가입일:</span><span>2024.03.15</span></li>
                </ul>

                <div class="admin-menu-section">
                    <h4>회원 관리</h4>
                    
                    <div class="action-form">
                        <button type="submit" class="menu-item-btn btn-success">회원 정보 수정 저장</button>
                    </div>
            </form> 

            <form action="${pageContext.request.contextPath}/admin/user.do" method="post" class="action-form">
                <input type="hidden" name="command" value="suspend">
                <input type="hidden" name="userId" value="${userDetail.userid}">
                
                <c:choose>
                    <c:when test="${userDetail.user_status == 'ACTIVE'}">
                        <!-- 활성 상태일 때: 정지 버튼 표시 -->
                        <input type="hidden" name="status" value="SUSPENDED">
                        <button type="submit" class="menu-item-btn btn-danger">🚫 계정 정지</button>
                    </c:when>
                    <c:otherwise>
                        <!-- 정지 상태일 때: 해제 버튼 표시 -->
                        <input type="hidden" name="status" value="ACTIVE">
                        <button type="submit" class="menu-item-btn btn-success">✅ 정지 해제</button>
                    </c:otherwise>
                </c:choose>
            </form>

            <form action="${pageContext.request.contextPath}/admin/user.do" method="post" class="action-form">
                <input type="hidden" name="command" value="delete">
                <input type="hidden" name="userId" value="${userDetail.userid}">
                <button type="submit" class="menu-item-btn btn-danger">🗑️ 회원 탈퇴 처리</button>
            </form>

            </div> 
        </aside>

        <main class="detail-content">

            <div class="activity-stats">
                <h4 style="font-size: 16px; color: #666; margin-bottom: 15px;">📊 활동 통계</h4>
                <div class="stats-grid">
                    <div class="stat-item"><div class="stat-icon" style="color:#2ecc71;">📍</div><div class="stat-value">3</div><div class="stat-label">텃밭</div></div>
                    <div class="stat-item"><div class="stat-icon" style="color:#3498db;">🌱</div><div class="stat-value">12</div><div class="stat-label">작물</div></div>
                    <div class="stat-item"><div class="stat-icon" style="color:#e67e22;">📃</div><div class="stat-value">15</div><div class="stat-label">게시글</div></div>
                    <div class="stat-item"><div class="stat-icon" style="color:#9b59b6;">💬</div><div class="stat-value">48</div><div class="stat-label">댓글</div></div>
                    <div class="stat-item"><div class="stat-icon" style="color:#e74c3c;">❤️</div><div class="stat-value">124</div><div class="stat-label">좋아요</div></div>
                </div>
            </div>

            <div class="garden-list-card">
                <h4 style="font-size: 16px; color: #666; margin-bottom: 15px;">🏡 텃밭 목록</h4>
                <div class="garden-item">
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <div class="garden-name">우리집 베란다 텃밭</div>
                        <span class="crop-count-tag" style="background-color: #c8e6c9; color: #33691e;">4개 작물</span>
                    </div>
                    <div class="garden-details-grid">
                        <div class="detail-item"><span class="detail-label">위치:</span>서울특별시 구로구</div>
                        <div class="detail-item"><span class="detail-label">크기:</span>1.5평</div>
                        <div class="detail-item"><span class="detail-label">생성일:</span>2024.03.20</div>
                    </div>
                </div>
            </div>
        </main>
    </div>

</body>
</html>