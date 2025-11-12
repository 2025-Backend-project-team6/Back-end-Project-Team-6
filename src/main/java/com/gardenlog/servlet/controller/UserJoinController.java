package com.gardenlog.servlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/join.do")
public class UserJoinController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		UserDTO udto = new UserDTO();
		udto.setUserid(userid);
		udto.setPassword(password);
		udto.setUsername(username);
		udto.setEmail(email);
		
		UserDAO udao = new UserDAO();
		int result = udao.userJoin(udto);
		if (result==1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/loginForm.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
