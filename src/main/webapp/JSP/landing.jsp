<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GardenLog - 나만의 작은 정원 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/landing.css">
</head>
<body>

    <header class="header">
        <div class="logo">
            <span>🌱</span> GardenLog
        </div>
        <div class="header-btns">
            <button class="btn-login" onclick="location.href='userLoginForm.jsp'">로그인</button>
        </div>
    </header>

    <section class="hero-section">
        <div class="container">
            <span class="hero-badge">✨ 스마트한 정원 관리의 시작</span>
            <div class="hero-title">
                <h1>나만의 작은 정원을<br>체계적으로 관리하세요</h1>
            </div>
            <p class="hero-subtitle">
                GardenLog와 함께라면 초보 농부도 전문가처럼!<br>
                작물 재배부터 일지 관리, 커뮤니티 활동까지 한 곳에서
            </p>
            <div class="hero-btns">
                <button class="btn-main-start" onclick="location.href='userJoinForm.jsp'">지금 시작하기 ></button>
                <button class="btn-main-login" onclick="location.href='userLoginForm.jsp'">로그인하기</button>
            </div>
            <div class="hero-icons">
                <span>🍅</span>
                <span>🥬</span>
                <span>🥒</span>
                <span>🍓</span>
                <span>🌶️</span>
            </div>
        </div>
    </section>

    <section class="stats-bar">
        <div class="stats-container">
            <div class="stat-item">
                <h3>👨‍🌾 1,248+</h3>
                <p>활동 중인 농부</p>
            </div>
            <div class="stat-item">
                <h3>🌱 8,934</h3>
                <p>재배 중인 작물</p>
            </div>
            <div class="stat-item">
                <h3>💬 3,421</h3>
                <p>공유된 노하우</p>
            </div>
        </div>
    </section>

    <section class="features-section">
        <div class="container">
            <div class="section-title">
                <h2>GardenLog의 주요 기능</h2>
                <p>텃밭 가꾸기가 더 쉽고 즐거워지는 똑똑한 도구들</p>
            </div>
            
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">🌱</div>
                    <h4>텃밭 관리</h4>
                    <p>나만의 텃밭을 등록하고 작물 현황을 한눈에 확인하세요</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">📅</div>
                    <h4>농사 일지</h4>
                    <p>매일의 농사 기록을 캘린더로 관리하고 성장을 추적하세요</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">📖</div>
                    <h4>작물 백과사전</h4>
                    <p>작물별 재배법, 물주기, 병해충 정보를 손쉽게 검색하세요</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">👥</div>
                    <h4>커뮤니티</h4>
                    <p>다른 농부들과 노하우를 공유하고 질문을 나눠보세요</p>
                </div>
            </div>
        </div>
    </section>

    <section class="preview-section">
        <div class="preview-container">
            <div class="preview-text">
                <span style="color:#558b2f; font-weight:bold;">스마트 관리</span>
                <h3>작물 성장일<br>데이터로 추적 하세요</h3>
                
                <ul class="preview-list">
                    <li>
                        <span class="check-icon">✔</span>
                        <div>
                            <strong>적절한 파종 알림</strong><br>
                            물주기, 비료주기 시기를 놓치지 않도록 알려드려요
                        </div>
                    </li>
                    <li>
                        <span class="check-icon">✔</span>
                        <div>
                            <strong>실시간 날씨 정보</strong><br>
                            지역별 날씨를 확인하고 농사 계획을 세워보세요
                        </div>
                    </li>
                    <li>
                        <span class="check-icon">✔</span>
                        <div>
                            <strong>성장 기록 관리</strong><br>
                            사진과 메모로 작물의 성장 과정을 기록하세요
                        </div>
                    </li>
                </ul>
            </div>

            <div class="preview-image-box">
                <div class="mock-card">
                    <div class="mock-icon">💧</div>
                    <div class="mock-info">
                        <h5>토마토에 물 주기</h5>
                        <span>2일 후</span>
                    </div>
                    <span class="mock-badge" style="background:#e3f2fd; color:#1976d2;">예정</span>
                </div>
                <div class="mock-card">
                    <div class="mock-icon">☀️</div>
                    <div class="mock-info">
                        <h5>오늘의 날씨</h5>
                        <span>맑음 20°C</span>
                    </div>
                    <span class="mock-badge" style="background:#fff3e0; color:#f57c00;">좋음</span>
                </div>
                <div class="mock-card">
                    <div class="mock-icon">🥬</div>
                    <div class="mock-info">
                        <h5>상추 수확 완료</h5>
                        <span>오늘</span>
                    </div>
                    <span class="mock-badge" style="background:#e8f5e9; color:#388e3c;">완료</span>
                </div>
            </div>
        </div>
    </section>

    <section class="reviews-section">
        <div class="container">
            <div class="section-title">
                <h2>농부들의 생생한 후기</h2>
                <p>GardenLog와 함께하는 1,200명 이상의 농부들</p>
            </div>
            <div class="reviews-grid">
                <div class="review-card">
                    <div class="reviewer-info">
                        <div class="reviewer-img">👩‍🌾</div> <div>
                            <div class="reviewer-name">김농부</div>
                            <span class="reviewer-level">Level 5 베테랑 농부</span>
                        </div>
                    </div>
                    <p class="review-text">"작물 관리가 이렇게 쉬워질 줄 몰랐어요! 물주기 알림 덕분에 토마토가 쑥쑥 자라고 있어요 🍅"</p>
                    <div class="stars">♥♥♥♥♥</div>
                </div>
                <div class="review-card">
                    <div class="reviewer-info">
                        <div class="reviewer-img">👨‍🌾</div>
                        <div>
                            <div class="reviewer-name">박정원</div>
                            <span class="reviewer-level">Level 2 초보 농부</span>
                        </div>
                    </div>
                    <p class="review-text">"커뮤니티에서 배운 팁 덕분에 상추 수확량이 2배로 늘었어요. 정말 유용한 서비스입니다."</p>
                    <div class="stars">♥♥♥♥♥</div>
                </div>
                <div class="review-card">
                    <div class="reviewer-info">
                        <div class="reviewer-img">🧑‍🌾</div>
                        <div>
                            <div class="reviewer-name">최새싹</div>
                            <span class="reviewer-level">Level 1 새싹 농부</span>
                        </div>
                    </div>
                    <p class="review-text">"초보자인데도 식물 백과사전 덕분에 쉽게 시작할 수 있었어요. 감사합니다! 🌿"</p>
                    <div class="stars">♥♥♥♥♥</div>
                </div>
            </div>
        </div>
    </section>

    <section class="cta-section">
        <div class="container">
            <h2>지금 바로 시작하세요! 🌱</h2>
            <p>무료로 가입하고 나만의 정원을 가꿔보세요</p>
            <button class="btn-cta-white" onclick="location.href='register.jsp'">🚀 무료로 시작하기</button>
            <p style="font-size:12px; margin-top:15px; opacity:0.7;">> 회원가입은 1분이면 끝나요 · 신용카드 필요 없음</p>
        </div>
    </section>

    <footer class="footer">
        <div class="footer-logo">Garden Log</div>
        <p>모두를 위한 스마트 텃밭 관리 서비스</p>
        <p>&copy; 2025 GardenLog. All rights reserved.</p>
    </footer>

</body>
</html>