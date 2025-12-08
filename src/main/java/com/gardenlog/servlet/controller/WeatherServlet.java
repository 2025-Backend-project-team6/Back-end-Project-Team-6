package com.gardenlog.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gardenlog.servlet.dao.WeatherDAO;
import com.gardenlog.servlet.dto.HourlyWeatherDTO;
import com.gardenlog.servlet.util.LatLonToXYConverter;
import com.gardenlog.servlet.util.LatLonToXYConverter.LatLonPoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/weather.do")

public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
	    super.init();
	    System.out.println("★ ★ ★ DEBUG SUCCESS ★ ★ ★: WeatherServlet 클래스 로딩 및 초기화 성공!");
	    System.out.println("★ ★ ★ API URL: " + API_URL); // API_URL 상수가 정상 정의되었는지 확인
	}
       
	// 사용자 인증 키
    private final String SERVICE_KEY = "a23deb2d0a8cc3d8a4e50571cff269d21a75370b77fc1dfddb242ebe77ca8455"; 
    
 // 기상청 API URL (단기 예보 조회 예시)
    private final String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    
    // --- Base Time 계산 로직  ---
    // 단기 예보 API 발표 시간: 02, 05, 08, 11, 14, 17, 20, 23시 (정시로부터 10분 후 발표)
    private static final LocalTime[] BASE_TIMES = 
        {LocalTime.of(2, 0), LocalTime.of(5, 0), LocalTime.of(8, 0), 
        	    LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), 
        	    LocalTime.of(20, 0), LocalTime.of(23, 0)
        	};
    
 
    private String[] getBaseTimeAndDate() {
        // API 발표는 KST 기준이므로 TimeZone을 명확히 하는 것이 좋습니다.
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalTime currentTime = now.toLocalTime();
        
        // 발표 후 10분이 지나야 해당 데이터를 요청할 수 있다고 가정합니다.
        LocalTime currentCheckTime = currentTime.minusMinutes(10);
        
        // 최종적으로 API에 사용할 날짜/시간
        LocalDateTime baseDateTime = now; 
        LocalTime finalBaseTime = null; 

        // 1. 현재 시간보다 '10분 전'을 기준으로, 가장 최근 발표 시각 찾기 (역순 탐색)
        for (int i = BASE_TIMES.length - 1; i >= 0; i--) {
            LocalTime baseTime = BASE_TIMES[i];
            
            if (currentCheckTime.isAfter(baseTime) || currentCheckTime.equals(baseTime)) {
                finalBaseTime = baseTime;
                break; // 찾았으면 루프 종료
            }
        }
        
        // 2. 가장 치명적인 문제 해결: 당일 발표된 데이터가 없는 경우 (자정 ~ 02:10 사이)
        if (finalBaseTime == null) {
            // 어제 23:00 발표 데이터를 사용해야 함
            finalBaseTime = LocalTime.of(23, 0); 
            baseDateTime = now.minusDays(1); // 날짜를 어제로 설정 (날짜 역산)
        }

        // 3. 포맷 적용
        // Base Time 포맷: "HH00"
        String timeStr = finalBaseTime.format(DateTimeFormatter.ofPattern("HH00"));

        // Base Date 포맷: "yyyyMMdd"
        String dateStr = baseDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return new String[]{dateStr, timeStr};
    }
    // -------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 요청 파라미터(위도/경도) 획득
        String latStr = request.getParameter("lat"); 
        String lonStr = request.getParameter("lon"); 

        if (latStr == null || lonStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "위도 또는 경도 정보가 없습니다.");
            return;
        }

        try {
            double lat = Double.parseDouble(latStr);
            double lon = Double.parseDouble(lonStr);
            
            // 2. 위도/경도를 격자 좌표 (X, Y)로 변환
            LatLonPoint pt = LatLonToXYConverter.convert(lat, lon);
            int nx = pt.x;
            int ny = pt.y;
            
            // 3. API 호출에 필요한 base_date 및 base_time 계산 (생략)
            String[] baseInfo = getBaseTimeAndDate();
            String baseDate = baseInfo[0];
            String baseTime = baseInfo[1];
            
            // 4. DAO를 통해 API 호출 및 JSON 파싱, DTO로 변환 (List<HourlyWeatherDTO> 반환)
            WeatherDAO dao = new WeatherDAO(API_URL, SERVICE_KEY);
            List<HourlyWeatherDTO> weatherList = dao.getWeatherForecast(baseDate, baseTime, nx, ny);
            
            // 5. 위치 정보 설정 (임시)
            String locationName = "동양미래대학교 근처"; // 기본 위치 또는 동양미래대 위치
            
            // 6. JSON 응답 생성 및 전송 (가장 중요한 수정 부분!)
            
            // 응답 타입을 JSON으로 설정하고 UTF-8 인코딩 지정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // JSON 객체를 만들기 위해 Map에 데이터 정리 (위치 정보, 날씨 리스트 포함)
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("locationName", locationName);
            responseData.put("weatherList", weatherList);
            
            // Gson 객체 생성 (보기 좋게 포맷팅)
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            // Map을 JSON 문자열로 변환
            String jsonResponse = gson.toJson(responseData);
            
            // 클라이언트에 JSON 응답 작성
            response.getWriter().write(jsonResponse);
            
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "위도 또는 경도 형식이 잘못되었습니다.");
        } catch (Exception e) {
            // TODO 3: 실제 운영 시에는 e.printStackTrace() 대신 로깅 라이브러리를 사용해야 합니다.
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "날씨 정보 조회 중 오류가 발생했습니다.");
        }
    }
}
