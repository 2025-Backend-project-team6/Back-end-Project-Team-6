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
	RequestDispatcher dispatcher = null;
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String agreeRequired = request.getParameter("agreeRequired");
		
		if(userid==null || userid.isEmpty() || 
		   password==null || password.isEmpty() || 
		   username==null || username.isEmpty() || 
		   email==null || email.isEmpty()) {
			
			request.setAttribute("userid", userid);
			request.setAttribute("password", password);
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			request.setAttribute("joinCheckMessage", "모든 필수 입력칸을 채워주세요.");
			
			dispatcher = request.getRequestDispatcher("/JSP/userJoinForm.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if(agreeRequired==null) {
			request.setAttribute("userid", userid);
			request.setAttribute("password", password);
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			
			request.setAttribute("joinCheckMessage", "필수 약관에 동의해야 회원가입에 가능합니다.");
			dispatcher = request.getRequestDispatcher("/JSP/userJoinForm.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		UserDTO udto = new UserDTO();
		udto.setUserid(userid);
		udto.setPassword(password);
		udto.setUsername(username);
		udto.setEmail(email);
		
		UserDAO udao = new UserDAO();
		int result = udao.userJoin(udto);
		if (result==1) {
			dispatcher = request.getRequestDispatcher("/JSP/userLoginForm.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}