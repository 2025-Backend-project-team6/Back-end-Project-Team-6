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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		String keyword = request.getParameter("keyword");
		String selectedGarden = request.getParameter("selectedGarden");
		String selectedCrop = request.getParameter("selectedCrop");
		String recommendedCrop = request.getParameter("recommendedCrop");
		
		CropDAO cdao = new CropDAO();
		MyCropDAO mcdao = new MyCropDAO();
		GardenDAO gdao = new GardenDAO();
		CropDataDAO cddao = new CropDataDAO();
		
		if(selectedCrop!=null) {
			if(keyword!=null && !keyword.isEmpty()) {
				List<CropDataDTO> searchCropList = cddao.searchCropData(keyword);
				
				request.setAttribute("searchCropList", searchCropList);
				request.setAttribute("keyword", keyword);
			}
			
			request.setAttribute("selectedGarden", selectedGarden);
			request.setAttribute("selectedCrop", selectedCrop);
			
			dispatcher = request.getRequestDispatcher("/JSP/addCrop.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if(recommendedCrop!=null) {
			request.setAttribute("selectedGarden", selectedGarden);
			request.setAttribute("recommendedCrop", recommendedCrop);
			
			dispatcher = request.getRequestDispatcher("/JSP/addCrop.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if("addCropPageBtn".equals(action)) {
			List<GardenDTO> userGardenList = gdao.getAllGarden(userid);
			Map<String, String> recommendedMap = new LinkedHashMap<>();
			
			recommendedMap.put("토마토,방울토마토", "🍅 토마토");
			recommendedMap.put("상추", "🥬 상추");
			recommendedMap.put("가지", "🍆 가지");
			recommendedMap.put("고추(보통재배)", "🌶️ 고추");
			recommendedMap.put("오이", "🥒 오이");
			recommendedMap.put("감자", "🥔 감자");
			recommendedMap.put("고구마", "🍠 고구마");
			recommendedMap.put("호박", "🎃 호박");
			recommendedMap.put("딸기(사계성여름재배)", "🍓 딸기");
			recommendedMap.put("당근", "🥕 당근");
				
			session.setAttribute("userGardenList", userGardenList);
			session.setAttribute("recommendedMap", recommendedMap);
			
			response.sendRedirect(request.getContextPath() + "/JSP/addCrop.jsp");
			return ;
		}
		
		if("searchCropBtn".equals(action)) {	
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
		
		if("deleteCropBtn".equals(action)) {
			int gardenid = Integer.parseInt(request.getParameter("gardenid"));
			int id = Integer.parseInt(request.getParameter("id"));
			int result = mcdao.deleteCrop(id);
			
			if(result==1) {
				gdao.minusCropCount(gardenid);
				
				GardenDTO garden = gdao.getDetailGarden(gardenid);
				List<MyCropDTO> updateGardenCropList = mcdao.getGardenCrop(userid, garden.getGardenid());
				
				session.setAttribute("garden", garden);
				session.setAttribute("gardenCropList", updateGardenCropList);
			}
			
			response.sendRedirect(request.getContextPath() + "/mycrop.do");
			return;
			
		}
		
		List<CropDTO> cropCategoryList = cdao.getAllCrops();
		session.setAttribute("cropCategoryList", cropCategoryList);
		
		if("allCrop".equals(category)) {
			session.removeAttribute("findByCategoryList");
			category = null;
		}
		
		if(category!=null) {
			List<MyCropDTO> findByCategoryList = mcdao.findByCategory(userid, category);
			session.setAttribute("findByCategoryList", findByCategoryList);
			
		} else {
			List<MyCropDTO> allMyCropList = mcdao.allMyCrop(userid);
			request.setAttribute("allMyCropList", allMyCropList);
		}
		
		dispatcher = request.getRequestDispatcher("/JSP/myCrop.jsp");
		dispatcher.forward(request, response);	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String userid = loginUser.getUserid();
		String action = request.getParameter("action");
		String selectedGarden = request.getParameter("selectedGarden");
		String selectedCrop = request.getParameter("selectedCrop");
		String keyword = request.getParameter("keyword");
		
		CropDataDAO cddao = new CropDataDAO();
		GardenDAO gdao = new GardenDAO();
		MyCropDAO mcdao = new MyCropDAO();
		
		if("cropSearchBtn".equals(action)) {
			if(keyword==null || keyword.isEmpty()) {
				request.setAttribute("keywordNullMessage", "텃밭에 추가할 작물을 검색하세요.");
				
				dispatcher = request.getRequestDispatcher("/JSP/addCrop.jsp");
				dispatcher.forward(request, response);
				return ;
			}
			
			List<CropDataDTO> searchCropList = cddao.searchCropData(keyword);
			
			if(searchCropList==null || searchCropList.isEmpty()) {
				request.setAttribute("nullMessage", "해당하는 작물이 없습니다.");
			}
			
			request.setAttribute("selectedGarden", selectedGarden);
			request.setAttribute("searchCropList", searchCropList);
			request.setAttribute("keyword", keyword);
			
			dispatcher = request.getRequestDispatcher("/JSP/addCrop.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if("cancel".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/mycrop.do");
			return ;
		}
		
		if("addCrop".equals(action)) {
			String nickname = request.getParameter("nickname");
			String planted_date_Str = request.getParameter("planted_date");
			
			if(selectedGarden==null || selectedGarden.isEmpty() ||
			   selectedCrop==null || selectedCrop.isEmpty() ||
			   nickname==null || nickname.isEmpty() ||
			   planted_date_Str==null || planted_date_Str.isEmpty()) {
				request.setAttribute("nullMessage", "모든 입력값을 채워주세요.");
				
				dispatcher = request.getRequestDispatcher("/JSP/addCrop.jsp");
				dispatcher.forward(request, response);
				return ;
			}
			
			int gardenid = gdao.getGardenid(userid, selectedGarden);
			int cropid = cddao.getCropid(selectedCrop);
			
			Date planted_date = null;
			if(planted_date_Str!=null && !planted_date_Str.isEmpty()) {
				planted_date = Date.valueOf(planted_date_Str);
			}
			
			MyCropDTO mcdto = new MyCropDTO();
			mcdto.setUserid(userid);
			mcdto.setGardenid(gardenid);
			mcdto.setCropid(cropid);
			mcdto.setNickname(nickname);
			mcdto.setPlanted_date(planted_date);
			
			int result = mcdao.addCrop(mcdto);
			if(result==1) {
				int cropCountResult = gdao.plusCropCount(gardenid);
				
				if(cropCountResult==1) {
					session.removeAttribute("userGardenList");
					session.removeAttribute("recommendedList");
					
					response.sendRedirect(request.getContextPath() + "/mycrop.do");
					return ;
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/JSP/addCrop.jsp");
			}
			
		}
	}

}
