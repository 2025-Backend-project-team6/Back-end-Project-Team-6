<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>커뮤니티 | Garden.ing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/community.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="container">
        <main class="main-content">
            <h1 class="page-title">커뮤니티</h1>
            <p class="page-subtitle">농사 이야기를 나눠보세요</p>
            <button class="write-btn">+ 글쓰기</button>

            <div class="search-filter-area">
                <div class="search-box">
                    <input type="text" placeholder="검색어를 입력하세요">
                    <button>🔍</button>
                </div>
                <div class="tabs">
                    <button class="tab active">전체</button>
                    <button class="tab">질문</button>
                    <button class="tab">답변</button>
                    <button class="tab">수확 인증</button>
                </div>
            </div>

            <section class="post-list">
                <article class="post">
                    <div class="post-header">
                        <span class="user-level">Level 2</span>
                        <span class="user-name">김농부</span>
                        <span class="post-date">3시간 전</span>
                    </div>
                    <h2 class="post-title">토마토 첫 수확 성공했어요! 🥰</h2>
                    <p class="post-content">
                        드디어 토마토를 수확했습니다! 병이 몇 번 왔지만, 차츰 키우는 재미에 성공해버렸네요.
                    </p>
                    <div class="post-images">
                    	<img src="${pageContext.request.contextPath}/images/post_tomato_1.jfif" 
             			alt="수확한 토마토 사진" class="post-img">
                    </div>
                    <div class="post-tags">
                        <span class="tag">#토마토</span>
                        <span class="tag">#첫수확</span>
                    </div>
                    <div class="post-meta">
                        <span class="likes">👍 129</span>
                        <span class="comments">💬 24</span>
                        <span class="views">👁️ 15</span>
                    </div>
                </article>

                <article class="post">
                    <div class="post-header">
                        <span class="user-level">Level 2</span>
                        <span class="user-name">이세연</span>
                        <span class="post-date">6시간 전</span>
                    </div>
                    <h2 class="post-title">상추가 시들어요 😭😭😭 원인 좀 알려주세요</h2>
                    <p class="post-content">
                        상추를 키운 지 2주 정도 됐는데 얼마 전부터 잎 끝이 탔습니다. 물은 매일 주고 있는데 왜 이럴까요?
                    </p>
                    <div class="post-images">
                        <img src="${pageContext.request.contextPath}/images/post_2.jfif" 
             			alt="시든 상추 사진" class="post-img">        		
                    </div>
                    <div class="post-tags">
                        <span class="tag">#상추</span>
                        <span class="tag">#잎마름병</span>
                        <span class="tag">#초보농사</span>
                    </div>
                    <div class="post-meta">
                        <span class="likes">👍 45</span>
                        <span class="comments">💬 10</span>
                        <span class="views">👁️ 8</span>
                    </div>
                </article>
                
                <div class="more-btn-area">
                    <button class="load-more-btn">더 보기</button>
                </div>
            </section>
        </main>

        <aside class="sidebar">
            <section class="ranking-widget">
                <h3>🔥 인기 노력 🔥</h3>
                <ul>
                    <li><span class="rank-title">🌱 경작의 재발견</span><span class="rank-count">125</span></li>
                    <li><span class="rank-title">🍀 행복한 씨앗</span><span class="rank-count">98</span></li>
                    <li><span class="rank-title">🌾 수확 기술</span><span class="rank-count">67</span></li>
                    <li><span class="rank-title">💧 수분 관리</span><span class="rank-count">76</span></li>
                </ul>
            </section>

            <hr>

            <section class="weekly-best-widget">
                <h3>9일 전 한 주 베스트 ✨</h3>
                <div class="best-post">
                    <p class="best-post-content">토마토 잎이 노랗게 변하는 이유</p>
                    <div class="best-post-meta">
                        <span>👍 512</span>
                        <span>💬 89</span>
                    </div>
                </div>
                <div class="best-post">
                    <p class="best-post-content">초보도 쉽게 키우는 작물 추천</p>
                    <div class="best-post-meta">
                        <span>👍 507</span>
                        <span>💬 84</span>
                    </div>
                </div>
            </section>

            <div class="cta-banner">
                <p>✍️ 내 글을 작성해보세요!</p>
                <p class="sub-text">농사 경험을 공유해주세요</p>
            </div>
        </aside>
    </div>
	<%@ include file="footer.jsp" %>
    </body>
</html>