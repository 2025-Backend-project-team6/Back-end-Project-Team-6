package com.gardenlog.servlet.dao;

import com.gardenlog.servlet.dto.HourlyWeatherDTO;
import com.gardenlog.servlet.dto.WeatherInfoDTO;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class WeatherDAO {

    private final String API_URL;
    private final String SERVICE_KEY;
    
    // DAO 객체 생성 시 API 정보 주입
    public WeatherDAO(String apiUrl, String serviceKey) {
    
        this.API_URL = apiUrl;      
        this.SERVICE_KEY = serviceKey;
    }
    
    // 격자 좌표를 기반으로 기상청 단기 예보를 조회하고 DTO 리스트로 반환
    public List<HourlyWeatherDTO> getWeatherForecast(String baseDate, String baseTime, int nx, int ny) 
            throws IOException {
        
        // 1. API 호출 URL 생성
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        
        // [수정] serviceKey 파라미터 연결 시 '=' 문자가 누락되어 추가했습니다.
        urlBuilder.append("?serviceKey=" + SERVICE_KEY);
        
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(nx), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(ny), "UTF-8"));

        
        // 2. API 호출
        URL url = new URL(urlBuilder.toString());
        
        System.out.println("API 요청 URL: " + urlBuilder.toString());
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        // 3. 응답 읽기
        BufferedReader rd;
        int responseCode = conn.getResponseCode();
        
        if (responseCode >= 200 && responseCode <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            // 오류 응답 처리 (오류 스트림 읽기)
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
            // TODO: 오류 JSON을 파싱하여 상세 오류 메시지를 로그에 기록해야 합니다.
            throw new IOException("기상청 API 응답 코드 오류: " + responseCode);
        }
        
       // 1. rawWeatherList는 List<WeatherInfoDTO> (JSON 파싱 결과)
        List<WeatherInfoDTO> rawWeatherList = parseJsonToDTO(resultJson, nx, ny);
        
        // 2. 그룹화 로직을 호출하여 List<HourlyWeatherDTO>로 변환
        return groupAndTransform(rawWeatherList);   
    }
    
    // API 응답 JSON 문자열을 읽어 날씨 항목 별로 분리된 WeatherInfoDTO 를 만듬
    private List<WeatherInfoDTO> parseJsonToDTO(String jsonString, int nx, int ny) {
        List<WeatherInfoDTO> weatherList = new ArrayList<>();
        
        try {
            // 1. JSON 문자열 파싱 준비 (JsonParser를 사용하여 최상위 객체 가져오기)
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            
            // 2. 필요한 'item' 배열 경로 탐색: response -> body -> items -> item
            // 
            JsonObject response = jsonObject.getAsJsonObject("response");
            JsonObject body = response.getAsJsonObject("body");
            JsonObject items = body.getAsJsonObject("items");
            JsonArray itemArray = items.getAsJsonArray("item"); // 예보 데이터 리스트

            // 3. 배열을 반복하면서 각 item에서 필요한 데이터 추출
            for (int i = 0; i < itemArray.size(); i++) {
                JsonObject item = itemArray.get(i).getAsJsonObject();
                
                String fcstDate = item.get("fcstDate").getAsString(); // 예보 날짜 (yyyyMMdd)
                String fcstTime = item.get("fcstTime").getAsString(); // 예보 시각 (HHMM)
                String category = item.get("category").getAsString(); // 예보 항목 코드 (PTY, T1H, SKY 등)
                String fcstValue = item.get("fcstValue").getAsString(); // 예보 값
                
                // DTO 객체 생성 및 리스트에 추가
                WeatherInfoDTO dto = new WeatherInfoDTO(fcstDate, fcstTime, category, fcstValue, nx, ny);
                weatherList.add(dto);
            }
            
        } catch (Exception e) {
            // TODO: JSON 파싱 오류 발생 시 로그 기록
            System.err.println("JSON 파싱 중 오류가 발생했습니다: " + e.getMessage());
        }
        return weatherList;
    }
    
    // WeatherInfoDTO는 흩어져있기에 시간대 별로 통합하여 HourlyWeatherDTO로 변환
    private List<HourlyWeatherDTO> groupAndTransform(List<WeatherInfoDTO> rawWeatherList) {
        
    Map<String, HourlyWeatherDTO> hourlyDataMap = new HashMap<>();

        // 1. 원본 DTO 리스트를 반복하면서
        for (WeatherInfoDTO item : rawWeatherList) {
            
            String key = item.getFcstDate() + item.getFcstTime();

            // 2. 맵에 해당 시간대 키가 없으면, 새 HourlyWeatherDTO를 생성하여 넣습니다.
            HourlyWeatherDTO hourlyData = hourlyDataMap.getOrDefault(key, new HourlyWeatherDTO());

            // 💡 핵심 수정 부분: 날짜와 시간을 DTO에 저장
            hourlyData.setFcstDate(item.getFcstDate());
            hourlyData.setFcstTime(item.getFcstTime());

            // 3. category에 따라 HourlyWeatherDTO의 필드를 채웁니다.
            String category = item.getCategory().trim(); // 공백 제거 후 비교
            String value = item.getFcstValue().trim();

            // 값이 비어있으면 건너뜀 (기본값 유지)
            if (value.isEmpty()) {
                continue;
            }

            switch (category) {
                case "TMP":
                    hourlyData.setTemperature(value); 
                    break;
                case "SKY":
                    hourlyData.setSkyStatus(value);
                    break;
                case "REH": // 습도
                    hourlyData.setHumidity(value); // 문자열 그대로 저장
                    break;
                case "PTY": // 강수 형태
                    hourlyData.setPrecipitationType(value);
                    break;
                case "PCP": // 강수량
                    hourlyData.setPrecipitationAmount(value);
                    break;
            }
            
            // key와 DTO를 다시 Map에 저장 (getOrDefault를 썼더라도 put을 해줘야 필드 값이 업데이트됩니다.)
            hourlyDataMap.put(key, hourlyData);
        }
        
        // 4. 최종적으로 Map의 Value들만 List로 추출하여 반환
        // 정렬 로직 추가: 날짜와 시간 순으로 정렬하여 가장 빠른 시간의 예보가 리스트의 첫 번째에 오도록 함
        List<HourlyWeatherDTO> resultList = new ArrayList<>(hourlyDataMap.values());
        resultList.sort((o1, o2) -> {
            int dateCompare = o1.getFcstDate().compareTo(o2.getFcstDate());
            if (dateCompare != 0) return dateCompare;
            return o1.getFcstTime().compareTo(o2.getFcstTime());
        });
        
        return resultList;
    }
}