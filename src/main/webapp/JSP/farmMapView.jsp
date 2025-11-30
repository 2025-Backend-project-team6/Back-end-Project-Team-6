<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="java.util.List, java.util.ArrayList" %>

<%
    // Servlet에서 전달받은 JSON 문자열을 가져옵니다.
    // [근거] Servlet 코드에서 request.setAttribute("farmDataJson", ...)로 저장했음
    String farmDataJson = (String) request.getAttribute("farmDataJson");
    List<JSONObject> farmList = new ArrayList<>();
    
    // JSON 파싱 (simple-json 라이브러리가 필요합니다)
    if (farmDataJson != null && !farmDataJson.isEmpty()) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(farmDataJson);
            JSONObject resultObject = (JSONObject) jsonObject.get("result"); // result가 있다면
            
            // 만약 result가 없고 바로 items가 있다면 아래처럼 수정:
            JSONArray items = (JSONArray) jsonObject.get("items");
            
            if (items != null) {
                for (Object item : items) {
                    farmList.add((JSONObject) item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리: 파싱 실패 시 빈 목록 유지
        }
    }
    // [주의] 이 코드를 실행하려면 simple-json 라이브러리(JAR 파일)를 프로젝트에 추가해야 합니다.
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주말농장 지도</title>
    <style>
        #map { width: 100%; height: 600px; border: 1px solid #ccc; }
    </style>
    
    <%-- 1. 네이버 지도 API 스크립트 로드 --%>
    <%-- YOUR_CLIENT_ID를 실제 네이버 지도 API용 클라이언트 ID로 변경해야 합니다. --%>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=f05sse6krq"></script>
</head>
<body>

    <h2>📍 내 주변 주말농장 찾기</h2>
    <div id="map"></div>

    <script>
        function initializeMapAndMarkers() {
        	
        	const currentLat = <%= request.getAttribute("currentLat") != null ? request.getAttribute("currentLat") : "37.5666103" %>;
            const currentLng = <%= request.getAttribute("currentLng") != null ? request.getAttribute("currentLng") : "126.9783882" %>;
            
            // 2. 지도 초기화 (Dynamic Map)
            const mapOptions = {
                // 서울 시청 중심 좌표
                center: new naver.maps.LatLng(parseFloat(currentLat), parseFloat(currentLng)),
                zoom: 13
            };
            const map = new naver.maps.Map('map', mapOptions);

            // 3. JSP에서 Java 변수를 JavaScript 변수로 변환하여 사용
            const farmList = <%= (new JSONParser().parse(farmDataJson)).toString() %>; // 전체 JSON 데이터
            
            // [추측입니다] 네이버 지역 검색 API에서 받은 좌표는 mapx(경도)/mapy(위도)이며 BTM 좌표계일 수 있습니다.
            // 네이버 지도 API는 LatLng(WGS84)을 사용하므로, 좌표 변환을 고려해야 합니다.
            // 여기서는 단순화를 위해 mapx/mapy를 바로 위경도로 사용합니다. (정확한 구현 시 변환 필수)
            
            if (farmList && farmList.items) {
                farmList.items.forEach(item => {
                    // mapx와 mapy는 문자열이므로 parseFloat으로 변환합니다.
                    const mapx = parseFloat(item.mapx); 
                    const mapy = parseFloat(item.mapy); 

                    // [주의] LatLng 객체는 위도(Lat)를 먼저 받습니다. (mapy -> mapx 순서)
                    const position = new naver.maps.LatLng(mapy, mapx); 

                    // 마커 생성
                    const marker = new naver.maps.Marker({
                        map: map,
                        position: position,
                        title: item.title.replace(/<[^>]*>/g, '') // HTML 태그 제거
                    });
                });
            }
        }

        // 페이지 로드 후 함수 실행
        window.onload = initializeMapAndMarkers;
    </script>

</body>
</html>