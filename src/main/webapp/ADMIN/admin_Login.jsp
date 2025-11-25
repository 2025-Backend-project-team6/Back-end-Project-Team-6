<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GardenLog Admin Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_Login.css">
</head>
<body>

    <div class="login-wrapper">
        <div class="login-card">
            <div class="login-header">
                <div class="icon-circle">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                    </svg>
                </div>
                <h2>GardenLog Admin</h2>
                <p>관리자 로그인</p>
            </div>

            <form action="/admin/login.do" method="post">
                <div class="form-group">
                    <label for="adminEmail">관리자 이메일</label>
                    <input type="text" id="adminId" name="adminId" placeholder="아이디를 입력하세요" required>
                </div>

                <div class="form-group">
                    <label for="adminPw">비밀번호</label>
                    <input type="password" id="adminPw" name="adminPw" placeholder="비밀번호를 입력하세요" required>
                </div>

                <button type="submit" class="submit-btn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                    </svg>
                    관리자 로그인
                </button>
            </form>
        </div>
    </div>

</body>
</html>