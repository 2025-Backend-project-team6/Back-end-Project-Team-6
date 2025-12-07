<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String farmDataJson = (String) request.getAttribute("farmDataJson");
    String currentLat = (String) request.getAttribute("currentLat");
    String currentLng = (String) request.getAttribute("currentLng");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 주변 주말농장 찾기</title>
    
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/farmMapView.css">
    
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=f05sse6krq&submodules=geocoder"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    
    <div class="map-container-wrap">
        <h2 class="page-title"><span class="icon">🌱</span> 내 주변 주말농장 찾기</h2>
        <div id="map" class="map-view"></div>
    </div>

    <div id="loading">내 주변 농장을 찾는 중입니다...🚜</div>

    <script>
        $(document).ready(function() {
            const serverLat = "<%= currentLat != null ? currentLat : "" %>";
            const serverLng = "<%= currentLng != null ? currentLng : "" %>";

            if (!serverLat || !serverLng) {
                if (navigator.geolocation) {
                    $('#loading').show();
                    navigator.geolocation.getCurrentPosition(function(position) {
                        const lat = position.coords.latitude;
                        const lng = position.coords.longitude;
                        
                        const contextPath = "<%= request.getContextPath() %>";
                        location.replace(contextPath + "/api/farms?lat=" + lat + "&lng=" + lng);
                        
                    }, function(error) {
                        alert("위치 정보를 가져올 수 없어 기본 위치(서울)로 이동합니다.");
                        const contextPath = "<%= request.getContextPath() %>";
                        location.replace(contextPath + "/api/farms?lat=37.5665&lng=126.9780");
                    });
                } else {
                    alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
                }
                return; 
            }

            const myLocation = new naver.maps.LatLng(parseFloat(serverLat), parseFloat(serverLng));
            const map = new naver.maps.Map('map', {
                center: myLocation,
                zoom: 14
            });

            new naver.maps.Marker({
                position: myLocation,
                map: map,
                icon: {
                    content: '<div style="background:red; width:12px; height:12px; border-radius:50%; border:3px solid white; box-shadow:0 0 5px rgba(0,0,0,0.5);"></div>',
                    anchor: new naver.maps.Point(9, 9)
                }
            });

            const jsonString = '<%= farmDataJson != null ? farmDataJson.replace("'", "\\'") : "{}" %>';
            const farmData = JSON.parse(jsonString);

            if (farmData.items) {
                const infoWindow = new naver.maps.InfoWindow({ anchorSkew: true });

                farmData.items.forEach(function(item) {
                    if(item.mapx && item.mapy) {
                        const lat = parseFloat(item.mapy) / 10000000; 
                        const lng = parseFloat(item.mapx) / 10000000; 
                        const latLng = new naver.maps.LatLng(lat, lng);

                        const marker = new naver.maps.Marker({
                            position: latLng,
                            map: map,
                            title: item.title.replace(/<[^>]*>/g, '')
                        });
                        
                        naver.maps.Event.addListener(marker, "click", function(e) {
                            const contentString = [
                                '<div class="iw_content">',
                                '   <b>' + item.title + '</b>',
                                '   <p>' + item.roadAddress + '</p>',
                                '   <p>' + (item.telephone ? item.telephone : '') + '</p>',
                                '</div>'
                            ].join('');

                            infoWindow.setContent(contentString);
                            infoWindow.open(map, marker);
                        });
                    }
                });
            }
        });
    </script>
</body>
</html>