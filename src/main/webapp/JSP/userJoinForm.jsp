<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<form action="../join.do" method="post">
		<h5>아이디</h5>
		<input type="text" name=userid>
		<h5>비밀번호</h5>
		<input type="text" name="password">
		<h5>이름</h5>
		<input type="text" name="username">
		<h5>이메일</h5>
		<input type="text" name="email">
		<input type="submit">
	</form>
</body>
</html>