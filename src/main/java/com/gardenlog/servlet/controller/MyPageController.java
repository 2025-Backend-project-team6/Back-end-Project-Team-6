package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.ArrayList;

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
        }

        request.setAttribute("myCropList", new ArrayList<>()); 

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/mypage.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}