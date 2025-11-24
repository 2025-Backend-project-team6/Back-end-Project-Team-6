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
import java.util.List;

import com.gardenlog.servlet.dao.GardenDAO;
import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/gardenmanage.do")
public class GardenManageController extends HttpServlet {
	RequestDispatcher dispatcher = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("userid", "testuser"); // 테스트 코드
		String userid = (String)session.getAttribute("userid"); // 테스트 코드
		// UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		// String userid = loginUser.getUserid();
		String action = request.getParameter("action");
		
		GardenDAO gdao = new GardenDAO();
		
		if("addGardenBtn".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/JSP/addGarden.jsp");
			return ;
		}
		
		if("viewGardenBtn".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/JSP/viewGarden.jsp");
			return ;
		}
		
		if("searchGardenBtn".equals(action)) {
			String keyword = request.getParameter("keyword");
			List<GardenDTO> searchGardenList = gdao.searchGarden(userid, keyword);
			
			if(searchGardenList==null || searchGardenList.isEmpty()) {
				request.setAttribute("nullMessage", "해당하는 텃밭이 없습니다.");			
			}
			
			request.setAttribute("searchGardenList", searchGardenList);
			request.setAttribute("keyword", keyword);
			
			setTotalInfo(request, userid, gdao);
			
			dispatcher = request.getRequestDispatcher("/JSP/gardenManage.jsp");
			dispatcher.forward(request, response);
			return ;
		} 	
		
		if("detailGardenBtn".equals(action)) {			
			int gardenid = Integer.parseInt(request.getParameter("gardenid"));
			GardenDTO gdto = gdao.getDetailGarden(gardenid);
			
			request.setAttribute("garden", gdto);
			
			dispatcher = request.getRequestDispatcher("/JSP/detailGarden.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		List<GardenDTO> userGardenList = gdao.getAllGarden(userid);
		
		request.setAttribute("userGardenList", userGardenList);
		setTotalInfo(request, userid, gdao);
		
		dispatcher = request.getRequestDispatcher("/JSP/gardenManage.jsp");
		dispatcher.forward(request, response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("userid", "testuser"); // 테스트 코드
		String userid = (String) session.getAttribute("userid"); // 테스트 코드
		// UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		// String userid = loginUser.getUserid();
		String action = request.getParameter("action");	
		
		GardenDTO gdto = new GardenDTO();
		
		if("cancel".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/gardenmanage.do");
			return ;
		}
				
		if("addGarden".equals(action)) {			
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
			
			gdto.setUserid(userid);
			gdto.setGardenname(gardenname);
			gdto.setLocation(location);
			gdto.setArea(area);
			gdto.setStart_date(start_date);
			
			GardenDAO gdao = new GardenDAO();
			int result = gdao.addGarden(gdto);
			
			if(result==1) {
				response.sendRedirect(request.getContextPath() + "/gardenmanage.do");
				return ;
			}
		}		
	}
	
	
	private void setTotalInfo(HttpServletRequest request, String userid, GardenDAO gdao) {
		List<GardenDTO> userGardenList = gdao.getAllGarden(userid);
		int totalArea = 0;
		int totalCropCount = 0;
		
		for(GardenDTO g: userGardenList) {
			totalArea += g.getArea();
			totalCropCount += g.getCrop_count();
		}
		
		request.setAttribute("totalArea", totalArea);
		request.setAttribute("totalCropCount", totalCropCount);
		request.setAttribute("totalGardenCount", userGardenList.size());
	}
}
