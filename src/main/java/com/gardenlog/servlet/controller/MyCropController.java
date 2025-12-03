package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.gardenlog.servlet.dao.CropDAO;
import com.gardenlog.servlet.dao.CropDataDAO;
import com.gardenlog.servlet.dao.CropFileDAO;
import com.gardenlog.servlet.dao.CropInfoDAO;
import com.gardenlog.servlet.dao.GardenDAO;
import com.gardenlog.servlet.dao.MyCropDAO;
import com.gardenlog.servlet.dto.CropDTO;
import com.gardenlog.servlet.dto.CropDataDTO;
import com.gardenlog.servlet.dto.CropInfoDTO;
import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.dto.MyCropDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/mycrop.do")
public class MyCropController extends HttpServlet {
	RequestDispatcher dispatcher = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
		if(loginUser==null) {
			response.sendRedirect(request.getContextPath() + "/JSP/userLoginForm.jsp");
			return ;
		}
		
		String userid = loginUser.getUserid();
		String action = request.getParameter("action");
		String category = request.getParameter("category");
		
		CropDAO cdao = new CropDAO();
		MyCropDAO mcdao = new MyCropDAO();
		GardenDAO gdao = new GardenDAO();
		

		
		if("addCropBtn".equals(action)) {
			List<GardenDTO> UserGardenList = gdao.getAllGarden(userid);
			session.setAttribute("UserGardenList", UserGardenList);
			
			response.sendRedirect(request.getContextPath() + "/JSP/addCrop.jsp");
			return ;
		}
		
		if("searchCropBtn".equals(action)) {
			String keyword = request.getParameter("keyword");
			
			List<MyCropDTO> searchMyCropList = mcdao.searchMyCrop(userid, keyword);
			
			if(searchMyCropList==null || searchMyCropList.isEmpty()) {
				request.setAttribute("searchNullMessage", "해당하는 작물이 없습니다.");
			}
			
			request.setAttribute("searchMyCropList", searchMyCropList);
			request.setAttribute("keyword", keyword);
			
			dispatcher = request.getRequestDispatcher("/JSP/myCrop.jsp");
			dispatcher.forward(request, response);
			return ;	
		}
		
		List<CropDTO> cropCategoryList = cdao.getAllCrops();
		session.setAttribute("cropCategoryList", cropCategoryList);
		
		if("allCrop".equals(category)) {
			category = null;
		}
		
		if(category!=null) {
			List<MyCropDTO> findByCategoryList = mcdao.findByCategory(userid, category);
			request.setAttribute("findByCategoryList", findByCategoryList);
			
		} else {
			List<MyCropDTO> allMyCropList = mcdao.allMyCrop(userid);
			session.setAttribute("allMyCropList", allMyCropList);
		}
		
		dispatcher = request.getRequestDispatcher("/JSP/myCrop.jsp");
		dispatcher.forward(request, response);	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String userid = loginUser.getUserid();
		String action = request.getParameter("action");
		
		if("cropSearchBtn".equals(action)) {
			String keyword = request.getParameter("keyword");
			
			CropDataDAO cddao = new CropDataDAO();
			List<CropDataDTO> searchCropList = cddao.searchCropData(keyword);
			
			if(searchCropList==null || searchCropList.isEmpty()) {
				request.setAttribute("nullMessage", "해당하는 작물이 없습니다.");
			}
			
			request.setAttribute("searchCropList", searchCropList);
			request.setAttribute("keyword", keyword);
			
			dispatcher = request.getRequestDispatcher("/JSP/addCrop.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
	}

}
