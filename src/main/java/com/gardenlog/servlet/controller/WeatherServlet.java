package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.gardenlog.servlet.dto.HourlyWeatherDTO;
import com.gardenlog.servlet.service.WeatherService; 

@WebServlet("/weather.do")
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    // Service 인스턴스 (Controller는 Service를 호출합니다.)
    private final WeatherService weatherService = new WeatherService(); 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	System.out.println("--- [DEBUG] WeatherController.doGet 시작 ---");

        // 1. 초기 데이터 설정 및 요청 파라미터 획득 (Controller 역할)
        List<HourlyWeatherDTO> weatherList = Collections.emptyList();
        String locationName = "위치 정보 없음";
        
        // ⚠️ 현재는 Geolocation API를 사용하지 않으므로 하드코딩된 좌표를 사용합니다. 
        // 실제 운영 시에는 사용자 주소나 기본 설정 위치를 사용해야 합니다.
        double defaultLat = 37.4939; // 동양미래대 근처 위도
        double defaultLon = 126.8530; // 동양미래대 근처 경도
        
        // 2. Service 호출 및 데이터 처리 (Controller 역할)
        try {
            // Service는 위도/경도를 받아 API 호출부터 DTO 가공까지 모두 처리합니다.
            weatherList = weatherService.getWeatherForecast(defaultLat, defaultLon);
            
            // 위치 정보는 Service에서 역지오코딩 등으로 가져와야 하지만, 현재는 Controller에서 임시 설정
            // TODO: FarmSearchServlet.java의 getRegionFromCoords() 로직을 활용하여 위치명을 가져오는 Service 로직을 추가하면 좋습니다.
            locationName = "서울특별시 구로구 (임시)"; 
            
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 빈 리스트와 오류 메시지를 View로 전달
            locationName = "날씨 정보를 불러오는 중 오류 발생";
            weatherList = Collections.emptyList();
        }
        
        // 3. 데이터를 Request에 담아 View로 전달 (Controller 역할)
        // index.jsp에서 사용하는 EL/JSTL 속성 이름에 맞춰 담습니다.
        request.setAttribute("weatherDataList", weatherList);
        request.setAttribute("locationName", locationName);
        
        System.out.println("--- [DEBUG] WeatherController.doGet 완료 및 index.jsp로 포워딩 ---");
        
        // 4. View(JSP)로 포워딩 (Controller 역할)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/index.jsp");
        dispatcher.forward(request, response);
    }
}