<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 관리</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin.css"> 
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_Table.css">
</head>
<body>
	
	<jsp:include page="admin_Header.jsp" />
	
	<div class="container">
		<div class="main-content">
	
	        <div class="tab-content active">
	            <div class="card">
	                <div class="card-header">
	                    <div class="reports-title">
	                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#ef4444" stroke-width="2">
	                            <path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/>
	                            <line x1="12" y1="9" x2="12" y2="13"/>
	                            <line x1="12" y1="17" x2="12.01" y2="17"/>
	                        </svg>
	                        <h3>신고된 게시글</h3>
	                        <span class="badge report-badge">2건 대기중</span>
	                    </div>
	                </div>

	                <table class="data-table">
	                    <thead>
	                        <tr>
	                            <th>ID</th>
	                            <th>제목</th>
	                            <th>작성자</th>
	                            <th>신고 수</th>
	                            <th>날짜</th>
	                            <th>상태</th>
	                            <th>조치</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr>
	                            <td>1</td>
	                            <td>스팸 광고성 게시글입니다</td>
	                            <td>스팸유저</td>
	                            <td><span class="badge report-count">12건</span></td>
	                            <td>2024.11.08</td>
	                            <td><span class="badge status-pending">대기중</span></td>
	                            <td>
	                                <div class="action-buttons">
	                                    <form action="approveReport.do" method="post" style="display: inline;">
	                                        <input type="hidden" name="reportId" value="1">
	                                        <button type="submit" class="btn-approve" title="승인">✓</button>
	                                    </form>
	                                    <form action="rejectReport.do" method="post" style="display: inline;">
	                                        <input type="hidden" name="reportId" value="1">
	                                        <button type="submit" class="btn-reject" title="거부">✕</button>
	                                    </form>
	                                </div>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>2</td>
	                            <td>부적절한 내용 포함</td>
	                            <td>문제유저</td>
	                            <td><span class="badge report-count">8건</span></td>
	                            <td>2024.11.07</td>
	                            <td><span class="badge status-pending">대기중</span></td>
	                            <td>
	                                <div class="action-buttons">
	                                    <form action="approveReport.do" method="post" style="display: inline;">
	                                        <input type="hidden" name="reportId" value="2">
	                                        <button type="submit" class="btn-approve" title="승인">✓</button>
	                                    </form>
	                                    <form action="rejectReport.do" method="post" style="display: inline;">
	                                        <input type="hidden" name="reportId" value="2">
	                                        <button type="submit" class="btn-reject" title="거부">✕</button>
	                                    </form>
	                                </div>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>3</td>
	                            <td>허위 정보 유포</td>
	                            <td>거짓말쟁이</td>
	                            <td><span class="badge report-count">15건</span></td>
	                            <td>2024.11.06</td>
	                            <td><span class="badge status-resolved">처리완료</span></td>
	                            <td>
	                                <div class="action-buttons">
	                                    <form action="approveReport.do" method="post" style="display: inline;">
	                                        <input type="hidden" name="reportId" value="3">
	                                        <button type="submit" class="btn-approve" title="승인" disabled>✓</button>
	                                    </form>
	                                    <form action="rejectReport.do" method="post" style="display: inline;">
	                                        <input type="hidden" name="reportId" value="3">
	                                        <button type="submit" class="btn-reject" title="거부" disabled>✕</button>
	                                    </form>
	                                </div>
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	        
	    </div>
	</div>
</body>
</html>