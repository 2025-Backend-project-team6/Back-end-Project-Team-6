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
public class AdminUserServlet extends HttpServlet {
	
	private UserDAO dao = new UserDAO();
	
	// 단순 조회에 사용
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		
		if (userId != null && !userId.isEmpty()) {
	        //ID가 있으면 상세 페이지로 이동
			UserDTO user = dao.getUserByIdAdmin(userId);
			request.setAttribute("userDetail", user);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ADMIN/admin_User_Detail.jsp");
	        dispatcher.forward(request, response);
		
		} else {
	        // ID가 없으면 목록 페이지로 이동
	        
	        List<UserDTO> memberList = dao.selectUserListAdmin(); 
	        request.setAttribute("memberList", memberList);
	        
	     
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/ADMIN/admin_User_List.jsp");
	        dispatcher.forward(request, response);
	    }
	}

	// 수정 경고 정지 탈퇴
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter("command");
	    String userId = request.getParameter("userId");
	    int result = 0;
	    
	    if (command == null || userId == null || userId.isEmpty()) {
	        // command나 ID가 없으면 처리 불가, 목록으로 리다이렉트
	        response.sendRedirect(request.getContextPath() + "/admin/user.do");
	        return;
	    }
	    
	    if ("update".equals(command)) {
	        // 회원 정보 수정 로직 
	        
	        //JSP 폼에서 넘어온 모든 파라미터를 받아서 새로운 DTO
	        UserDTO updatedDto = new UserDTO();
	        
	        updatedDto.setUserid(userId); // WHERE 절에 사용될 ID
	        updatedDto.setPassword(request.getParameter("password")); 
	        updatedDto.setUsername(request.getParameter("username"));
	        updatedDto.setEmail(request.getParameter("email"));
	        updatedDto.setLevel(Integer.parseInt(request.getParameter("level")));
	        updatedDto.setRole(request.getParameter("role"));
	        updatedDto.setUser_status(request.getParameter("user_status"));
	        
	        // DAO 호출: DTO 전체 업데이트
	        result = dao.updateUserAdmin(updatedDto);
	        
	    } else if ("suspend".equals(command)) {
	        // 계정 정지/활성 로직
	        String status = request.getParameter("status"); // 'SUSPENDED' 또는 'ACTIVE'
	        result = dao.updateUserStatus(userId, status); // (DAO의 updateUserStatus 메소드를 호출합니다)

	    }  else if ("delete".equals(command)) {
	        // 회원 삭제 로직
	        result = dao.deleteUserAdmin(userId); 
	        
	    }
	    response.sendRedirect(request.getContextPath() + "/admin/user.do");
	
	}

}
