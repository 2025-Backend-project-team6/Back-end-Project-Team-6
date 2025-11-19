<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GardenLog - 로그인</title>
</head>
<body>

    <div class="login-container">
        <div class="login-card">
            <h2>로그인</h2>

            <form action="login.do" method="post">
                <div class="input-group">
                    <label for="userid">아이디</label>
                    <input type="text" id="userid" name="userid" placeholder="아이디를 입력하세요" required>
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
                </div>

                <c:if test="${not empty loginError}">
                    <div class="error-message">${loginError}</div>
                </c:if>

                <button type="submit" class="btn btn-login">로그인</button>
                
            </form>

            <div class="register-link">
                아직 계정이 없으신가요? <a href="userJoinForm.jsp">회원가입</a>
            </div>
        </div>
    </div>

</body>
</html>