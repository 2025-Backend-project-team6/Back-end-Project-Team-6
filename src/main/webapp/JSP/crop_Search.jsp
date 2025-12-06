<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
   			<c:forEach var="crop" items="${requestScope.cropList}">
            
            <div class="crop-card">
                <div class="crop-image-container">
                    </div>
                
                <h3>${crop.crop_title}</h3>
                
                <div class="tags">
                    <span class="tag tag-season">${crop.category_name}</span>
                    <span class="tag tag-level">${crop.difficulty_level}</span>
                </div>
                
                <ul class="info-list">
   					<li><span class="icon">📅</span> ${crop.period_text}</li>
    				<li><span class="icon">💧</span> ${crop.water_cycle}</li>
    				<li><span class="icon">☀️</span> ${crop.sunlight_hours}</li>
				</ul>
                
                <button class="detail-btn" onclick="location.href='crop-detail.do?cropId=${crop.cropid}'">자세히 보기</button>
            </div>
        
        </c:forEach>
         </main>
    </div>
</body>
</html>