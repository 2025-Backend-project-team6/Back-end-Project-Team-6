<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 상세 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/member_detail.css">
</head>
<body>

    <div class="detail-wrapper">
        
        <aside class="detail-sidebar">
            <div class="profile-header">
                <p style="text-align: left; font-size: 12px; color: #666;">⬅️ 돌아가기</p>
                <h4 style="margin: 0;">회원 상세 정보</h4>
                <p style="font-size: 12px; color: #999;">사용자 ID: 1</p>
            </div>
            
            <div class="profile-main">
                <div class="profile-icon">👤</div>
                <div class="profile-name">이세연</div>
                <div class="profile-level">Level 1 - 새싹 농부</div>
                <div class="status-badge" style="background:#e8f5e9; color:#388E3C;">✔️ 활성 계정</div>
            </div>

            <div class="user-bio">
                도시 텃밭에서 토마토와 상추를 키우고 있습니다.<br>
                초보 농부의 일상을 기록합니다.
            </div>

            <ul class="user-info-list">
                <li class="info-item">📧<span class="detail-label">이메일:</span><span>seyeon@example.com</span></li>
                <li class="info-item">📍<span class="detail-label">주소:</span><span>서울특별시 구로구</span></li>
                <li class="info-item">📅<span class="detail-label">가입일:</span><span>2024.03.15</span></li>
                <li class="info-item">⚡<span class="detail-label">최근 접속:</span><span>2024.11.19 14:32</span></li>
            </ul>

            <div class="admin-menu-section">
                <h4>회원 관리</h4>
                <a href="#" class="menu-item-btn btn-success">회원 정보 수정</a>
                <a href="#" class="menu-item-btn btn-warning">⚠️ 경고 발송</a>
                <a href="#" class="menu-item-btn btn-danger">🚫 계정 정지</a>
                <a href="#" class="menu-item-btn btn-danger">🗑️ 회원 탈퇴 처리</a>
            </div>

        </aside>

        <main class="detail-content">
            
            <div class="activity-stats">
                <h4 style="font-size: 16px; color: #666; margin-bottom: 15px;">📊 활동 통계</h4>
                
                <div class="stats-grid">
                    <div class="stat-item">
                        <div class="stat-icon" style="color:#2ecc71;">📍</div>
                        <div class="stat-value">3</div>
                        <div class="stat-label">텃밭</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-icon" style="color:#3498db;">🌱</div>
                        <div class="stat-value">12</div>
                        <div class="stat-label">작물</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-icon" style="color:#e67e22;">📃</div>
                        <div class="stat-value">15</div>
                        <div class="stat-label">게시글</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-icon" style="color:#9b59b6;">💬</div>
                        <div class="stat-value">48</div>
                        <div class="stat-label">댓글</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-icon" style="color:#e74c3c;">❤️</div>
                        <div class="stat-value">124</div>
                        <div class="stat-label">좋아요</div>
                    </div>
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

                <div class="garden-item">
                     <div style="display: flex; justify-content: space-between; align-items: center;">
                        <div class="garden-name">주말농장</div>
                        <span class="crop-count-tag" style="background-color: #c8e6c9; color: #33691e;">6개 작물</span>
                    </div>
                    <div class="garden-details-grid">
                        <div class="detail-item"><span class="detail-label">위치:</span>경기도 화성시</div>
                        <div class="detail-item"><span class="detail-label">크기:</span>30평</div>
                        <div class="detail-item"><span class="detail-label">생성일:</span>2024.04.15</div>
                    </div>
                </div>
                
                <div class="garden-item">
                     <div style="display: flex; justify-content: space-between; align-items: center;">
                        <div class="garden-name">옥상 정원</div>
                        <span class="crop-count-tag" style="background-color: #c8e6c9; color: #33691e;">2개 작물</span>
                    </div>
                    <div class="garden-details-grid">
                        <div class="detail-item"><span class="detail-label">위치:</span>서울특별시 구로구</div>
                        <div class="detail-item"><span class="detail-label">크기:</span>2.0평</div>
                        <div class="detail-item"><span class="detail-label">생성일:</span>2024.06.10</div>
                    </div>
                </div>

            </div>
        </main>
    </div>

</body>
</html>