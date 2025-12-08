<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🌱GardenLog - 작물 검색</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/detailCrop.css">
</head>
<body>
	<%@ include file="/JSP/header.jsp"%>
	
	<div>
		<h5>${crop.crop_title}</h5>
		<p>${crop.category_name}</p>
		<p>${crop.difficulty_level}</p>

		<!-- 기타 섹션이 존재할 경우 -->
		<c:if test="${not empty crop.infoDetail['기타']}">
			<p>${crop.infoDetail['기타'][0]}</p>
		</c:if>
	</div>
	
	<div>
		<h5>재배 정보🌾</h5>
		
		<div>
			<p>📅 생육 기간</p>
			<p>${crop.period_text}</p>
		</div>
		
		<div>
			<p>💧 물주기</p>
			<p>${crop.water_cycle}</p>
		</div>
		
		<div>
			<p>☀️ 햇빛</p>
			<p>${crop.sunlight_hours}</p>
		</div>
		
		<div>
			<p>🌡️ 온도</p>
			<p>${crop.growTemp}</p>
		</div>
	</div>
	
	<div>
		<h5>재배 팁💡</h5>

		<!-- 🔥 tips가 있을 경우만 출력 (비어있으면 아무것도 안 보이게) -->
		<c:if test="${not empty crop.tips}">
			<ul>
			    <c:forEach var="tip" items="${crop.tips}">
			        <li>${tip}</li>
			    </c:forEach>
			</ul>
		</c:if>

		<!-- 🔥 tips가 비어 있으면 표시 -->
		<c:if test="${empty crop.tips}">
			<p>등록된 재배 팁이 없습니다.</p>
		</c:if>
	</div>
	
	<%@ include file="/JSP/footer.jsp"%>
</body>
</html>
