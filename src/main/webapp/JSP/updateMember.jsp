<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GardenLog - 정보 수정</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/mypage.css">
</head>
<body>
    <jsp:include page="./header.jsp" />

    <div class="mypage-container">
        <div class="sidebar">
            <div class="profile-card">
                 <img src="${pageContext.request.contextPath}/images/profile_default.png" alt="프로필" class="profile-img">
                 <div class="user-name">${sessionScope.loginUser.username} 님</div>
                 <div class="side-menu" style="margin-top: 20px;">
                    <button onclick="location.href='${pageContext.request.contextPath}/mypage.do'">마이페이지로 돌아가기</button>
                 </div>
            </div>
        </div>

        <div class="main-content">
            <div class="section-title">🔒 개인정보 수정</div>
            
            <div class="dashboard-card" style="max-width: 600px;">
                <form action="updateUser.do" method="post" onsubmit="return validateForm()">
                    
                    <div class="form-group">
                        <label class="form-label">아이디 (변경 불가)</label>
                        <input type="text" name="userid" class="form-input" value="${sessionScope.loginUser.userid}" readonly>
                    </div>

                    <div class="form-group">
                        <label class="form-label">이름 (닉네임)</label>
                        <input type="text" name="username" class="form-input" value="${sessionScope.loginUser.username}" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label">이메일</label>
                        <input type="email" name="email" class="form-input" value="${sessionScope.loginUser.email}" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label">새 비밀번호</label>
                        <input type="password" name="password" id="password" class="form-input" placeholder="변경할 비밀번호를 입력하세요" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label">비밀번호 확인</label>
                        <input type="password" id="passwordConfirm" class="form-input" placeholder="비밀번호를 한 번 더 입력하세요" required>
                        <span id="pwMsg" style="color: red; font-size: 0.8rem;"></span>
                    </div>

                    <button type="submit" class="btn-submit">수정 내용 저장하기</button>
                    <a href="${pageContext.request.contextPath}/mypage.do" class="cancel-link">취소하고 돌아가기</a>
                </form>
            </div>
        </div>
    </div>

    <script>
        function validateForm() {
            var pw = document.getElementById("password").value;
            var pwConfirm = document.getElementById("passwordConfirm").value;
            
            if (pw != pwConfirm) {
                document.getElementById("pwMsg").innerText = "비밀번호가 일치하지 않습니다.";
                return false;
            }
            return true;
        }
    </script>
</body>
</html>