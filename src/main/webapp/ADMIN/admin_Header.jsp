<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="currentUri" value="${pageContext.request.requestURI}" />

<header class="admin-header">
    <div class="container">
        <div class="header-content">
        <a href="${pageContext.request.contextPath}/ADMIN/admin_Main.jsp" 
           class="header-left" 
           style="text-decoration: none; color: inherit; display: flex; align-items: center; gap: 1rem;">
            
            <div class="logo-circle">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                </svg>
            </div>
            
            <div>
                <h1>GardenLog Admin</h1>
                <p class="header-subtitle">관리자 대시보드</p>
            </div>
            
        </a> <form action="${pageContext.request.contextPath}/JSP/landing.jsp" method="post" style="margin: 0;">
                <button type="submit" class="logout-btn">로그아웃</button>
            </form>
        </div>
    </div>
</header>

<div class="container">
    <div class="tabs">
        <a href="${pageContext.request.contextPath}/ADMIN/admin_Main.jsp" class="tab-btn">
            대시보드
        </a>
        
        <a href="${pageContext.request.contextPath}/admin/user.do" class="tab-btn">
            사용자 관리
        </a>
        
        <a href="${pageContext.request.contextPath}/ADMIN/admin_Post.jsp" class="tab-btn">
            게시글 관리
        </a>
        
        <a href="${pageContext.request.contextPath}/ADMIN/admin_Reports.jsp" class="tab-btn">
            신고 관리
        </a>
    </div>
</div>