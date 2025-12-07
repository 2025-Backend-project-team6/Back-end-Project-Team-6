package com.gardenlog.servlet.controller;

import java.io.IOException;

import com.gardenlog.servlet.dto.CropDetailDTO;
import com.gardenlog.servlet.service.CropDataService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/crop_detail.do") 
public class CropDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CropDataService cropDataService;

    public void init() throws ServletException {
        super.init();
        cropDataService = new CropDataService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. cropId 파라미터 추출
        String cropIdParam = request.getParameter("cropId");
        if (cropIdParam == null || cropIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "작물 ID가 필요합니다.");
            return;
        }
        int cropId = Integer.parseInt(cropIdParam);

        // 2. Service 호출 (상세 DTO 가져오기)
        CropDetailDTO detailDTO = cropDataService.getCropDetail(cropId);
        
        if (detailDTO == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청하신 작물 정보를 찾을 수 없습니다.");
            return;
        }

        // 3. JSP로 포워딩
        request.setAttribute("cropDetail", detailDTO); 
  
   
        RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/crop_Detail.jsp");
        dispatcher.forward(request, response);
    }
}