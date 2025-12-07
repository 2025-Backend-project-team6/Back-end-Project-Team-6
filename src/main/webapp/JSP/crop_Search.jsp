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
                <option value="" disabled selected>카테고리</option>
                <option value="논농사">논농사</option>
        		<option value="밭농사">밭농사</option>
        		<option value="버섯">버섯</option>
        		<option value="약초">약초</option>
        		<option value="채소">채소</option>
        		<option value="과수">과수</option>
        		<option value="화훼">화훼</option>
            </select>
            <select class="filter-select">
                <option value="" disabled selected>난이도</option>
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
<script>
        document.addEventListener('DOMContentLoaded', function() {
            // 1. 필요한 HTML 요소들을 가져옵니다.
            const selectFilters = document.querySelectorAll('.filter-select');
            const categorySelect = selectFilters[0]; 
            const difficultySelect = selectFilters[1]; 
            const cropCards = document.querySelectorAll('.crop-card');
            
            // 2. 두 개의 select 박스에 'change' 이벤트 리스너를 추가합니다.
            categorySelect.addEventListener('change', filterCrops);
            difficultySelect.addEventListener('change', filterCrops);

            /**
             * 작물 목록을 필터링하는 메인 함수
             */
            function filterCrops() {
                // 3. 현재 선택된 카테고리와 난이도 값을 가져옵니다.
                //    HTML의 value="" 덕분에 초기값은 ""이 됩니다.
                const selectedCategoryValue = categorySelect.value; 
                const selectedDifficultyValue = difficultySelect.value;
                
                // 4. 모든 작물 카드를 순회하며 필터링 로직을 적용합니다.
                cropCards.forEach(card => {
                    // 5. 현재 카드의 태그 텍스트를 가져옵니다.
                    const cardCategory = card.querySelector('.tag-season').textContent.trim();
                    const cardDifficulty = card.querySelector('.tag-level').textContent.trim();
                    
                    // 6. 필터링 조건을 검사합니다.
                    
                    // 카테고리 일치 조건: value가 "" 이거나 (전체보기) 실제 카테고리와 일치해야 합니다.
                    const isCategoryMatch = (selectedCategoryValue === "" || selectedCategoryValue === cardCategory);
                    
                    // 난이도 일치 조건: value가 "" 이거나 (전체보기) 실제 난이도와 일치해야 합니다.
                    const isDifficultyMatch = (selectedDifficultyValue === "" || selectedDifficultyValue === cardDifficulty);

                    // 7. 두 조건이 모두 참일 때만 카드를 표시합니다. (단일/다중 필터 모두 처리)
                    if (isCategoryMatch && isDifficultyMatch) {
                        card.style.display = 'block'; 
                    } else {
                        card.style.display = 'none'; 
                    }
                });
            }
            
            // 페이지 로드 시 초기 목록을 설정합니다. (selected="" 덕분에 전체 목록이 보입니다.)
            filterCrops();
        });
    </script>
    
</body>
</html>