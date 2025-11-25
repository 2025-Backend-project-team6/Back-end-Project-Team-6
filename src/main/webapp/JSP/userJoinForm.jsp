<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userJoinForm.css">

</head>
<body>

<div class="signup-container">
    <div class="logo">
    	<span>🌱</span> GardenLog
    </div>
    <div class="signup-sub">GardenLog와 함께 농사를 시작하세요</div>

    <form action="${pageContext.request.contextPath}/checkId.do" method="get">
        <label>아이디</label>
            <input type="text" name="userid"
                   value="${userid}"
                   placeholder="아이디를 입력해주세요">
            <div class="check-row">
            	<button type="submit" class="small-btn">중복확인</button>
            	
            	<c:if test="${not empty idCheckMessage}">
        			<span class="check-msg">${idCheckMessage}</span>
    			</c:if>
        	</div>
    </form>

    <form action="${pageContext.request.contextPath}/join.do" method="post">
        <input type="hidden" name="userid" value="${userid}">

        <label>비밀번호</label>
        <input type="password" name="password"
               placeholder="8자 이상 입력해주세요">

        <label>이름</label>
        <input type="text" name="username"
               value="${username}"
               placeholder="홍길동">

        <label>이메일</label>
        <input type="text" name="email"
               value="${email}"
               placeholder="example@email.com">

        <div class="checkbox-area">
            <label>
                <input type="checkbox" name="agreeRequired">
                [필수] 이용약관 및 개인정보 처리방침에 동의합니다.
            </label>
            <label>
                <input type="checkbox" name="agreeMarketing">
                [선택] 마케팅 정보 수신에 동의합니다.
            </label>
        </div>

        <c:if test="${not empty joinCheckMessage}">
            <span class="check-msg">${joinCheckMessage}</span>
        </c:if>

        <button type="submit" class="submit-btn">회원가입</button>
    </form>

    <div class="login-link">
        이미 계정이 있으신가요?
        <a href="${pageContext.request.contextPath}/JSP/userLoginForm.jsp">로그인</a>
    </div>
</div>

</body>
</html>