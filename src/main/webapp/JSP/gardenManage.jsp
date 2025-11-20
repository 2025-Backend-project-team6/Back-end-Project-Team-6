<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/JSP/header.jsp" %>
	
	<h5>텃밭 관리</h5>
	<p>총 ${count}개의 텃밭을 관리중입니다.</p>
	
	<form action="${pageContext.request.contextPath}/gardenmanage.do" method="get">
		<button type="submit" name="action" value="addGarden">+ 새텃밭 추가</button>
		<button type="submit" name="action" value="viewGarden">텃밭 둘러보기</button>
	</form>
</body>
</html>