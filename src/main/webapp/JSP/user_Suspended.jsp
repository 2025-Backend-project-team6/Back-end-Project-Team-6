<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>계정 정지 안내</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/user_Suspended.css">
</head>
<body>
    <div class="suspended-container">
        <h1>🚨 계정 이용 정지 안내</h1>
        
        <%-- 서블릿에서 전달받은 메시지를 출력합니다. --%>
        <p class="error-message">
        <%
            String msg = (String) request.getAttribute("suspendMessage");
            if (msg != null && !msg.isEmpty()) {
                out.print(msg);
            } else {
                out.print("이 계정은 관리자에 의해 사용이 정지되었습니다.");
            }
        %>
        </p>
        
        <p>계정 사용과 관련하여 궁금한 점은 관리자에게 문의해 주십시오.</p>
        <p class="contact-info">
            관리자 이메일: admin@gardenlog.com<br>
            고객센터 전화: 02-1234-5678
        </p>
        
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/JSP/landing.jsp">메인 화면으로 돌아가기</a>
        </div>
    </div>
</body>
</html>