package com.gardenlog.servlet.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.gardenlog.servlet.dao.JournalDAO;
import com.gardenlog.servlet.dto.JournalDTO;
import com.gardenlog.servlet.dto.UserDTO;

@WebServlet("/journalWrite.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class JournalWriteController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/JSP/userLoginForm.jsp");
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/journalWrite.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String weather = request.getParameter("weather");
        String dateStr = request.getParameter("logDate");
        Date logDate = Date.valueOf(dateStr);

        String fileName = null;
        Part filePart = request.getPart("logImg");
        
        if(filePart != null && filePart.getSize() > 0) {
            String uploadPath = request.getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String originalName = filePart.getSubmittedFileName();
            String ext = originalName.substring(originalName.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + ext;

            filePart.write(uploadPath + File.separator + fileName);
        }

        JournalDTO journal = new JournalDTO();
        journal.setUserid(loginUser.getUserid());
        journal.setTitle(title);
        journal.setContent(content);
        journal.setWeather(weather);
        journal.setLogDate(logDate);
        journal.setLogImg(fileName);

        JournalDAO dao = new JournalDAO();
        int result = dao.insertJournal(journal);

        if(result == 1) {
            response.sendRedirect("journal.do");
        } else {
            response.getWriter().println("<script>alert('등록 실패!'); history.back();</script>");
        }
    }
}