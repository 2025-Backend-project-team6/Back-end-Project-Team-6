package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/mypage.do")
public class MyPageController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDTO fakeUser = new UserDTO();
        fakeUser.setUserid("testUser");
        fakeUser.setUsername("김동양");
        fakeUser.setLevel("새싹 농부");
        fakeUser.setEmail("garden@test.com");
        fakeUser.setPassword("123");
        
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", fakeUser);
        
        request.setAttribute("myCropList", new ArrayList<>()); 

        request.getRequestDispatcher("JSP/mypage.jsp").forward(request, response);
    }
}