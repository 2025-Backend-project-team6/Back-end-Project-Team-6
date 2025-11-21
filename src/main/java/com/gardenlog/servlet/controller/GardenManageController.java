package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Date;

import com.gardenlog.servlet.dao.GardenDAO;
import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/gardenmanage.do")
public class GardenManageController extends HttpServlet {
	RequestDispatcher dispatcher = null;
       
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
		String action = request.getParameter("action");		
		
		if("addGarden".equals(action)) {			
			HttpSession session = request.getSession();
			session.setAttribute("userid", "leenayeon"); // 테스트 코드
			String userid = (String) session.getAttribute("userid"); // 테스트 코드
			// UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
			// String userid = loginUser.getUserid();
			
			String gardenname = request.getParameter("gardenname");
			String location = request.getParameter("location");
			String areaStr = request.getParameter("area");
			String start_date_Str = request.getParameter("start_date");
			
			if(gardenname==null || gardenname.isEmpty()) {
				request.setAttribute("nullMessage", "필수 입력값을 채워주세요.");
				
				dispatcher = request.getRequestDispatcher("/JSP/addGarden.jsp");
				dispatcher.forward(request, response);
				return ;
			}
			
			int area=0;
			if(areaStr!=null && !areaStr.isEmpty()) {
				area = Integer.parseInt(areaStr);
			}
			
			Date start_date = null;
			if(start_date_Str!=null && !start_date_Str.isEmpty()) {
				 start_date = Date.valueOf(start_date_Str);
			}
			
			GardenDTO gdto = new GardenDTO();
			gdto.setUserid(userid); // 테스트 코드
			// gdto.setUserid(userid);
			gdto.setGardenname(gardenname);
			gdto.setLocation(location);
			gdto.setArea(area);
			gdto.setStart_date(start_date);
			
			GardenDAO gdao = new GardenDAO();
			int result = gdao.addGarden(gdto);
			
			if(result==1) {
				response.sendRedirect(request.getContextPath() + "/JSP/gardenManage.jsp");
				return ;
			}
		}
		
		// GardenDAO gdao = new GardenDAO();
		// int count = gdao.gardenCount(userid);
		// request.setAttribute("count", count);
				
	}

}
