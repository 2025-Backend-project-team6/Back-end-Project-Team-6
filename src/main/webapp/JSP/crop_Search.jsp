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
                <option>카테고리</option>
                <option value="논농사">논농사</option>
        		<option value="밭농사">밭농사</option>
        		<option value="버섯">버섯</option>
        		<option value="약초">약초</option>
        		<option value="채소">채소</option>
        		<option value="과수">과수</option>
        		<option value="화훼">화훼</option>
            </select>
            <select class="filter-select">
                <option>난이도</option>
                <option value="초급">초급</option>
        		<option value="중급">중급</option>
        		<option value="상급">상급</option>
            </select>
        </section>

        <main class="crop-list">
   			<c:forEach var="crop" items="${requestScope.cropList}">
            
            <div class="crop-card">
                
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
                
               <button class="detail-btn" onclick="location.href='${pageContext.request.contextPath}/crop_detail.do?cropId=${crop.cropid}'">자세히 보기</button>
            </div>
        
        </c:forEach>
         </main>
    </div>
</body>
</html>