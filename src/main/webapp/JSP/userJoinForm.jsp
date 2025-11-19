<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>	
	<p>GardenLog와 함께 농사를 시작하세요</p>
	<form action="${pageContext.request.contextPath}/checkId.do" method="get">
		<h5>아이디</h5>
		<input type="text" name="userid" 
			   value="${userid}"
	       	   placeholder="아이디를 입력해주세요">
		<button type="submit">중복확인</button>
	</form>
			
	<c:if test="${not empty idCheckMessage}">
		<p>${idCheckMessage}</p>
	</c:if>
	
	<form action="${pageContext.request.contextPath}/join.do" method="post">
		<input type="hidden" name="userid" value="${userid}">
		
		<h5>비밀번호</h5>
		<input type="password" name="password" 
			   value="${password}"
			   placeholder="8자 이상 입력해주세요">
		
		<h5>이름</h5>
		<input type="text" name="username" 
			   value="${username}"
			   placeholder="홍길동">
		
		<h5>이메일</h5>
		<input type="text" name="email" 
			   value="${email}"
			   placeholder="example@email.com">
		<br>
		
		<input type="checkbox" name="agreeRequired">[필수] 이용약관 및 개인정보 처리방침에 동의합니다.<br>
		<input type="checkbox" name="agreeMarketing">[선택] 마케팅 정보 수신에 동의합니다.<br>
		<input type="submit" value="회원가입">
		
		<c:if test="${not empty joinCheckMessage}">
			<p>${joinCheckMessage}</p>			
		</c:if>
		
		<h5>
			이미 계정이 있으신가요? 
			<a href="${pageContext.request.contextPath}/JSP/userLoginForm.jsp">로그인</a>
		</h5>
	</form>
</body>
</html>