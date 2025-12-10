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
    <title>내 주변 주말텃밭 찾기</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/CSS/farmMapView.css">
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=3fy9khavdx&submodules=geocoder"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

    <div class="header-wrapper">
        <jsp:include page="./header.jsp" />
    </div>

    <div class="main-container">
        
        <div class="page-title-box">
            <h2>내 주변 텃밭찾기 🗺️</h2>
            <p>현재 위치에서 가까운 주말 농장을 확인하세요.</p>
        </div>
        <div class="map-list-wrapper">
            <div class="farm-list-sidebar">
                <div class="list-header">
                    <h2>🌱 주변 텃밭 <span class="count" id="result-count">0</span>곳</h2>
                </div>
                <div id="farm-list-box">
                </div>
            </div>

            <div class="map-wrapper">
                <div id="map"></div>
            </div>
        </div>
        </div>

    <div id="loading">🚜 열심히 농장을 찾고 있어요...</div>

    <script>
        $(document).ready(function() {
            const serverLat = "<%= currentLat != null ? currentLat : "" %>";
            const serverLng = "<%= currentLng != null ? currentLng : "" %>";

            if (!serverLat || !serverLng) {
                if (navigator.geolocation) {
                    $('#loading').show();
                    navigator.geolocation.getCurrentPosition(function(pos) {
                        const path = "<%= request.getContextPath() %>";
                        location.replace(path + "/api/farms?lat=" + pos.coords.latitude + "&lng=" + pos.coords.longitude);
                    }, function() {
                        alert("위치를 찾을 수 없어 기본 위치로 이동합니다.");
                        const path = "<%= request.getContextPath() %>";
                        location.replace(path + "/api/farms?lat=37.5665&lng=126.9780");
                    });
                }
                return;
            }

            const myLocation = new naver.maps.LatLng(parseFloat(serverLat), parseFloat(serverLng));
            const map = new naver.maps.Map('map', {
                center: myLocation,
                zoom: 13
            });

            new naver.maps.Marker({
                position: myLocation,
                map: map,
                icon: {
                    content: '<div style="background:#ff3333; width:14px; height:14px; border-radius:50%; border:3px solid white; box-shadow:0 0 5px rgba(0,0,0,0.3);"></div>',
                    anchor: new naver.maps.Point(10, 10)
                }
            });

            const jsonString = '<%= farmDataJson != null ? farmDataJson.replace("'", "\\'") : "{}" %>';
            const farmData = JSON.parse(jsonString);
            const items = farmData.items || [];

            $('#result-count').text(items.length);

            const markers = [];
            const infoWindow = new naver.maps.InfoWindow({ anchorSkew: true });

            items.forEach(function(item, index) {
                if(!item.mapx || !item.mapy) return;

                const lat = parseFloat(item.mapy) / 10000000;
                const lng = parseFloat(item.mapx) / 10000000;
                const latLng = new naver.maps.LatLng(lat, lng);

                const marker = new naver.maps.Marker({
                    position: latLng,
                    map: map,
                    title: item.title.replace(/<[^>]*>/g, '')
                });
                markers.push(marker);

                const cleanTitle = item.title.replace(/<[^>]*>/g, '');
                const cleanAddr = item.roadAddress || item.address;
                const html = 
                    '<div class="farm-item" id="item-' + index + '">' +
                    '   <div class="farm-title">' + (index+1) + '. ' + cleanTitle + '</div>' +
                    '   <div class="farm-addr">' + cleanAddr + '</div>' +
                    '</div>';
                
                $('#farm-list-box').append(html);

                $('#item-' + index).on('click', function() {
                    map.panTo(latLng);
                    infoWindow.setContent('<div style="padding:10px;min-width:150px;"><b>'+cleanTitle+'</b></div>');
                    infoWindow.open(map, marker);

                    $('.farm-item').removeClass('active');
                    $(this).addClass('active');
                });

                naver.maps.Event.addListener(marker, "click", function(e) {
                    $('#item-' + index).click();
                });
            });
        });
    </script>
</body>
</html>