package com.gardenlog.servlet.util;

public class LatLonToXYConverter {
	
	// 기상청 격자 좌표 변환을 위한 상수 설정
    private static final double RE = 6371.00877; // 지구 반경 (km)
    private static final double GRID = 5.0;      // 격자 간격 (km)
    private static final double SLAT1 = 30.0;    // 표준 위도 1
    private static final double SLAT2 = 60.0;    // 표준 위도 2
    private static final double OLON = 126.0;    // 기준 경도
    private static final double OLAT = 38.0;     // 기준 위도
    private static final double XO = 43;         // 기준점 X좌표 (이 값은 기상청 설정값임)
    private static final double YO = 136;        // 기준점 Y좌표 (이 값은 기상청 설정값임)

    private LatLonToXYConverter() {} // 유틸리티 클래스이므로 인스턴스 생성 방지

    public static class LatLonPoint {
        public double lat; // 위도
        public double lon; // 경도
        public int x;      // 격자 X
        public int y;      // 격자 Y
    }

    // 위도, 경도를 격자 좌표 (X, Y)로 변환하는 메인 함수
    public static LatLonPoint convert(double lat, double lon) {
        LatLonPoint rs = new LatLonPoint();
        rs.lat = lat;
        rs.lon = lon;

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), sn);

        double ra = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5), sn);
        
        if (sn == 0.0) { // 예외 처리 추가 (거의 발생하지 않지만, 수학적 안정성을 위해)
             rs.x = 0;
             rs.y = 0;
             return rs;
        }

        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        rs.x = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs.y = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return rs;
    }
}
