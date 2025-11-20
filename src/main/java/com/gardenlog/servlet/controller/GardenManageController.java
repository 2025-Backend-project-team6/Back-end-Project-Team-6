package com.gardenlog.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.gardenlog.servlet.dao.GardenDAO;
import com.gardenlog.servlet.dto.GardenDTO;

@WebServlet("/gardenmanage.do")
public class GardenManageController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("addGarden".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/JSP/addGarden.jsp");
			return ;
		}
		if("viewGarden".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/JSP/viewGarden.jsp");
			return ;
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		
		GardenDAO gdao = new GardenDAO();
		int count = gdao.checkCropCount(userid);
		request.setAttribute("count", count);
	}

}
