<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/JSP/header.jsp" %>
	<h1>새 텃밭 추가</h1>
	<p>새로운 텃밭 정보를 입력해주세요.</p>
	
	<form action="${pageContext.request.contextPath}/gardenmanage.do" method="post">
		<label>텃밭 이름 *</label>
		<input type="text" name="gardenname"
			   placeholder="예: 우리집 옥상 텃밭">
		<br>
		
		<label>위치</label>
		<input type="text" name="location" 
			   placeholder="예: 서울시 강남구">
		<br>
		
		<label>크기</label>
		<input type="number" name="area"
			   placeholder="텃밭 크기를 입력하세요">
		<br>
			   
		<label>시작일</label>
		<input type="date" name="start_date">
		<br>
		
		<button type="reset">취소</button>
		<button type="submit" name="action" value="addGarden">추가하기</button>
	</form>
</body>
</html>