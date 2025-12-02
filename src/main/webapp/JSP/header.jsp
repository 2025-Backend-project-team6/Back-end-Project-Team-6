<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header class="full-width-wrapper">   
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/header.css">
	<div class="header">
		<a href="${pageContext.request.contextPath}/JSP/index.jsp">
	    	<div class="logo">
	    	<span>🌱</span> GardenLog
	    	</div>
		</a>
    	<nav class="nav-menu">
        	<a href="${pageContext.request.contextPath}/gardenmanage.do">텃밭 관리</a>
        	<a href="#">작물 관리</a>
        	<a href="${pageContext.request.contextPath}/JSP/crop_Search.jsp">작물 검색</a>
        	<a href="#">농사 일지</a>
        	<a href="#">커뮤니티</a>
        	<a href="${pageContext.request.contextPath}/mypage.do">마이페이지</a>
    	</nav>
    	<button class="logout-btn" onclick="location.href='landing.jsp'">로그아웃</button>
    </div>
</header>