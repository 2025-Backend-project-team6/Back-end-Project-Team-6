<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header class="full-width-wrapper">   
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/header.css">
	<div class="header">
		<a href="${pageContext.request.contextPath}/JSP/index.jsp">
	    	<div class="logo">
	    	<span>🌱</span> GardenLog
	    	</div>
		</a>
    	<nav class="nav-menu">
    		<a href="#" id="findFarmLink">내 주변 텃밭찾기</a>
        	<a href="${pageContext.request.contextPath}/gardenmanage.do">텃밭 관리</a>
        	<a href="${pageContext.request.contextPath}/mycrop.do">작물 관리</a>
        	<a href="${pageContext.request.contextPath}/journal.do">농사 일지</a>
        	<a href="${pageContext.request.contextPath}//cropsearch.do">작물 검색</a>
        	<a href="${pageContext.request.contextPath}/JSP/community.jsp">커뮤니티</a>
        	<a href="${pageContext.request.contextPath}/mypage.do">마이페이지</a>
    	</nav>
    	
    	<form action="${pageContext.request.contextPath}/login.do" method="get">
    		<button class="logout-btn" name="loginStatus" value="logoutBtn">로그아웃</button>
    	</form>
    </div>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const findFarmLink = document.getElementById('findFarmLink');
    const contextPath = '${pageContext.request.contextPath}';

    findFarmLink.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 링크 이동 방지
        
        // 💡 3. 브라우저의 Geolocation API를 사용하여 현재 위치를 요청
        if (navigator.geolocation) {
            
            // 현재 위치를 성공적으로 가져왔을 때 실행될 함수
            navigator.geolocation.getCurrentPosition(function(position) {
                const lat = position.coords.latitude;
                const lng = position.coords.longitude;
                
                // 💡 4. 위도/경도를 파라미터로 붙여서 서블릿(LocalSearchServlet) 호출
                const requestUrl = contextPath + '/api/farms?lat=' + lat + '&lng=' + lng;
                console.log("Farm Search Request URL:", requestUrl);
                
                // 해당 URL로 페이지 이동
                window.location.href = requestUrl;

            }, function(error) {
                // 위치 가져오기 실패 시 실행될 함수
                console.error("위치 정보 가져오기 실패. 코드:", error.code);
                
                let errorMessage = "위치 정보 권한이 거부되었습니다. 설정에서 허용해 주세요.";
                
                if (error.code === error.POSITION_UNAVAILABLE) {
                    errorMessage = "위치 정보를 사용할 수 없습니다. 잠시 후 다시 시도해 주세요.";
                } else if (error.code === error.TIMEOUT) {
                    errorMessage = "위치 정보 요청 시간이 초과되었습니다.";
                } 
                
                // 💡 사용자에게 대체 검색 옵션 제공
                if (confirm(errorMessage + "\n\n현재 위치 대신 서울 중심(기본값)으로 검색하시겠습니까?")) {
                    const defaultLat = 37.5665; // 서울 시청 위도
                    const defaultLng = 126.9780; // 서울 시청 경도
                    // 기본값 URL 생성 및 페이지 이동
                    const defaultRequestUrl = contextPath + '/searchFarm?lat=' + defaultLat + '&lng=' + defaultLng;
                    window.location.href = defaultRequestUrl;
                }
            });
        } else {
            // 브라우저가 Geolocation API를 지원하지 않을 때
            alert("사용 중인 브라우저는 위치 정보(Geolocation)를 지원하지 않습니다.");
        }
    });
});
	</script>

</header>

