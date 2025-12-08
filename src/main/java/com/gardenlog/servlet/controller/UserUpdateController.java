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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") == null) {
             response.sendRedirect(request.getContextPath() + "/JSP/userLoginForm.jsp");
             return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/updateUser.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8"); 
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String location = request.getParameter("location");
        
        PrintWriter out = response.getWriter();

        if (!password.equals(passwordConfirm)) {
            out.println("<script>");
            out.println("alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');");
            out.println("history.back();"); 
            out.println("</script>");
            return;
        }
        
        UserDTO updateUser = new UserDTO();
        updateUser.setUserid(loginUser.getUserid());
        updateUser.setUsername(username);
        updateUser.setEmail(email);
        updateUser.setPassword(password);
        updateUser.setLocation(location);

        UserDAO dao = new UserDAO();
        int result = dao.updateUser(updateUser);
        
        if(result == 1) {
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