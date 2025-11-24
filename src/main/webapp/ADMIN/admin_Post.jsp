<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin.css"> 
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin_Post.css">
</head>
<body>

	<jsp:include page="admin_Header.jsp" />	
	<div class="tab-content active">
            <div class="card">
                <div class="card-header">
                    <h3>최근 게시글</h3>
                    <form action="filterPosts.do" method="get">
                        <button type="submit" class="btn-filter">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3"/>
                            </svg>
                            필터
                        </button>
                    </form>
                </div>

                <div class="posts-list">
                    <div class="post-item">
                        <div class="post-content">
                            <h4>게시글 제목 1 - 토마토 재배 팁 공유합니다</h4>
                            <div class="post-meta">
                                <span>작성자: 김농부</span>
                                <span>•</span>
                                <span>2024.11.08</span>
                                <span>•</span>
                                <span>조회 245</span>
                                <span>•</span>
                                <span>좋아요 38</span>
                            </div>
                        </div>
                        <div class="post-actions">
                            <a href="viewPost.jsp?postId=1" class="btn-view">보기</a>
                            <form action="deletePost.do" method="post" style="display: inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <input type="hidden" name="postId" value="1">
                                <button type="submit" class="btn-delete">삭제</button>
                            </form>
                        </div>
                    </div>

                    <div class="post-item">
                        <div class="post-content">
                            <h4>게시글 제목 2 - 상추 키우기 초보 가이드</h4>
                            <div class="post-meta">
                                <span>작성자: 박정원</span>
                                <span>•</span>
                                <span>2024.11.07</span>
                                <span>•</span>
                                <span>조회 189</span>
                                <span>•</span>
                                <span>좋아요 25</span>
                            </div>
                        </div>
                        <div class="post-actions">
                            <a href="viewPost.jsp?postId=2" class="btn-view">보기</a>
                            <form action="deletePost.do" method="post" style="display: inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <input type="hidden" name="postId" value="2">
                                <button type="submit" class="btn-delete">삭제</button>
                            </form>
                        </div>
                    </div>

                    <div class="post-item">
                        <div class="post-content">
                            <h4>게시글 제목 3 - 오이 병충해 대처법</h4>
                            <div class="post-meta">
                                <span>작성자: 이세연</span>
                                <span>•</span>
                                <span>2024.11.06</span>
                                <span>•</span>
                                <span>조회 312</span>
                                <span>•</span>
                                <span>좋아요 52</span>
                            </div>
                        </div>
                        <div class="post-actions">
                            <a href="viewPost.jsp?postId=3" class="btn-view">보기</a>
                            <form action="deletePost.do" method="post" style="display: inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <input type="hidden" name="postId" value="3">
                                <button type="submit" class="btn-delete">삭제</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>