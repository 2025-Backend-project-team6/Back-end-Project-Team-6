package com.gardenlog.servlet.controller;

import java.io.IOException;

// 'javax' -> 'jakarta'로 모두 변경
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
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/userLoginForm.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		
		if("testUser".equals(userid) && "123".equals(password)) {
			UserDTO fakeUser = new UserDTO();
			fakeUser.setUserid("testUser");
			fakeUser.setPassword("123");
			fakeUser.setUsername("김동양");
			fakeUser.setEmail("garden@test.com");
			fakeUser.setLevel(1);
			fakeUser.setRole("USER");
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", fakeUser);
			
			response.sendRedirect(request.getContextPath() + "/JSP/index.jsp");
			
		} else {
			request.setAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
			
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/userLoginForm.jsp");
			rd.forward(request, response);
		}
		
		//후에 db 연결하면 주석 풀 예정
//		UserDAO dao = new UserDAO();
//		UserDTO user = dao.login(userid, password);
//		
//		if(user != null) {
//			HttpSession session = request.getSession();
//			session.setAttribute("loginUser", user);
//			session.setAttribute("username", user.getUsername()); 
//			
//			response.sendRedirect("index.jsp");
//			
//		} else {
//			request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다."); 
//			
//			RequestDispatcher rd = request.getRequestDispatcher("loginForm.jsp");
//			rd.forward(request, response);
//		}
	}
}