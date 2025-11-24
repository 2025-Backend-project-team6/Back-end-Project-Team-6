package com.gardenlog.servlet.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/updateUser.do")
public class UserUpdateController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("JSP/updateMember.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        
        UserDTO updateUser = new UserDTO();
        updateUser.setUserid(loginUser.getUserid()); // ID는 세션에서 가져옴 (변경 불가)
        updateUser.setPassword(password);
        updateUser.setUsername(username);
        updateUser.setEmail(email);

        UserDAO dao = new UserDAO();
        int result = dao.updateUser(updateUser);
        
        if(result == 1) {
            loginUser.setPassword(password);
            loginUser.setUsername(username);
            loginUser.setEmail(email);
            session.setAttribute("loginUser", loginUser);
            
            response.sendRedirect("mypage.do"); 
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}