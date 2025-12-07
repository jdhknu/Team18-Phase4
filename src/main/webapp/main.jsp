<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team18.user.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 메뉴</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 100%; max-width: 600px; text-align: center; }
    h2 { color: #2c3e50; margin-bottom: 10px; }
    .welcome-text { color: #7f8c8d; margin-bottom: 30px; }
    .menu-list { list-style: none; padding: 0; }
    .menu-list li { margin-bottom: 15px; }
    .menu-list a { display: block; padding: 15px; background-color: #e8f8f5; color: #2c3e50; text-decoration: none; border-radius: 8px; font-weight: bold; transition: 0.3s; border-left: 5px solid #2ecc71; }
    .menu-list a:hover { background-color: #2ecc71; color: white; transform: translateX(5px); }
    .logout-btn { display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #95a5a6; color: white; border-radius: 5px; text-decoration: none; font-size: 0.9em; }
    .logout-btn:hover { background-color: #7f8c8d; }
</style>
</head>
<body>

<%
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="container">
    <h2><%= loginUser.getName() != null ? loginUser.getName() : loginUser.getUserId() %>님, 환영합니다!</h2>
    <p class="welcome-text">🌿 메뉴를 선택해주세요 🌿</p>

    <ul class="menu-list">
        <li><a href="diet/list">🍽️ 식단 관리</a></li>
        <li><a href="recipe/list">👨‍🍳 레시피 관리 / 추천</a></li>
        <li><a href="pantry/list">🥦 재료 관리 (내 냉장고)</a></li>
        <li><a href="goal/view">🎯 영양 목표 관리</a></li>
        <li><a href="user/mypage">👤 회원 정보 관리</a></li>
    </ul>

    <a href="logout" class="logout-btn">로그아웃</a>
</div>

</body>
</html>