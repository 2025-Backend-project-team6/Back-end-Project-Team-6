package com.gardenlog.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;


@WebServlet("/admin/login.do")
public class AdminLoginController extends HttpServlet {

  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("adminid");
		String pw = request.getParameter("adminpw");
		
		UserDAO dao = new UserDAO();
		UserDTO user = dao.login(id,pw);
		
		if(user !=null && "admin".equals(user.getRole())) {
			System.out.println("관리자 로그인 성공: " + user.getUsername());
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			response.sendRedirect(request.getContextPath() + "/ADMIN/admin_Main.jsp");
		} else {
			System.out.println("로그인 실패: 정보가 없거나 관리자 권한 없음");
			response.sendRedirect(request.getContextPath() + "/ADMIN/admin_Login.jsp?error=true");
		}
		}
	}


