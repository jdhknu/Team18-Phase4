<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team18.recipe.Recipe" %>
<% Recipe r = (Recipe) request.getAttribute("recipe"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피 상세</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 700px; }
    h2 { color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; }
    .recipe-meta { display: flex; gap: 20px; margin-bottom: 20px; color: #555; font-size: 0.95em; }
    .recipe-meta span { background: #e8f8f5; padding: 5px 10px; border-radius: 5px; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
    th { background-color: #eee; width: 120px; }
    .desc-box { background-color: #f9f9f9; padding: 15px; border-radius: 5px; line-height: 1.6; margin-top: 10px; white-space: pre-wrap; }
    .back-btn { display: block; text-align: center; margin-top: 30px; text-decoration: none; color: #333; font-weight: bold; }
    .back-btn:hover { color: #2ecc71; }
</style>
</head>
<body>

<div class="container">
    <% if (r == null) { %>
        <p>존재하지 않는 레시피입니다.</p>
    <% } else { %>
        <h2><%= r.getTitle() %></h2>
        
        <div class="recipe-meta">
            <span>⏱️ 조리시간: <%= r.getCookingTime() %>분</span>
            <span>⭐ 난이도: <%= r.getDifficulty() %></span>
        </div>

        <h3>📝 조리 방법</h3>
        <div class="desc-box"><%= r.getDescription() %></div>

        <h3>📊 영양 정보</h3>
        <table>
            <tr><th>칼로리</th><td><%= r.getKcal() %> kcal</td></tr>
            <tr><th>탄수화물</th><td><%= r.getCarbs() %> g</td></tr>
            <tr><th>단백질</th><td><%= r.getProtein() %> g</td></tr>
            <tr><th>지방</th><td><%= r.getFat() %> g</td></tr>
            <tr><th>당류</th><td><%= r.getSugar() %> g</td></tr>
            <tr><th>나트륨</th><td><%= r.getSodium() %> mg</td></tr>
        </table>
    <% } %>

    <a href="<%= request.getContextPath() %>/recipe/list" class="back-btn">◀️ 목록으로 돌아가기</a>
</div>

</body>
</html>