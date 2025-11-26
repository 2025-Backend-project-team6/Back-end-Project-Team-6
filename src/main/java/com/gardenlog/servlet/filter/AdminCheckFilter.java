package com.gardenlog.servlet.filter;

import java.io.IOException;

import com.gardenlog.servlet.dto.UserDTO;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AdminCheckFilter implements Filter {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		
		// 1. 형변환
		// doFilter 메소드는 범용성을 위해 파라미터를 ServletRequest 로 받음
		// ServletRequest 에는 getSession 이란 메소드 존재하지 않음
		// -> HttpServletRequest 인 자식으로 형변환 해줘야 세션 정보 확인 가능
		HttpServletRequest request1 = (HttpServletRequest) request;
		HttpServletResponse response1 = (HttpServletResponse) response;
		
		// 2 로그인이나 그 외 파일은 허락
		String uri = request1.getRequestURI();
		if (uri.endsWith("login.jsp") || uri.contains("login.do") ||uri.contains("/css/") || uri.contains("/js/") || uri.contains("/img/")){
			chain.doFilter(request, response);
            return;
		}
		// 3. 세션 가져오기
		HttpSession session = request1.getSession(false);
		
		// 4. 로그인,권한 여부 확인
		boolean isAdmin = false;
		
		if(session != null) { // 로그인 되어있다면
			// 로그인 된 정보 가져오기
			UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
			
			if(loginUser != null && "admin".equals(loginUser.getRole())) { // 로그인을 했고 && 그 사람의 등급이 Admin이라면
				isAdmin = true; // 관리자 여부 true 로
			}
		}
		
		if(isAdmin) {
			chain.doFilter(request, response); // 필터 검증이 완료되었다는 메소드
		} else {
            // ❌ 거절! (메인 페이지나 로그인 페이지로 쫓아냄)
            // "관리자만 접근 가능합니다" 같은 알림을 띄우고 싶다면 여기서 처리 가능
            response1.sendRedirect(request1.getContextPath() + "/admin_Login.jsp");
        }
		
	}
}
