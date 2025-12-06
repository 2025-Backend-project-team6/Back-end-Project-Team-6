package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.List;

import com.gardenlog.servlet.dto.CropDetailDTO;
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

    // GET 요청 처리 (상세 페이지 접속 요청)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 요청 파라미터(cropId) 추출 및 유효성 검사
        String cropIdParam = request.getParameter("cropId");
        int cropId = 0;
        
        // ⚠️ 파라미터가 없거나 숫자가 아닐 경우를 대비해 예외 처리
        if (cropIdParam != null && !cropIdParam.isEmpty()) {
            try {
                cropId = Integer.parseInt(cropIdParam);
            } catch (NumberFormatException e) {
                // 숫자가 아닌 경우 처리: 오류 페이지 또는 목록 페이지로 리다이렉트
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 작물 ID 형식입니다.");
                return;
            }
        } else {
        	
        	try {
                // 1. Service를 호출하여 전체 작물 목록 DTO 가져오기
                // CropDataService에 getAllCropDataForView() 메소드가 있어야 합니다!
                List<CropViewDTO> cropList = cropDataService.getAllCropDataForView(); 
                
                // 2. 가져온 목록 DTO를 요청 객체에 속성으로 저장
                request.setAttribute("cropList", cropList); 
                
                // 3. 목록 페이지 View (JSP)로 포워딩
                // ⚠️ 센의 목록 JSP 파일 경로로 정확하게 수정하세요!
                String viewPath = "/JSP/crop_Search.jsp"; 
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
                dispatcher.forward(request, response);
                return; // 목록 처리 후 종료
            } catch (Exception e) {
                System.err.println("작물 목록 조회 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "작물 목록을 불러오는 중 오류가 발생했습니다.");
                return;
            }
       
        }


        // 2. Service를 호출하여 상세 정보 DTO 가져오기
        CropDetailDTO detailDTO = cropDataService.getCropDetail(cropId);
        
        // 3. 데이터 유효성 검사
        if (detailDTO == null) {
            // 해당 ID의 작물 정보가 DB에 없는 경우 처리
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청하신 작물 정보를 찾을 수 없습니다.");
            return;
        }

        // 4. 가져온 상세 DTO를 요청 객체(request)에 속성으로 저장
        // JSP에서 "cropDetail"이라는 이름으로 사용합니다.
        request.setAttribute("cropDetail", detailDTO); 

        // 5. 상세 페이지 View (JSP)로 포워딩
        String viewPath = "/JSP/crop_Detail.jsp"; 
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}