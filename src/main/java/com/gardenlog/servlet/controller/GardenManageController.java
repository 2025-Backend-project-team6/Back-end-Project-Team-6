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
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gardenlog.servlet.dao.GardenActivityDAO;
import com.gardenlog.servlet.dao.GardenDAO;
import com.gardenlog.servlet.dao.MyCropDAO;
import com.gardenlog.servlet.dto.GardenActivityDTO;
import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.dto.MyCropDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/gardenmanage.do")
public class GardenManageController extends HttpServlet {
	RequestDispatcher dispatcher = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String userid = loginUser.getUserid();
		String action = request.getParameter("action");
		
		GardenDAO gdao = new GardenDAO();
		MyCropDAO mcdao = new MyCropDAO();
		
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
			
			LocalDate today = LocalDate.now();
			int year = today.getYear();
			int month = today.getMonthValue();
			
			YearMonth ym = YearMonth.of(year, month);
		    int lastDay = ym.lengthOfMonth();
			
			GardenDTO garden = gdao.getDetailGarden(gardenid);
			List<MyCropDTO> gardenCropList = mcdao.getGardenCrop(userid, gardenid);		
			
			GardenActivityDAO gadao = new GardenActivityDAO();
			List<GardenActivityDTO> activityList = gadao.getMonthlyActivity(userid, gardenid, year, month);
			
			Map<Integer, List<GardenActivityDTO>> calendarMap = new HashMap<>();
			
			for(GardenActivityDTO activity: activityList) {
				int day = activity.getActivity_date().toInstant().atZone(ZoneId.systemDefault()).getDayOfMonth();
				
				calendarMap.putIfAbsent(day, new ArrayList<>());
			    calendarMap.get(day).add(activity);
			}
			
			List<Integer> days = new ArrayList<>();
		    for (int d = 1; d <= lastDay; d++) {
		        days.add(d);
		    }
			
			session.setAttribute("garden", garden);
			session.setAttribute("gardenCropList", gardenCropList);
			request.setAttribute("calendarMap", calendarMap);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("days", days);
			
			dispatcher = request.getRequestDispatcher("/JSP/detailGarden.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if("water".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = mcdao.plusWater(id);
			
			if(result==1) {
				Map<Integer, Boolean> waterStatus = new HashMap<>();
				waterStatus.put(id, true);
				
				request.setAttribute("waterStatus", waterStatus);	
			}

			GardenDTO garden = (GardenDTO)session.getAttribute("garden");
			List<MyCropDTO> updateGardenCropList = mcdao.getGardenCrop(userid, garden.getGardenid());
			
			session.setAttribute("gardenCropList", updateGardenCropList);
			
			dispatcher = request.getRequestDispatcher("/JSP/detailGarden.jsp");
			dispatcher.forward(request, response);
			return ;	
		}
		
		if("allWater".equals(action)) {
			GardenDTO garden = (GardenDTO)session.getAttribute("garden");
			List<MyCropDTO> CropList = (List<MyCropDTO>)session.getAttribute("gardenCropList");
			Map<Integer, Boolean> waterStatus = new HashMap<>();
			
			for(MyCropDTO crop: CropList) {
				if(crop.getLast_watered_at() != null &&
				   crop.getLast_watered_at().toString().equals(java.time.LocalDate.now().toString())) {
						waterStatus.put(crop.getId(), true);
						continue;
				}
				mcdao.plusWater(crop.getId());
				waterStatus.put(crop.getId(), true);
			}
			
			List<MyCropDTO> updateGardenCropList = mcdao.getGardenCrop(userid, garden.getGardenid());
			session.setAttribute("gardenCropList", updateGardenCropList);
			request.setAttribute("waterStatus", waterStatus);
			
			dispatcher = request.getRequestDispatcher("/JSP/detailGarden.jsp");
			dispatcher.forward(request, response);
			return ;	
		}
		
		if("deleteCrop".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = mcdao.deleteCrop(id);
			
			if(result==1) {
				int gardenid = Integer.parseInt(request.getParameter("gardenid"));
				
				gdao.minusCropCount(gardenid);
				
				GardenDTO garden = gdao.getDetailGarden(gardenid);
				List<MyCropDTO> updateGardenCropList = mcdao.getGardenCrop(userid, garden.getGardenid());
				
				session.setAttribute("garden", garden);
				session.setAttribute("gardenCropList", updateGardenCropList);
			}
			
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
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String userid = loginUser.getUserid();
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