<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${loginUser.gardenname}</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css">

</head>
<body>
	<%@ include file="/JSP/header.jsp" %>
	
	<h1>텃밭 상세보기 페이지</h1>
</body>
</html>