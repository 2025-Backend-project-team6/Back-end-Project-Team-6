<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GardenLog - 로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userLoginForm.css">
</head>
<body>

    <div class="login-container">
        <div class="login-card">
            <h2>🌱GardenLog</h2>
            <p class="subtitle">농사 일지를 기록하고 관리하세요</p>

            <form action="${pageContext.request.contextPath}/login.do" method="post">
                <div class="input-group">
                    <label for="userid">아이디</label>
                    <input type="text" id="userid" name="userid" placeholder="아이디를 입력하세요" required>
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호" required>
                </div>

                <c:if test="${not empty loginError}">
                    <%-- 에러 메시지 텍스트 변경 --%>
                    <div class="error-message">${loginError}</div>
                </c:if>

                <div class="form-options">
                    <label class="remember-me">
                        <input type="checkbox" name="remember"> 로그인 상태 유지
                    </label>
                    <a href="#">비밀번호 찾기</a>
                </div>

                <button type="submit" class="btn btn-login">로그인</button>
            </form>

            <div class="register-link">
                아직 계정이 없으신가요? <a href="${pageContext.request.contextPath}/JSP/userJoinForm.jsp">회원가입</a>
            </div>
            <hr>
            <div class="admin-login-area">
    			관리자이신가요? 
    			<a href="${pageContext.request.contextPath}/JSP/admin_Login.jsp" class="admin-link">
        			관리자 로그인
    			</a>
			</div>
        </div>
    </div>

</body>
</html>