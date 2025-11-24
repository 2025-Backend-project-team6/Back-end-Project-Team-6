<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 - 회원 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin.css"> 
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_Table.css">
</head>
<body>
    
    <jsp:include page="admin_Header.jsp" />
    
    <div class="container main-content"> 
        <div class="tab-content active" style="display: block;">
            <div class="card">
                <div class="card-header">
                    <h3>사용자 목록</h3>
                    <div class="header-actions">
                        
                        <form action="${pageContext.request.contextPath}/admin/user.do" method="get" class="search-form">
                            
                            <div class="search-box">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <circle cx="11" cy="11" r="8"/>
                                    <path d="m21 21-4.35-4.35"/>
                                </svg>
                                <input type="text" name="search" placeholder="사용자 검색..." value="${param.search}">
                            </div>
                            
                            <select name="filter" class="filter-select" onchange="this.form.submit()">
                                <option value="all" ${param.filter == 'all' ? 'selected' : ''}>전체</option>
                                <option value="active" ${param.filter == 'active' ? 'selected' : ''}>활성</option>
                                <option value="inactive" ${param.filter == 'inactive' ? 'selected' : ''}>비활성</option>
                            </select>
                        </form>
                    </div>
                </div>

                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>레벨</th>
                            <th>권한</th> <th>가입일</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty memberList}">
                                <c:forEach var="member" items="${memberList}">
                                    <tr>
                                        <td>${member.userid}</td>
                                        <td>${member.username}</td>
                                        <td>${member.email}</td>
                                        
                                        <td><span class="badge green-badge">Lv.${member.level}</span></td>
                                        
                                        <td>${member.role}</td>
                                        
                                        <td>2024.03.15</td> 
                                        
                                        <td>
                                            <c:choose>
                                                <c:when test="${member.user_status == 'ACTIVE'}">
                                                    <span class="badge status-active">활성</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge status-inactive">정지</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/user.do?userId=${member.userid}" class="btn-detail">상세</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            
                            <c:otherwise>
                                <tr>
                                    <td colspan="8" style="text-align: center; padding: 30px; color: #666;">
                                        회원 정보가 없습니다.
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                </table>
            </div>
        </div>
    </div> 
</body>
</html>