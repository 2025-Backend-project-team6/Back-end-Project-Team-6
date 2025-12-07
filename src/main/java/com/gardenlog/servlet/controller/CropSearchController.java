package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.List;

import com.gardenlog.servlet.dto.CropViewDTO; 
import com.gardenlog.servlet.service.CropDataService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/crop_search.do") 
public class CropSearchController extends HttpServlet {
    
    private CropDataService cropDataService;

    public void init() throws ServletException {
        super.init();
        // Service 객체 초기화
        cropDataService = new CropDataService();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        try {
            // 1. Service를 호출하여 전체 작물 목록 DTO 가져오기
            // CropDataService에 getAllCropDataForView() 메소드가 있어야 합니다!
            List<CropViewDTO> cropList = cropDataService.getAllCropDataForView(); 
            
            // 2. 가져온 목록 DTO를 요청 객체에 속성으로 저장
            request.setAttribute("cropList", cropList); 
            
            // 3. 목록 페이지 View (JSP)로 포워딩

            String viewPath = "/JSP/crop_Search.jsp"; 
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("작물 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "작물 목록을 불러오는 중 오류가 발생했습니다.");
        }
        
      
    }
}