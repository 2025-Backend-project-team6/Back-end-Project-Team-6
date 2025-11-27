package com.gardenlog.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gardenlog.servlet.dao.UserDAO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/updateUser.do")
public class UserUpdateController extends HttpServlet {
    
    // 수정 폼 보여주기 (GET 요청)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 로그인 체크 (로그인 안 했으면 폼도 못 봄)
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") == null) {
             response.sendRedirect(request.getContextPath() + "/JSP/userLoginForm.jsp");
             return;
        }

        // 2. [중요 수정] 마이페이지가 아니라 '수정 폼(updateMember.jsp)'으로 보내야 함!
        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/updateUser.jsp");
        dispatcher.forward(request, response);
    }

    // 수정 로직 처리 (POST 요청 - DB 반영)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8"); 
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        // 1. 파라미터 받기
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        
        PrintWriter out = response.getWriter();

        // 2. 비밀번호 일치 확인
        if (!password.equals(passwordConfirm)) {
            out.println("<script>");
            out.println("alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');");
            out.println("history.back();"); 
            out.println("</script>");
            return;
        }
        
        // 3. DTO 생성
        UserDTO updateUser = new UserDTO();
        updateUser.setUserid(loginUser.getUserid()); // 아이디는 세션에서 (PK니까 변경 불가)
        updateUser.setUsername(username);
        updateUser.setEmail(email);
        updateUser.setPassword(password);

        // 4. DB 업데이트 호출
        UserDAO dao = new UserDAO();
        int result = dao.updateUser(updateUser); // DAO에 이 메소드가 있어야 해! (2단계 참고)
        
        // 5. 결과 처리
        if(result == 1) {
            // 성공 시: DB에서 최신 정보 다시 가져와서 세션 갱신 (가장 안전한 방법)
            UserDTO freshUser = dao.getUser(loginUser.getUserid());
            session.setAttribute("loginUser", freshUser);
            
            out.println("<script>");
            out.println("alert('회원 정보가 성공적으로 수정되었습니다.');");
            out.println("location.href='" + request.getContextPath() + "/mypage.do';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('정보 수정에 실패했습니다. 다시 시도해주세요.');");
            out.println("history.back();");
            out.println("</script>");
        }
    }
}