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


@WebServlet("/adminlogin.do")
public class AdminLoginController extends HttpServlet {

  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 로그인 폼 JSP로 포워딩
        RequestDispatcher rd = request.getRequestDispatcher("/JSP/admin_Login.jsp");
        rd.forward(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. JSP에서 넘어온 값 확인 (이게 null이면 용의자 2번이 범인!)
	    String id = request.getParameter("adminid"); 
	    String pw = request.getParameter("adminpw");
	    
	    System.out.println("=== 디버깅 시작 ===");
	    System.out.println("1. JSP에서 받은 ID: " + id);
	    System.out.println("2. JSP에서 받은 PW: " + pw);

	    UserDAO dao = new UserDAO();
	    UserDTO user = dao.login(id, pw);

	    // 2. DB 조회 결과 확인 (이게 null이면 아이디/비번 틀림 or DB연결 문제)
	    if (user == null) {
	        System.out.println("3. DAO 결과: user가 NULL입니다. (회원정보 못 찾음)");
	    } else {
	        System.out.println("4. DAO 결과: 유저 찾음! 이름: " + user.getUsername());
	        System.out.println("5. DB에 저장된 Role 값: [" + user.getRole() + "]"); 
	        
	        // 3. 권한 비교 
	        if("admin".equalsIgnoreCase(user.getRole())) { // 대소문자 무시로 변경함
	             System.out.println("🎉 관리자 인증 성공!");
	             HttpSession session = request.getSession();
	             session.setAttribute("loginUser", user);
	             response.sendRedirect(request.getContextPath() + "/ADMIN/admin_Main.jsp");
	             return; // 성공했으면 여기서 끝!
	        } else {
	             System.out.println("😱 관리자 권한 없음! (Role 불일치)");
	        }
	    }

	    // 실패 시 여기로 옴
	    System.out.println("로그인 실패 로직 실행...");
	    response.sendRedirect(request.getContextPath() + "/JSP/admin_Login.jsp?error=true");
	}
}

