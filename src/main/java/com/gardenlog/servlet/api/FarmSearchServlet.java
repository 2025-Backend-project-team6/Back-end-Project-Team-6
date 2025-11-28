package com.gardenlog.servlet.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.dto.ApiFarmDTO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet("/api/farms")
public class FarmSearchServlet extends HttpServlet {
	
	private static final String CLIENT_ID = "wPjSjpqQSNytJ4eJcQCV"; 
    private static final String CLIENT_SECRET = "Uz8F8K3YOF";
    
    // 에러 발생 시 포워딩할 JSP 경로
    private static final String ERROR_VIEW = "/error.jsp";
    // 정상 작동 시 포워딩할 JSP 경로
    private static final String SUCCESS_VIEW = "/farmMapView.jsp";
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. JSP에서 이 이름(lat, lng)으로 파라미터를 넘겨줘야함
		String userLatitudeParam = request.getParameter("lat"); // y (위도)
        String userLongitudeParam = request.getParameter("lng"); // x (경도)
        String searchKeyword = "주말농장";
        
     // 2. 위도/경도 값이 없거나 유효하지 않으면 사용자에게 추가 정보 요청
        if (userLatitudeParam == null || userLongitudeParam == null || userLatitudeParam.isEmpty() || userLongitudeParam.isEmpty()) {
            // 확실하지 않음: 위치 정보가 없으므로 API 호출을 할 수 없습니다.
            request.setAttribute("errorMessage", "위치 정보(위도, 경도)가 요청에 포함되지 않았습니다. 사용자 위치를 확인해주세요.");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_VIEW);
            dispatcher.forward(request, response);
            return; // 서블릿 실행 중지
        }
        
     // Double.parseDouble() 시 발생할 수 있는 NumberFormatException 처리를 위한 try-catch
        double userLatitude;
        double userLongitude;
        
        try {
            userLatitude = Double.parseDouble(userLatitudeParam);
            userLongitude = Double.parseDouble(userLongitudeParam);
        } catch (NumberFormatException e) {
            // 확실하지 않음: 위도/경도 값이 숫자가 아님
            request.setAttribute("errorMessage", "위치 정보의 형식이 올바르지 않습니다. 숫자 형식으로 전달해주세요.");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_VIEW);
            dispatcher.forward(request, response);
            return; // 서블릿 실행 중지
        }
        
        try {
            // 💡 3. 네이버 지역 검색 API URL 구성
            String encodedQuery = URLEncoder.encode(searchKeyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + encodedQuery
                            + "&x=" + userLongitude   // 경도 (Longitude)
                            + "&y=" + userLatitude    // 위도 (Latitude)
                            + "&sort=distance"        // 거리순 정렬
                            + "&display=10";          // 10개 검색 (최대)

            // 4. API 연결 및 인증 헤더 설정
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);

            // 5. 응답 코드 확인 및 데이터 읽기
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출 (확실한 정보)
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {  // 에러 발생 (확실하지 않음: API 인증 실패, 요청 오류 등)
                // 에러 발생 시 상세 응답을 읽어 에러 메시지를 확인합니다.
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                
                request.setAttribute("errorMessage", "네이버 API 호출에 실패했습니다. 응답 코드: " + responseCode);
                RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_VIEW);
                dispatcher.forward(request, response);
                return; // 에러 발생 시 서블릿 실행 중지
            }
            
         // 6. JSON 응답 전문(Full String) 읽기
            String inputLine;
            StringBuffer jsonResponse = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                jsonResponse.append(inputLine);
            }
            br.close();
            con.disconnect();
            
         // 7. 핵심: Gson을 사용한 JSON 파싱 및 데이터 추출
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse.toString(), JsonObject.class);
            JsonArray items = jsonObject.getAsJsonArray("items"); // 'items' 배열 추출
            
            List<ApiFarmDTO> farmList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                // 'items' 배열의 각 JSON 객체를 우리가 만든 FarmInfo 클래스에 바로 매핑
                JsonObject item = items.get(i).getAsJsonObject();
                ApiFarmDTO farm = gson.fromJson(item, ApiFarmDTO.class);
                farmList.add(farm);
                // System.out.println("파싱된 농장: " + farm); // 콘솔 디버그용
            }
            
         //  8. 파싱된 데이터를 JSP로 전달하고 포워딩
            request.setAttribute("farmList", farmList);
            RequestDispatcher dispatcher = request.getRequestDispatcher(SUCCESS_VIEW);
            dispatcher.forward(request, response);
            
        } catch (IOException e) {
            // 네트워크 오류, URL 오류 등 IO 관련 예외 처리
            e.printStackTrace();
            request.setAttribute("errorMessage", "네트워크 연결 또는 API 주소 설정에 오류가 발생했습니다: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_VIEW);
            dispatcher.forward(request, response);
        }
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}

}
