<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GardenLog Admin - 관리자 대시보드</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_Main.css">
    <!-- Chart.js CDN (통계 차트용) -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    

    <jsp:include page="admin_Header.jsp" />

    <div class="container main-content">
        
        <!-- 대시보드 탭 내용 -->
        <div class="tab-content active" style="display: block;">
            
            <!-- 통계 카드 -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon green-bg">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                                <circle cx="9" cy="7" r="4"/>
                                <path d="M22 21v-2a4 4 0 0 0-3-3.87"/>
                                <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                            </svg>
                        </div>
                        <svg class="trend-icon green" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
                            <polyline points="17 6 23 6 23 12"/>
                        </svg>
                    </div>
                    <p class="stat-label">총 사용자</p>
                    <p class="stat-value">1,248</p>
                    <p class="stat-change green">+23 오늘</p>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon blue-bg">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                                <circle cx="9" cy="7" r="4"/>
                                <polyline points="16 11 18 13 22 9"/>
                            </svg>
                        </div>
                        <svg class="trend-icon blue" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
                            <polyline points="17 6 23 6 23 12"/>
                        </svg>
                    </div>
                    <p class="stat-label">활성 사용자</p>
                    <p class="stat-value">856</p>
                    <p class="stat-change gray">68.6% 활성률</p>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon purple-bg">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                            </svg>
                        </div>
                        <svg class="trend-icon purple" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
                            <polyline points="17 6 23 6 23 12"/>
                        </svg>
                    </div>
                    <p class="stat-label">총 게시글</p>
                    <p class="stat-value">3,421</p>
                    <p class="stat-change gray">커뮤니티 활동</p>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <div class="stat-icon orange-bg">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M7 20h10"/>
                                <path d="M10 20c5.5-2.5.8-6.4 3-10"/>
                                <path d="M9.5 9.4c1.1.8 1.8 2.2 2.3 3.7-2 .4-3.5.4-4.8-.3-1.2-.6-2.3-1.9-3-4.2 2.8-.5 4.4 0 5.5.8z"/>
                                <path d="M14.1 6a7 7 0 0 0-1.1 4c1.9-.1 3.3-.6 4.3-1.4 1-1 1.6-2.3 1.7-4.6-2.7.1-4 1-4.9 2z"/>
                            </svg>
                        </div>
                        <svg class="trend-icon orange" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
                            <polyline points="17 6 23 6 23 12"/>
                        </svg>
                    </div>
                    <p class="stat-label">재배중인 작물</p>
                    <p class="stat-value">8,934</p>
                    <p class="stat-change gray">전체 작물</p>
                </div>
            </div>

            <!-- 차트 영역 -->
            <div class="charts-grid">
                <div class="chart-card">
                    <div class="chart-header">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#466D1D" stroke-width="2">
                            <line x1="18" y1="20" x2="18" y2="10"/>
                            <line x1="12" y1="20" x2="12" y2="4"/>
                            <line x1="6" y1="20" x2="6" y2="14"/>
                        </svg>
                        <h3>사용자 증가 추이</h3>
                    </div>
                    <canvas id="userGrowthChart"></canvas>
                </div>

                <div class="chart-card">
                    <div class="chart-header">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#466D1D" stroke-width="2">
                            <path d="M7 20h10"/>
                            <path d="M10 20c5.5-2.5.8-6.4 3-10"/>
                        </svg>
                        <h3>작물 분포</h3>
                    </div>
                    <canvas id="cropDistributionChart"></canvas>
                </div>
            </div>
            
        </div> <!-- tab-content 닫기 -->
        
    </div> <!-- container 닫기  -->

    <!-- Chart.js 스크립트 -->
    <script>
        // 사용자 증가 추이 차트
        const userCtx = document.getElementById('userGrowthChart');
        if (userCtx) {
            new Chart(userCtx, {
                type: 'line',
                data: {
                    labels: ['6월', '7월', '8월', '9월', '10월', '11월'],
                    datasets: [{
                        label: '사용자 수',
                        data: [320, 485, 678, 892, 1056, 1248],
                        borderColor: '#466D1D',
                        backgroundColor: 'rgba(70, 109, 29, 0.1)',
                        borderWidth: 3,
                        tension: 0.4,
                        pointRadius: 5,
                        pointBackgroundColor: '#466D1D',
                        pointBorderColor: '#fff',
                        pointBorderWidth: 2,
                        pointHoverRadius: 7,
                        fill: true // 배경색 채우기 옵션
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false, // 크기 자동 조절을 위해 false
                    plugins: {
                        legend: { display: false }
                    },
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        }

        // 작물 분포 차트
        const cropCtx = document.getElementById('cropDistributionChart');
        if (cropCtx) {
            new Chart(cropCtx, {
                type: 'pie',
                data: {
                    labels: ['토마토', '상추', '오이', '가지', '기타'],
                    datasets: [{
                        data: [2340, 1890, 1456, 1234, 2014],
                        backgroundColor: ['#FF6B6B', '#51CF66', '#4DABF7', '#845EF7', '#FAB005'],
                        borderWidth: 2,
                        borderColor: '#fff'
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false, // 크기 자동 조절을 위해 false
                    plugins: {
                        legend: { position: 'bottom' }
                    }
                }
            });
        }
    </script>
</body>
</html>