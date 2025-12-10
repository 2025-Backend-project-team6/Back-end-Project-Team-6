<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GardenLog - 일지 작성</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/header.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/footer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/journalWrite.css">
</head>
<body>

    <jsp:include page="./header.jsp" />

    <div class="write-container">
        <h2>📝 새 일지 작성</h2>
        
        <form action="${pageContext.request.contextPath}/journalWrite.do" method="post" enctype="multipart/form-data">
            
            <div class="form-group">
                <label class="form-label">날짜</label>
                <input type="date" name="logDate" class="form-input" required>
            </div>

            <div class="form-group">
                <label class="form-label">어떤 작물의 이야기인가요?</label>
                <select name="myCropId" class="form-select" required>
                    <option value="0">선택 안 함 (전체)</option>
                    <c:forEach var="crop" items="${myCropList}">
                        <option value="${crop.myCropId}">
                            [${crop.gardenName}] ${crop.cropNickname}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label class="form-label">활동 분류</label>
                <div class="weather-options">
                    <label><input type="radio" name="logType" value="관찰" checked> 📝 관찰</label>
                    <label><input type="radio" name="logType" value="물주기"> 💧 물주기</label>
                    <label><input type="radio" name="logType" value="비료"> 💊 비료</label>
                    <label><input type="radio" name="logType" value="수확"> 🧺 수확</label>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label">날씨</label>
                <div class="weather-options">
                    <label><input type="radio" name="weather" value="맑음" checked> ☀️ 맑음</label>
                    <label><input type="radio" name="weather" value="흐림"> ☁️ 흐림</label>
                    <label><input type="radio" name="weather" value="비"> 🌧️ 비</label>
                    <label><input type="radio" name="weather" value="눈"> ❄️ 눈</label>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label">제목</label>
                <input type="text" name="title" class="form-input" placeholder="오늘의 농사 제목을 입력하세요" required>
            </div>

            <div class="form-group">
                <label class="form-label">사진 첨부</label>
                <input type="file" name="logImg" class="form-input" accept="image/*">
            </div>

            <div class="form-group">
                <label class="form-label">내용</label>
                <textarea name="content" class="form-textarea" placeholder="작물의 상태나 오늘 한 일을 기록해보세요!" required></textarea>
            </div>

            <div class="btn-area">
                <a href="journal.do" class="btn btn-cancel">취소</a>
                <button type="submit" class="btn btn-submit">등록하기</button>
            </div>
        </form>
    </div>

    <jsp:include page="./footer.jsp" />

</body>
</html>