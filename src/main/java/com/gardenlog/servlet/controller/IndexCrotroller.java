package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.gardenlog.servlet.dao.GardenDAO;
import com.gardenlog.servlet.dao.MyCropDAO;
import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.dto.MyCropDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/index.do")
public class IndexCrotroller extends HttpServlet {
	RequestDispatcher dispatcher = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/JSP/landing.jsp");
            return;
        }
		
		String userid = loginUser.getUserid();
		String selectedGarden = request.getParameter("selectedGarden");
		
		GardenDAO gdao = new GardenDAO();
		MyCropDAO mcdao = new MyCropDAO();
		
		List<GardenDTO> userGardenList = gdao.getAllGarden(userid);
		request.setAttribute("userGardenList", userGardenList);
		
		if (userGardenList == null || userGardenList.isEmpty()) {
            dispatcher = request.getRequestDispatcher("/JSP/index.jsp");
            dispatcher.forward(request, response);
            return;
        }
		
		if (selectedGarden == null || selectedGarden.isEmpty()) {
			selectedGarden = userGardenList.get(0).getGardenname();
        }
		
		GardenDTO currentGarden = null;
        for (GardenDTO g : userGardenList) {
            if (g.getGardenname().equals(selectedGarden)) {
                currentGarden = g;
                break;
            }
        }

        if (currentGarden == null) {
            currentGarden = userGardenList.get(0);
        }
		
        List<MyCropDTO> gardenCropList = mcdao.getGardenCrop(userid, currentGarden.getGardenid());
		
        int gardenid = currentGarden.getGardenid();
        int totalWater = mcdao.getTotalWaterCount(gardenid);
        int totalCrops = mcdao.getCropCount(gardenid);

        LocalDate start = currentGarden.getStart_date().toLocalDate();
        LocalDate today = LocalDate.now();
        long passedDays = ChronoUnit.DAYS.between(start, today);
        
		request.setAttribute("selectedGarden", selectedGarden);
		request.setAttribute("currentGarden", currentGarden);
		request.setAttribute("gardenCropList", gardenCropList);
        request.setAttribute("totalWater", totalWater);
        request.setAttribute("totalCrops", totalCrops);
        request.setAttribute("passedDays", passedDays);
		
		dispatcher = request.getRequestDispatcher("/JSP/index.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

