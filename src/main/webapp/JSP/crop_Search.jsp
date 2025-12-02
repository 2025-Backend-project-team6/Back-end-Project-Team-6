<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GardenLog | 작물 검색</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/crop_Search.css">
</head>
<body>

	<%@ include file="header.jsp" %>
		
    <div class="container">
        <section class="page-header">
            <h2>
                <span class="icon">📜</span> 작물 백과사전
            </h2>
            <p>재배법, 수확 관리 정보를 확인하세요</p>          
        </section>

        <section class="search-filter-area">
            <div class="search-input-group">
                <span class="search-icon">🔍</span>
                <input type="text" placeholder="작물명 검색" class="search-input">
            </div>
            <select class="filter-select">
                <option>전체 카테고리</option>
            </select>
            <select class="filter-select">
                <option>전체 난이도</option>
            </select>
        </section>

        <main class="crop-list">
            <div class="crop-card">
                <div class="crop-image-container">
                    </div>
                <h3>토마토</h3>
                <div class="tags">
                    <span class="tag tag-season">열매</span>
                    <span class="tag tag-level">중급</span>
                </div>
                <ul class="info-list">
                    <li><span class="icon">📅</span> 80-100일</li>
                    <li><span class="icon">💧</span> 2-3일에 1회</li>
                    <li><span class="icon">☀️</span> 하루 6-8시간</li>
                </ul>
                <button class="detail-btn">자세히 보기</button>
            </div>

            <div class="crop-card">
                <div class="crop-image-container">
                    </div>
                <h3>상추</h3>
                <div class="tags">
                    <span class="tag tag-season">잎채소</span>
                    <span class="tag tag-level tag-beginner">초급</span>
                </div>
                <ul class="info-list">
                    <li><span class="icon">📅</span> 30-45일</li>
                    <li><span class="icon">💧</span> 매일 1회</li>
                    <li><span class="icon">☀️</span> 하루 4-6시간</li>
                </ul>
                <button class="detail-btn">자세히 보기</button>
            </div>
            
            </main>
    </div>
</body>
</html>