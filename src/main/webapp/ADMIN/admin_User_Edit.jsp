<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_User_Edit.css"> 
<style>
    /* 읽기 전용 필드 스타일 (회색 배경) */
    input[readonly] {
        background-color: #f0f0f0;
        color: #666;
        cursor: not-allowed;
        border: 1px solid #ddd;
    }
</style>
</head>
<body>

    <div class="edit-container">
        <div class="edit-card">
            
            <div class="card-header">
                <h2>회원 정보 수정</h2>
            </div>

            <form action="${pageContext.request.contextPath}/admin/user.do" method="post">
                <input type="hidden" name="command" value="update">
                <input type="hidden" name="userId" value="${user.userid}">

                <div class="form-row">
                    <div class="form-group half">
                        <label for="userName">이름 </label>
                        <input type="text" id="userName" name="username" value="${user.username}" readonly>
                    </div>
                    
                </div>

                <hr style="border: 0; border-top: 1px solid #eee; margin: 20px 0;">

                <div class="form-group">
				    <label for="userLevel">레벨 (Level)</label>
				    <div class="select-wrapper">
				        <select id="userLevel" name="level">
                            <option value="1" ${user.level == 1 ? 'selected' : ''}>1 - 새싹 농부</option>
				            <option value="2" ${user.level == 2 ? 'selected' : ''}>2 - 초보 농부</option>
				            <option value="3" ${user.level == 3 ? 'selected' : ''}>3 - 베테랑 농부</option>
				        </select>
				    </div>
				</div>
				
                <div class="form-group">
				    <label for="userRole">권한 (Role)</label>
				    <div class="select-wrapper">
                        <select id="userRole" name="role">
                            <option value="user" ${user.role == 'user' ? 'selected' : ''}>user (일반 회원)</option>
                            <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>admin (관리자)</option>
				        </select>
				    </div>
				</div>

                <div class="form-group">
				    <label for="userStatus">계정 상태 (Status)</label>
				    <div class="select-wrapper">
				        <select id="userStatus" name="user_status"> 
                            <option value="ACTIVE" ${user.user_status == 'ACTIVE' ? 'selected' : ''}>활성 (ACTIVE)</option>
				            <option value="SUSPENDED" ${user.user_status == 'SUSPENDED' ? 'selected' : ''}>정지 (SUSPENDED)</option>
				        </select>
				    </div>
				</div>

                <div class="button-group">
                    <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
                    <button type="submit" class="btn-save">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="margin-right: 5px;">
                            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                            <polyline points="17 21 17 13 7 13 7 21"></polyline>
                            <polyline points="7 3 7 8 15 8"></polyline>
                        </svg>
                        변경사항 저장
                    </button>
                </div>

            </form>
        </div>
    </div>

</body>
</html>