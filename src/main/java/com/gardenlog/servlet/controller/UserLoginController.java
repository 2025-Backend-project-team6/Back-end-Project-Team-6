package com.gardenlog.servlet.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/login.do")
public class UserLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserLoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginStatus = request.getParameter("loginStatus");
		
		if("logoutBtn".equals(loginStatus)) {
			session.invalidate();
			
			response.sendRedirect(request.getContextPath() + "/JSP/landing.jsp");
			return ;
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/userLoginForm.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		UserDAO dao = new UserDAO();
		UserDTO user = dao.login(userid, password);
		
		if(user != null) {
			// 2. 로그인 성공
			if ("SUSPENDED".equals(user.getUser_status())) {
				request.setAttribute("suspendMessage", "정지된 계정입니다. 관리자에게 문의하세요.");
				
				RequestDispatcher rd = request.getRequestDispatcher("/JSP/user_Suspended.jsp");
				rd.forward(request, response);
			} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			
		    com.gardenlog.servlet.dao.VisitDAO vdao = new com.gardenlog.servlet.dao.VisitDAO();
		    vdao.addVisit(user.getUserid());

			response.sendRedirect(request.getContextPath() + "/JSP/index.jsp");
			}
		} else {
			request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다."); 
			
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/userLoginForm.jsp");
			rd.forward(request, response);
		}
	}
}