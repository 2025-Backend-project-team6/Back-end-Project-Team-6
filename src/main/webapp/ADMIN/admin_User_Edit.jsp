<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_User_Edit.css"> 
</head>
<body>

    <div class="edit-container">
        <div class="edit-card">
            
            <div class="card-header">
                <h2>회원 정보 수정</h2>
                <p>회원의 기본 정보를 수정할 수 있습니다.</p>
            </div>

            <form action="${pageContext.request.contextPath}/admin/user.do" method="post">
                <input type="hidden" name="command" value="update">
                <input type="hidden" name="userId" value="${user.userid}">
                <div class="form-row">
                    <div class="form-group half">
                        <label for="userName">이름</label>
                        <input type="text" id="userName" name="userName" value="${user.username}">
                    </div>
                    <div class="form-group half">
                        <label for="userEmail">이메일</label>
                        <input type="email" id="userEmail" name="userEmail" value="${user.email}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="userLocation">위치</label>
                    <input type="text" id="userLocation" name="userLocation" value="${user.address}">
                </div>

				<div class="form-group">
				    <label for="userLevel">레벨</label>
				    <div class="select-wrapper">
				        <select id="userLevel" name="level"> <option value="1" ${user.level == 1 ? 'selected' : ''}>1 - 새싹 농부</option>
				            <option value="2" ${user.level == 2 ? 'selected' : ''}>2 - 초보 농부</option>
				            <option value="3" ${user.level == 3 ? 'selected' : ''}>3 - 베테랑 농부</option>
				        </select>
				    </div>
				</div>
				
				<div class="form-group">
				    <label for="userStatus">계정 상태</label>
				    <div class="select-wrapper">
				        <select id="userStatus" name="user_status"> <option value="active" ${user.user_status == 'active' ? 'selected' : ''}>활성</option>
				            <option value="inactive" ${user.user_status == 'inactive' ? 'selected' : ''}>비활성</option>
				            <option value="banned" ${user.user_status == 'banned' ? 'selected' : ''}>정지</option>
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
                        저장
                    </button>
                </div>

            </form>
        </div>
    </div>

</body>
</html>