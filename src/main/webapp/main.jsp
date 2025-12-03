<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.team18.user.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 메뉴</title>
</head>
<body>

<%
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<h2><%= loginUser.getName() != null ? loginUser.getName() : loginUser.getUserId() %>님, 환영합니다!</h2>

<ul>
    <li><a href="diet/list">식단 관리</a></li>
    <li><a href="recipe/list">레시피 관리/추천</a></li>
    <li><a href="pantry/list">재료 관리 (내 냉장고)</a></li>
    <li><a href="goal/view">영양 목표 관리</a></li>
    <li><a href="user/mypage">회원 정보 관리</a></li>
    
    
</ul>

</body>
</html>