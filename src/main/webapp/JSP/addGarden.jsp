<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>텃밭 추가</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/addGarden.css">

</head>
<body>
    <%@ include file="/JSP/header.jsp" %>

    <div class="page-container">
        <h1 class="title">새 텃밭 추가</h1>
        <p class="subtitle">새로운 텃밭 정보를 입력해주세요.</p>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/gardenmanage.do" method="post">

                <label>텃밭 이름 *</label>
                <c:if test="${not empty nullMessage}">
                    <p class="error-msg">${nullMessage}</p>
                </c:if>
                <input type="text" name="gardenname"
                    placeholder="예: 우리집 옥상 텃밭" class="input-field">

                <label>위치</label>
                <input type="text" name="location"
                    placeholder="예: 서울시 강남구" class="input-field">

                <label>크기</label>
                <input type="number" name="area" min="0"
                    placeholder="텃밭 크기를 입력하세요" class="input-field">

                <label>시작일</label>
                <input type="date" name="start_date" class="input-field">

                <div class="btn-group">
                    <button type="submit" name="action" value="cancel" class="btn cancel-btn">취소</button>
                    <button type="submit" name="action" value="addGarden" class="btn submit-btn">추가하기</button>
                </div>
            </form>
        </div>
        
    </div>
</body>
</html>
