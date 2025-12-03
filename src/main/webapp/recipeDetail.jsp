<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team18.recipe.Recipe" %>

<%
    Recipe r = (Recipe) request.getAttribute("recipe");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        table {
            border-collapse: collapse;
            width: 60%;
        }
        th, td {
            padding: 8px;
            border: 1px solid #999;
        }
        h2 { margin-left: 10px; }
    </style>
</head>
<body>

<% if (r == null) { %>
    <p>존재하지 않는 레시피입니다.</p>
<% } else { %>

<h2>레시피 상세 정보</h2>

<table>
    <tr><th>ID</th><td><%= r.getRecipeId() %></td></tr>
    <tr><th>제목</th><td><%= r.getTitle() %></td></tr>
    <tr><th>조리시간</th><td><%= r.getCookingTime() %> 분</td></tr>
    <tr><th>난이도</th><td><%= r.getDifficulty() %></td></tr>
    <tr><th>칼로리</th><td><%= r.getKcal() %></td></tr>
    <tr><th>탄수화물</th><td><%= r.getCarbs() %></td></tr>
    <tr><th>단백질</th><td><%= r.getProtein() %></td></tr>
    <tr><th>지방</th><td><%= r.getFat() %></td></tr>
    <tr><th>당</th><td><%= r.getSugar() %></td></tr>
    <tr><th>나트륨</th><td><%= r.getSodium() %></td></tr>
</table>

<h3>설명</h3>
<p><pre><%= r.getDescription() %></pre></p>

<% } %>

<p>
    <a href="<%= request.getContextPath() %>/recipe/list">레시피 목록으로</a>
</p>

</body>
</html>