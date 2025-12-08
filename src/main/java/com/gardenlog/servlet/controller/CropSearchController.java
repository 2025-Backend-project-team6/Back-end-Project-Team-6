package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.gardenlog.servlet.dao.CropDAO;
import com.gardenlog.servlet.dao.CropDataDAO;
import com.gardenlog.servlet.dto.CropDTO;
import com.gardenlog.servlet.dto.CropDataDTO;

@WebServlet("/cropsearch.do")
public class CropSearchController extends HttpServlet {
	RequestDispatcher dispatcher = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String categorySelect_Str = request.getParameter("categorySelect");
		
		CropDAO cdao = new CropDAO();
		CropDataDAO cddao = new CropDataDAO();
		
		List<CropDataDTO> allCropList = cddao.getAllCrop();		
		List<CropDTO> categoryList = cdao.getAllCrops();	
		
		request.setAttribute("allCropList", allCropList);
		request.setAttribute("categoryList", categoryList);
		
		if("cropSearchBtn".equals(action)) {
			String keyword = request.getParameter("keyword");
	
			List<CropDataDTO> searchCropList = cddao.searchCropData(keyword);
			
			if(searchCropList==null || searchCropList.isEmpty()) {
				request.setAttribute("SearchCropnullMessage", "해당하는 작물이 없습니다.");
			}
			
			request.setAttribute("searchCropList", searchCropList);
			request.setAttribute("keyword", keyword);
			
			dispatcher = request.getRequestDispatcher("/JSP/cropSearch.jsp");
			dispatcher.forward(request, response);
			return ;	
		}
		
		if("category".equals(action) &&
	            categorySelect_Str != null &&
	            !categorySelect_Str.equals("")) {
			int categorySelect = Integer.parseInt(categorySelect_Str);
			List<CropDataDTO> selectCategoryList = cddao.getCropsByCategory(categorySelect);
			
			request.setAttribute("selectCategoryList", selectCategoryList);
			request.setAttribute("selectedCategory", categorySelect);
			
			dispatcher = request.getRequestDispatcher("/JSP/cropSearch.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if("level".equals(action)) {
			String levelSelect = request.getParameter("levelSelect");
			
			request.setAttribute("selectCategoryList", null);
			request.setAttribute("selectedCategory", null);
			request.setAttribute("searchCropList", null);
			
			if(levelSelect!=null && !levelSelect.equals("")) {
				List<CropDataDTO> levelList = cddao.getCropByLevel(levelSelect);
				
				request.setAttribute("levelList", levelList);
				request.setAttribute("selectedLevel", levelSelect);
			}
			
			dispatcher = request.getRequestDispatcher("/JSP/cropSearch.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if("detailCropBtn".equals(action)) {
			dispatcher = request.getRequestDispatcher("/JSP/detailCrop.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		dispatcher = request.getRequestDispatcher("/JSP/cropSearch.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
