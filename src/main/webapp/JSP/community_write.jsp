<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글쓰기 - SanterLog</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/community_write.css"> 
</head>
<body>
<%@ include file="header.jsp" %>
    <div class="container my-4 custom-content">
        <div class="row justify-content-center">
            <div class="col-lg-8">
            
            <div class="back-link-area mb-3"> 
                    <a href="${pageContext.request.contextPath}/JSP/community.jsp" class="back-button">
                        &larr; 돌아가기
                    </a>
                </div>
                
                <div class="card p-4 custom-card">
                    <h2 class="card-title mb-4 custom-title">글쓰기 <span class="edit-icon">📝</span></h2>
                    <p class="text-muted mb-4 custom-subtitle">덧붙여 이야기를 공유해주세요</p>

                    <form action="submit_post" method="POST" enctype="multipart/form-data">
                        
                        <div class="mb-4">
                            <label for="category" class="form-label required-label">카테고리</label>
                            <select class="form-select form-select-lg" id="category" name="category" required>
                                <option value="" disabled selected>카테고리를 선택하세요</option>
                                <option value="food">맛집</option>
                                <option value="place">장소</option>
                                <option value="review">리뷰</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="title" class="form-label required-label">제목</label>
                            <input type="text" class="form-control form-control-lg" id="title" name="title" placeholder="제목을 입력하세요" required>
                        </div>

                        <div class="mb-4 content-box">
                            <label for="content" class="form-label required-label">내용</label>
                            <textarea class="form-control" id="content" name="content" rows="10" placeholder="내용을 입력하세요"></textarea>
                        </div>

                        <div class="mb-5 attachment-box">
                            <label for="attachment" class="form-label">첨부 파일</label>
                            <div class="file-upload-area">
                                <label for="file-input" class="file-label">
                                    <div class="placeholder-icon"></div>
                                    <p class="placeholder-text">사진이나 영상 등록 가능</p>
                                    <p class="placeholder-note">파일은 최대 5MB까지 가능합니다</p>
                                </label>
                                <input type="file" id="file-input" name="files[]" multiple accept="image/*,video/*" style="display:none;">
                            </div>
                        </div>

                        <div class="writing-tip-box p-3 mb-5">
                            <h4 class="tip-title">💡 글쓰기 Tip</h4>
                            <ul class="tip-list">
                                <li>- 세부적인 내용과 경험을 담아 작성해주세요.</li>
                                <li>- 구체적인 상황과 사실을 통해 흥미를 더해주세요.</li>
                                <li>- 맞춤법과 띄어쓰기에 유의하시면 만족도가 더해요.</li>
                                <li>- 세부적인 배경과 이유를 간단히 밝혀주세요.</li>
                            </ul>
                        </div>
                        
                        <div class="d-flex justify-content-end align-items-center form-footer">
                            <span class="text-muted me-3">취소</span>
                            <button type="submit" class="btn btn-submit">게시글 등록</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <%@ include file="footer.jsp" %>
</body>
</html>