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

@WebServlet("/api/farms")
public class FarmSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // 검색용 키 (Search API)
    private final String CLIENT_ID = "wPjSjpqQSNytJ4eJcQCV"; 
    private final String CLIENT_SECRET = "Uz8F8K3YOF";

    // 지도/주소변환용 키 (Map/Reverse Geocoding API)
    private final String MAPS_CLIENT_ID = "3fy9khavdx"; 
    private final String MAPS_CLIENT_SECRET = "6lfjwfwdeAb7k63HVKyp0ntLUHvHpzgoeJ3wRsCm"; 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("=== 서블릿 도착 성공 ===");
        
        String wgsLat = request.getParameter("lat");
        String wgsLng = request.getParameter("lng");
        
        String regionName = "서울";

        if (wgsLat != null && wgsLng != null && !wgsLat.isEmpty()) {
            String detectedRegion = getRegionFromCoords(wgsLng, wgsLat);
            if (detectedRegion != null) {
                regionName = detectedRegion; 
            }
        }

        String searchKeyword = regionName + " 주말농장";

        System.out.println("검색어 확인: " + searchKeyword);

        String query = URLEncoder.encode(searchKeyword, "UTF-8");

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + query + "&display=5";

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
        con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
        
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

        request.setAttribute("farmDataJson", responseData.toString());
        request.setAttribute("currentLat", wgsLat); 
        request.setAttribute("currentLng", wgsLng);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/farmMapView.jsp");
        dispatcher.forward(request, response);
    }

    private String getRegionFromCoords(String lng, String lat) {
        try {
            System.out.println(">>> [위치찾기] 좌표 확인: lat=" + lat + ", lng=" + lng);

            String apiURL = "https://maps.apigw.ntruss.com/map-reversegeocode/v2/gc";
            String params = "coords=" + lng + "," + lat + "&sourcecrs=epsg:4326&orders=legalcode&output=json";
            
            URL url = new URL(apiURL + "?" + params);
            System.out.println(apiURL + "?" + params);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", MAPS_CLIENT_ID);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", MAPS_CLIENT_SECRET);
            
            int responseCode = con.getResponseCode();
            
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                return null;
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) sb.append(line);
            br.close();
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(sb.toString());
            JSONArray results = (JSONArray) json.get("results");
            
            if (results != null && results.size() > 0) {
                JSONObject result = (JSONObject) results.get(0);
                JSONObject region = (JSONObject) result.get("region");
                JSONObject area2 = (JSONObject) region.get("area2");
                
                String guName = (String) area2.get("name");
                
                System.out.println(">>> [성공] 검색할 동네: " + guName);
                
                return guName; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }
}