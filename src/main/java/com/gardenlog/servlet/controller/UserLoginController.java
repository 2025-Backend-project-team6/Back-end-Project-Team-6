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
		RequestDispatcher rd = request.getRequestDispatcher("loginForm.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 폼 파라미터 받기 (email -> userid)
		String userid = request.getParameter("userid"); // "email" -> "userid"
		String password = request.getParameter("password");
		
		// 2. UserDAO 객체 생성 및 login 메소드 호출
		UserDAO dao = new UserDAO();
		UserDTO user = dao.login(userid, password); // email -> userid
		
		// 3. 로그인 결과 처리
		if(user != null) {
			// 3-1. 로그인 성공: 세션 생성 및 메인 페이지(index.jsp)로 이동
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			session.setAttribute("username", user.getUsername()); 
			
			response.sendRedirect("index.jsp"); // 메인 페이지로 리다이렉트
			
		} else {
			// 3-2. 로그인 실패: 에러 메시지를 request에 담아 loginForm.jsp로 다시 포워딩
			// 에러 메시지 텍스트 변경
			request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다."); 
			
			RequestDispatcher rd = request.getRequestDispatcher("loginForm.jsp");
			rd.forward(request, response);
		}
	}
}