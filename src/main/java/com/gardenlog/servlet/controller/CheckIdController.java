package com.gardenlog.servlet.controller;

import java.io.IOException;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/checkId.do")
public class CheckIdController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		
		UserDTO udto = new UserDTO();
		udto.setUserid(userid);
		
		UserDAO udao = new UserDAO();
		Boolean result = udao.checkId(udto);
		
		if(result==false) {
			request.setAttribute("idCheckMessage", "사용 가능한 아이디입니다.");
		} else {
			request.setAttribute("idCheckMessage", "이미 사용 중인 아이디입니다.");
		}
		
		request.setAttribute("userid", userid);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/userJoinForm.jsp");
		dispatcher.forward(request, response);
	}
}