package com.gardenlog.servlet.service;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.net.URLEncoder;
	import java.time.LocalDateTime;
	import java.time.LocalTime;
	import java.time.ZoneId;
	import java.time.format.DateTimeFormatter;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import com.gardenlog.servlet.dto.HourlyWeatherDTO;
	import com.gardenlog.servlet.dto.WeatherInfoDTO;
	import com.gardenlog.servlet.util.LatLonToXYConverter;
	import com.gardenlog.servlet.util.LatLonToXYConverter.LatLonPoint;
	import com.google.gson.JsonArray;
	import com.google.gson.JsonObject;
	import com.google.gson.JsonParser;

	// 이 클래스는 외부 API 호출, 데이터 파싱, 가공 등 모든 비즈니스 로직을 담당합니다.
	public class WeatherService {

	    // 💡 (기존 WeatherServlet에서 가져옴) 사용자 인증 키
	    private final String SERVICE_KEY = "a23deb2d0a8cc3d8a4e50571cff269d21a75370b77fc1dfddb242ebe77ca8455";
	    
	    // 💡 (기존 WeatherServlet에서 가져옴) 기상청 API URL
	    private final String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

	    // 💡 (기존 WeatherServlet에서 가져옴) Base Time 계산 로직에 사용
	    private static final LocalTime[] BASE_TIMES = 
	        {LocalTime.of(2, 0), LocalTime.of(5, 0), LocalTime.of(8, 0), 
	         LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), 
	         LocalTime.of(20, 0), LocalTime.of(23, 0)};

	    // --- (기존 WeatherServlet의 getBaseTimeAndDate 메서드) ---
	    // API 호출에 필요한 baseDate와 baseTime을 계산합니다.
	    private String[] getBaseTimeAndDate() {
	        // API 발표는 KST 기준
	        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
	        LocalTime currentTime = now.toLocalTime();
	        
	        // 발표 후 10분이 지나야 데이터를 요청할 수 있다고 가정합니다.
	        LocalTime currentCheckTime = currentTime.minusMinutes(10);
	        
	        LocalDateTime baseDateTime = now;
	        LocalTime finalBaseTime = null;

	        // 가장 최근 발표 시각 찾기 (역순 탐색)
	        for (int i = BASE_TIMES.length - 1; i >= 0; i--) {
	            LocalTime baseTime = BASE_TIMES[i];
	            
	            if (currentCheckTime.isAfter(baseTime) || currentCheckTime.equals(baseTime)) {
	                finalBaseTime = baseTime;
	                break;
	            }
	        }
	        
	        // 당일 발표된 데이터가 없는 경우 (자정 ~ 02:10 사이) -> 어제 23:00 발표 데이터 사용
	        if (finalBaseTime == null) {
	            finalBaseTime = LocalTime.of(23, 0); 
	            baseDateTime = now.minusDays(1);
	        }

	        // 포맷 적용
	        String timeStr = finalBaseTime.format(DateTimeFormatter.ofPattern("HH00")); // HH00
	        String dateStr = baseDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // yyyyMMdd

	        return new String[]{dateStr, timeStr};
	    }
	    // -------------------------------------------------------------------

	    
	    /**
	     * 최종적으로 Controller에서 호출할 메인 로직입니다.
	     * 위도/경도를 받아 격자 좌표를 계산하고, API를 호출하여 가공된 날씨 리스트를 반환합니다.
	     * @param lat 위도
	     * @param lon 경도
	     * @return 시간대별 날씨 예보 리스트
	     * @throws IOException 
	     */
	    public List<HourlyWeatherDTO> getWeatherForecast(double lat, double lon) throws IOException {

	        // 1. 위도/경도를 격자 좌표 (X, Y)로 변환
	        LatLonPoint pt = LatLonToXYConverter.convert(lat, lon);
	        int nx = pt.x;
	        int ny = pt.y;
	        
	        // 2. API 호출에 필요한 base_date 및 base_time 계산
	        String[] baseInfo = getBaseTimeAndDate();
	        String baseDate = baseInfo[0];
	        String baseTime = baseInfo[1];
	        
	        // 3. API 호출 URL 생성 (기존 WeatherDAO 로직)
	        StringBuilder urlBuilder = new StringBuilder(API_URL);	       	    
	        
	        // serviceKey 파라미터 연결 시 '=' 문자가 누락되어 추가했습니다.
	        urlBuilder.append("?serviceKey=" + SERVICE_KEY);
	        
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(nx), "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(ny), "UTF-8"));

	        System.out.println("[DEBUG Service] 최종 API 요청 URL: " + urlBuilder.toString()); // ✅ 완벽한 URL이 찍힘
	        
	        // 4. API 호출 및 응답 처리 (기존 WeatherDAO 로직)
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");

	        BufferedReader rd;
	        int responseCode = conn.getResponseCode();
	        
	        System.out.println("[DEBUG Service] API 응답 코드: " + responseCode);
	        
	        if (responseCode >= 200 && responseCode <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
	        }
	        
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        
	        String resultJson = sb.toString();
	        
	        if (responseCode != 200) {
	        	
	        	System.err.println("[DEBUG Service] 오류 응답 JSON: " + resultJson);
	        	
	            System.err.println("기상청 API 응답 코드 오류: " + responseCode + ", 응답 내용: " + resultJson);
	            throw new IOException("기상청 API 응답 코드 오류: " + responseCode);
	        }
	        
	       // 5. JSON 파싱 및 DTO 변환 (기존 WeatherDAO 로직)
	        List<WeatherInfoDTO> rawWeatherList = parseJsonToDTO(resultJson, nx, ny);
	        
	        System.out.println("[DEBUG Service] 파싱된 원본 항목 수 (rawWeatherList): " + rawWeatherList.size());
	        
	        // 6. 그룹화 및 최종 DTO 변환 (기존 WeatherDAO 로직)
	        return groupAndTransform(rawWeatherList);   
	    }
	    
	    // --- (기존 WeatherDAO의 parseJsonToDTO 메서드) ---
	    private List<WeatherInfoDTO> parseJsonToDTO(String jsonString, int nx, int ny) {
	        List<WeatherInfoDTO> weatherList = new ArrayList<>();
	        
	        try {
	            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
	            
	            JsonObject response = jsonObject.getAsJsonObject("response");
	            JsonObject body = response.getAsJsonObject("body");
	            JsonObject items = body.getAsJsonObject("items");
	            JsonArray itemArray = items.getAsJsonArray("item");

	            for (int i = 0; i < itemArray.size(); i++) {
	                JsonObject item = itemArray.get(i).getAsJsonObject();
	                
	                String fcstDate = item.get("fcstDate").getAsString();
	                String fcstTime = item.get("fcstTime").getAsString();
	                String category = item.get("category").getAsString();
	                String fcstValue = item.get("fcstValue").getAsString();
	                
	                WeatherInfoDTO dto = new WeatherInfoDTO(fcstDate, fcstTime, category, fcstValue, nx, ny);
	                weatherList.add(dto);
	            }
	            
	        } catch (Exception e) {
	            System.err.println("JSON 파싱 중 오류가 발생했습니다: " + e.getMessage());
	        }
	        return weatherList;
	    }
	    
	    // --- (기존 WeatherDAO의 groupAndTransform 메서드) ---
	    // WeatherInfoDTO는 흩어져있기에 시간대 별로 통합하여 HourlyWeatherDTO로 변환
	    private List<HourlyWeatherDTO> groupAndTransform(List<WeatherInfoDTO> rawWeatherList) {
	        
	    Map<String, HourlyWeatherDTO> hourlyDataMap = new HashMap<>();

	        for (WeatherInfoDTO item : rawWeatherList) {
	            
	            String key = item.getFcstDate() + item.getFcstTime();

	            // HourlyWeatherDTO 생성 또는 가져오기 (기본값 설정된 생성자 활용)
	            HourlyWeatherDTO hourlyData = hourlyDataMap.getOrDefault(key, new HourlyWeatherDTO());

	            hourlyData.setFcstDate(item.getFcstDate());
	            hourlyData.setFcstTime(item.getFcstTime());

	            String category = item.getCategory().trim();
	            String value = item.getFcstValue().trim();

	            if (value.isEmpty()) {
	                continue;
	            }

	            switch (category) {
	                case "TMP": // 기온
	                    hourlyData.setTemperature(value); 
	                    break;
	                case "SKY": // 하늘 상태
	                    hourlyData.setSkyStatus(value);
	                    break;
	                case "REH": // 습도
	                    hourlyData.setHumidity(value);
	                    break;
	                case "PTY": // 강수 형태
	                    hourlyData.setPrecipitationType(value);
	                    break;
	                case "PCP": // 강수량
	                    hourlyData.setPrecipitationAmount(value);
	                    break;
	            }
	            
	            hourlyDataMap.put(key, hourlyData);
	        }
	        
	        // 최종적으로 Map의 Value들만 List로 추출하여 반환 및 정렬
	        List<HourlyWeatherDTO> resultList = new ArrayList<>(hourlyDataMap.values());
	        resultList.sort((o1, o2) -> {
	            int dateCompare = o1.getFcstDate().compareTo(o2.getFcstDate());
	            if (dateCompare != 0) return dateCompare;
	            return o1.getFcstTime().compareTo(o2.getFcstTime());
	        });
	        
	        return resultList;
	    }
	}

