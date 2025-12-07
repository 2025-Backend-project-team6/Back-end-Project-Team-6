<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // 서블릿에서 포워딩될 때 request 객체에 저장된 에러 메시지를 가져옵니다.
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null || errorMessage.isEmpty()) {
        errorMessage = "요청을 처리하는 도중 알 수 없는 오류가 발생했습니다.";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>오류 발생</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/error.css">
</head>
<body>
    <div class="error-container">
        <div class="error-icon">❌</div>
        <h1 class="error-title">서비스 이용에 불편을 드려 죄송합니다.</h1>
        
        <p class="error-message">
            오류 내용: **<%= errorMessage %>**
        </p>
        
        <p class="error-message-detail">
            잠시 후 다시 시도해 주시거나, 홈 화면으로 돌아가 다른 서비스를 이용해 주세요.
        </p>
        
        <div class="button-group">
            <button class="home-button" 
                    onclick="location.href='${pageContext.request.contextPath}/JSP/index.jsp'">
                홈으로 돌아가기
            </button>
            
            <button class="back-button" onclick="history.back()">
                이전 페이지로
            </button>
        </div>
    </div>
</body>
</html>