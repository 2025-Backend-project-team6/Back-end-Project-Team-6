package com.gardenlog.servlet.controller;

import java.io.IOException;
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

        JournalDAO jdao = new JournalDAO();
        List<JournalDTO> journalList = jdao.selectJournalList(loginUser.getUserid());
        
        request.setAttribute("journalList", journalList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/journalList.jsp");
        dispatcher.forward(request, response);
    }
}