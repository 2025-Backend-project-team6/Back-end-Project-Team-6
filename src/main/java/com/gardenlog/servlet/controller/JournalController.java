package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gardenlog.servlet.dao.JournalDAO;
import com.gardenlog.servlet.dto.JournalDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/journal.do")
public class JournalController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        if(loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/JSP/userLoginForm.jsp");
            return;
        }
        
        String userid = loginUser.getUserid();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");

        if(yearParam != null && monthParam != null) {
            try {
                year = Integer.parseInt(yearParam);
                month = Integer.parseInt(monthParam);
                if (month < 1) { month = 12; year--; }
                if (month > 12) { month = 1; year++; }
            } catch (Exception e) {}
        }
        
        cal.set(year, month - 1, 1);
        int startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        JournalDAO jdao = new JournalDAO();

        List<JournalDTO> journalList = jdao.selectJournalList(userid);

        List<Integer> logDates = jdao.getLogDates(userid, year, month);
        
        int monthlyTotal = jdao.getMonthlyLogCount(userid, year, month);
        int waterCount = jdao.getKeywordCount(userid, year, month, "물");
        int harvestCount = jdao.getKeywordCount(userid, year, month, "수확");
        String topCrop = jdao.getTopCropName(userid, year, month);

        // 달력 정보
        request.setAttribute("currentYear", year);
        request.setAttribute("currentMonth", month);
        request.setAttribute("startDayOfWeek", startDayOfWeek);
        request.setAttribute("lastDay", lastDay);
        request.setAttribute("logDates", logDates);
        
        // 인사이트 정보
        request.setAttribute("monthlyTotal", monthlyTotal);
        request.setAttribute("waterCount", waterCount);
        request.setAttribute("harvestCount", harvestCount);
        request.setAttribute("topCrop", topCrop);
        
        // 일지 리스트
        request.setAttribute("journalList", journalList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/journalList.jsp");
        dispatcher.forward(request, response);
    }
}