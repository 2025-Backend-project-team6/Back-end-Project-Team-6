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
import com.gardenlog.servlet.dao.MyCropDAO;
import com.gardenlog.servlet.dto.CropDTO;
import com.gardenlog.servlet.dto.MyCropDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/mycrop.do")
public class MyCropController extends HttpServlet {
	RequestDispatcher dispatcher = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String userid = loginUser.getUserid();
		String action = request.getParameter("action");
		
		CropDAO cdao = new CropDAO();
		MyCropDAO mcdao = new MyCropDAO();
		
		if("addCropBtn".equals(action)) {
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
		request.setAttribute("cropCategoryList", cropCategoryList);
		
		List<MyCropDTO> allMyCropList = mcdao.allMyCrop(userid);
		request.setAttribute("allMyCropList", allMyCropList);
		
		dispatcher = request.getRequestDispatcher("/JSP/myCrop.jsp");
		dispatcher.forward(request, response);	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
