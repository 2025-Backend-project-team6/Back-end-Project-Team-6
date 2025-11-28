<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<%-- 💡 Tomcat 10+에서는 JSTL URI를 'jakarta.tags.core'로 사용합니다. --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주변 주말농장 지도</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    
    <script type="text/javascript" 
            src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=YOUR_MAP_CLIENT_ID">
    </script>
</head>
<body>

    <h2>📍 내 주변 주말농장 찾기</h2>

    <div id="farmDataContainer" style="display:none;">
        <c:out value="${farmJson}" escapeXml="false"/>
    </div>
    
    <div id="map"></div> <script>
        // 💡 3. 숨겨진 DIV 요소에서 JSON 데이터를 가져와 JavaScript 객체로 변환
        var farmList = [];
        try {
            var container = document.getElementById('farmDataContainer');
            // DIV의 텍스트 내용을 가져와 공백을 제거하고 JSON 문자열로 사용
            var farmJsonString = container.textContent.trim(); 
            
            if (farmJsonString) {
                farmList = JSON.parse(farmJsonString);
                console.log("파싱된 농장 수:", farmList.length);
            }
        } catch (e) {
            console.error("데이터 파싱 오류:", e);
            // 에러 발생 시 사용자에게 알림
            alert("농장 데이터를 불러오는 중 오류가 발생했습니다. 서버 로그를 확인하세요.");
        }
        
        // 💡 4. 지도 초기화 및 생성 함수
        function initMap() {
            var initialPosition;
            var initialZoom = 12;

            if (farmList.length > 0) {
                // 첫 번째 농장의 TM 좌표를 LatLng로 변환하여 지도의 중심 좌표로 사용
                var firstFarm = farmList[0];
                // parseInt로 mapx, mapy를 정수로 변환해야 합니다.
                var tmPoint = new naver.maps.Point(parseInt(firstFarm.mapx), parseInt(firstFarm.mapy));
                initialPosition = naver.maps.TransCoord.fromTM(tmPoint);
                initialZoom = 14;
            } else {
                // 데이터가 없을 경우 기본 위치 설정 (서울 시청)
                console.log("검색된 농장 데이터가 없습니다.");
                initialPosition = new naver.maps.LatLng(37.5666103, 126.9783882); 
            }

            // 지도 객체 생성
            var map = new naver.maps.Map('map', {
                center: initialPosition, 
                zoom: initialZoom,        
                mapTypeId: naver.maps.MapTypeId.NORMAL
            });

            // 💡 5. 마커 표시
            if (farmList.length > 0) {
                farmList.forEach(function(farm) {
                    addMarker(map, farm);
                });
            }
        }

        // 💡 6. 개별 마커 생성 및 정보창 추가 함수
        function addMarker(map, farm) {
            // TM 좌표를 LatLng 좌표로 변환
            var tmPoint = new naver.maps.Point(parseInt(farm.mapx), parseInt(farm.mapy));
            var position = naver.maps.TransCoord.fromTM(tmPoint);

            var marker = new naver.maps.Marker({
                map: map,
                position: position,
                title: farm.title 
            });

            var infoWindow = new naver.maps.InfoWindow({
                content: '<div class="iw_content"><b>' + farm.title + '</b><p>' + farm.address + '</p></div>'
            });

            naver.maps.Event.addListener(marker, 'click', function(e) {
                if (infoWindow.getMap()) {
                    infoWindow.close(); 
                } else {
                    infoWindow.open(map, marker); 
                }
            });
        }
        
        // 💡 7. HTML 문서 로드 후 지도 초기화 함수 실행
        naver.maps.onJSContentLoaded = initMap;

    </script>
</body>
</html>