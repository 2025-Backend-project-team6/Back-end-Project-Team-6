package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.util.List;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/user.do")
public class AdminUserController extends HttpServlet {
	
	private UserDAO dao = new UserDAO();
	
	// 단순 조회에 사용
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    String command = request.getParameter("command");
	    String userId = request.getParameter("userId");
	    
	    // 1. 수정 페이지 이동 로직 
	    if ("edit".equals(command) && userId != null) {
	        
	        // 여기서 만든 'user'는 이 if문 안에서만 살아있음
	        UserDTO user = dao.getUserByIdAdmin(userId);
	        request.setAttribute("user", user); 
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/ADMIN/admin_User_Edit.jsp");
	        dispatcher.forward(request, response);
	        
	        return; // ★중요★ 여기서 메소드를 끝내야 아래쪽 코드가 실행 안 됨!
	    }

	    // 2. 상세 페이지 이동 로직
	    if (userId != null && !userId.isEmpty()) {
	        
	        // 위에서 return으로 끝냈기 때문에, 여기서 다시 'user'를 만들어도 됨
	        UserDTO user = dao.getUserByIdAdmin(userId);
	        request.setAttribute("userDetail", user);
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/ADMIN/admin_User_Detail.jsp");
	        dispatcher.forward(request, response);
	    
	    } else {
	        // 3. 목록 페이지 이동 로직
	        List<UserDTO> memberList = dao.selectUserListAdmin(); 
	        request.setAttribute("memberList", memberList);
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/ADMIN/admin_User_List.jsp");
	        dispatcher.forward(request, response);
	    }
	}

	// 수정 경고 정지 탈퇴
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		    
		    // 1. 공통 변수 받기
		    String command = request.getParameter("command");
		    String userId = request.getParameter("userId");
		    
		    // 2. 유효성 검사 (이상하면 목록으로 튕겨냄)
		    if (command == null || userId == null || userId.isEmpty()) {
		        response.sendRedirect(request.getContextPath() + "/admin/user.do");
		        return; 
		    }
		    
		    // DAO 객체 생성
		    UserDAO dao = new UserDAO();
		    int result = 0;
		    
		    // 3. 명령어(command)에 따라 로직 분기
		    if ("update".equals(command)) {
		        // --- [수정 로직] ---
		        System.out.println("=== 🛠️ 회원 정보 수정 ===");
		        
		        String levelStr = request.getParameter("level");
		        String role = request.getParameter("role");
		        String status = request.getParameter("user_status");

		        UserDTO updatedDto = new UserDTO();
		        updatedDto.setUserid(userId);
		        updatedDto.setRole(role);
		        updatedDto.setUser_status(status);
		        
		        if(levelStr != null && !levelStr.isEmpty()) {
		             updatedDto.setLevel(Integer.parseInt(levelStr));
		        }
		        
		        result = dao.updateUserAdmin(updatedDto);
		        
		       

		    } else if ("suspend".equals(command)) {
		        // --- [정지 로직] ---
		        System.out.println("=== 🚫 회원 정지/해제 ===");
		        String status = request.getParameter("status");
		        result = dao.updateUserStatus(userId, status);

		    } else if ("delete".equals(command)) {
		        // --- [삭제 로직] ---
		        System.out.println("=== 🗑️ 회원 삭제 ===");
		        result = dao.deleteUserAdmin(userId);
		    }
		    
		    // 4. 모든 처리가 끝나면 여기서 이동
		    // (어떤 작업을 했든, 결국은 사용자 목록 페이지로 돌아감)
		    response.sendRedirect(request.getContextPath() + "/admin/user.do");
		}

}
