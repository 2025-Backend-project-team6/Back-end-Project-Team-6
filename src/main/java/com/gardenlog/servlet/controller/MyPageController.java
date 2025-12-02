package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/mypage.do")
public class MyPageController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        if(loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/JSP/userLoginForm.jsp");
            return;
        }

        UserDAO udao = new UserDAO();
        UserDTO freshUser = udao.getUser(loginUser.getUserid());
        
        if(freshUser != null) {
            session.setAttribute("loginUser", freshUser);
        } else {
            freshUser = loginUser;
        }
        
        String userid = freshUser.getUserid();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");

        if(yearParam != null && monthParam != null) {
            try {
                year = Integer.parseInt(yearParam);
                month = Integer.parseInt(monthParam);

                if (month < 1) {
                    month = 12;
                    year--;
                } else if (month > 12) {
                    month = 1;
                    year++;
                }
            } catch (NumberFormatException e) {

            }
        }
        
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        
        int startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        VisitDAO vdao = new VisitDAO();
        List<Integer> visitDays = vdao.getVisitDays(userid, year, month);
        int totalVisitCount = vdao.getTotalVisitCount(userid);
        
        // 작물 (임시 0)
        int cropCount = 0; 
        
        JournalDAO jdao = new JournalDAO();
        int journalCount = jdao.getJournalCount(userid);
        
        int currentLevel = freshUser.getLevel();
        int calculatedLevel = 1;
        int nextLevelTarget = 5;
        
        // 0~4개(Lv1), 5~9개(Lv2), 10개~(Lv3)
        if (journalCount >= 10) {
            calculatedLevel = 3;
            nextLevelTarget = 10;
        } else if (journalCount >= 5) {
            calculatedLevel = 2;
            nextLevelTarget = 10;
        } else {
            calculatedLevel = 1;
            nextLevelTarget = 5;
        }

        if (calculatedLevel > currentLevel) {
            udao.updateLevel(userid, calculatedLevel);
            freshUser.setLevel(calculatedLevel);
            session.setAttribute("loginUser", freshUser);
        }

        int remainingLogs = nextLevelTarget - journalCount;
        if (remainingLogs < 0) remainingLogs = 0;

        int progressPercent = 0;
        if (calculatedLevel < 3) {
             progressPercent = (int)((double)journalCount / nextLevelTarget * 100);
        } else {
             progressPercent = 100;
        }

        // (달력 정보)
        request.setAttribute("currentYear", year);
        request.setAttribute("currentMonth", month);
        request.setAttribute("startDayOfWeek", startDayOfWeek);
        request.setAttribute("lastDay", lastDay);
        request.setAttribute("visitDays", visitDays);
        request.setAttribute("visitCount", visitDays.size());
        
        // (통계 정보)
        request.setAttribute("cropCount", cropCount);
        request.setAttribute("totalVisitCount", totalVisitCount);
        request.setAttribute("journalCount", journalCount);
        
        // (레벨 정보)
        request.setAttribute("remainingLogs", remainingLogs);
        request.setAttribute("progressPercent", progressPercent);
        
        // (작물 리스트 - 빈 리스트)
        request.setAttribute("myCropList", new ArrayList<>()); 

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/mypage.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}