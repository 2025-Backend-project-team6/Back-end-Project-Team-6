package com.gardenlog.servlet.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@WebServlet("/api/farms")
public class FarmSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // [보안 주의] Client ID와 Secret을 실제 값으로 변경하세요.
    private final String CLIENT_ID = "wPjSjpqQSNytJ4eJcQCV"; 
    private final String CLIENT_SECRET = "Uz8F8K3YOF";
    // 네이버 지도 API ID/KEY는 지역 검색 API와 다를 수 있습니다.
    private final String MAPS_CLIENT_ID = "YOUR_MAPS_CLIENT_ID"; 
    private final String MAPS_CLIENT_SECRET = "YOUR_MAPS_CLIENT_SECRET"; 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. URL 파라미터로 전달된 WGS84 좌표를 가져옵니다.
        String wgsLat = request.getParameter("lat"); // WGS84 위도
        String wgsLng = request.getParameter("lng"); // WGS84 경도
        
        String btmCoord = null;
        
        // 2. WGS84 좌표를 네이버 검색에 사용될 BTM 좌표로 변환합니다. (핵심)
        if (wgsLat != null && wgsLng != null) {
            try {
                btmCoord = convertWgsToBtm(wgsLng, wgsLat);
            } catch (Exception e) {
                System.err.println("좌표 변환 중 오류 발생: " + e.getMessage());
                // 변환 실패 시 btmCoord는 null로 유지
            }
        }
        
        // 3. 지역 검색 API 요청 URL 생성
        String query = URLEncoder.encode("주말농장", "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + query + "&display=50";

        // 💡 4. 변환된 BTM 좌표를 사용하여 검색 기준을 지정 (주변 검색 효과)
        if (btmCoord != null) {
            // target=BTM_X,BTM_Y 형식으로 추가합니다.
            apiURL += "&target=" + btmCoord;
        }

        // 5. HTTP 연결 설정 및 요청 (지역 검색 API)
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
        con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
        
        // 6. 응답 데이터 읽기
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode == 200) { 
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
        }
        
        String inputLine;
        StringBuffer responseData = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            responseData.append(inputLine);
        }
        br.close();
        
        // 7. JSP로 데이터 전달 및 포워딩
        request.setAttribute("farmDataJson", responseData.toString());
        // 지도의 중심 설정을 위해 원본 WGS84 좌표를 그대로 전달합니다.
        request.setAttribute("currentLat", wgsLat); 
        request.setAttribute("currentLng", wgsLng);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/farmMapView.jsp");
        dispatcher.forward(request, response);
    }
    
    
    // 💡 8. WGS84 좌표를 네이버 BTM 좌표로 변환하는 헬퍼 메서드
    private String convertWgsToBtm(String wgsLng, String wgsLat) throws IOException, ParseException {
        // [근거] 네이버 좌표 변환 API 문서
        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geo/v2/transform";
        
        // fromSystem=WGS84, toSystem=BTM, coords=경도,위도 (변환 API는 경도,위도 순서)
        String params = "fromCoord=" + wgsLng + "," + wgsLat + "&fromSystem=epsg:4326&toSystem=epsg:5179";
        
        URL url = new URL(apiURL + "?" + params);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        // 이 API는 지도/지리 관련 키를 사용해야 합니다.
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", MAPS_CLIENT_ID);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", MAPS_CLIENT_SECRET);
        
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
        }
        
        String inputLine;
        StringBuffer responseData = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            responseData.append(inputLine);
        }
        br.close();
        
        // 9. JSON 파싱을 통해 BTM 좌표 추출
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(responseData.toString());
        
        // 변환된 좌표 배열 (JSON 구조: result.items[0].point)
        JSONObject result = (JSONObject) jsonObject.get("result");
        JSONArray items = (JSONArray) result.get("items");
        
        if (items != null && items.size() > 0) {
            JSONObject item = (JSONObject) items.get(0);
            JSONObject point = (JSONObject) item.get("point");
            
            // BTM 좌표 (X, Y) 추출
            String btmX = (String) point.get("x"); 
            String btmY = (String) point.get("y"); 
            
            // "BTM_X,BTM_Y" 형태로 반환
            return btmX + "," + btmY;
        }
        return null;
    }
}