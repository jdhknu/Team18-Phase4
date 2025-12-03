<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.team18.recipe.Recipe" %>

<%
    List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        table {
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            padding: 8px;
            border: 1px solid #999;
            text-align: center;
        }
        h2 { margin-left: 10px; }
    </style>
</head>
<body>

<h2>레시피 관리 / 추천</h2>

<table>
    <tr>
        <th>ID</th>
        <th>제목</th>
        <th>칼로리</th>
        <th>난이도</th>
    </tr>

    <%
        if (recipes != null && !recipes.isEmpty()) {
            for (Recipe r : recipes) {
    %>
    <tr>
        <td><%= r.getRecipeId() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/recipe/detail?recipe_id=<%= r.getRecipeId() %>">
                <%= r.getTitle() %>
            </a>
        </td>
        <td><%= r.getKcal() %></td>
        <td><%= r.getDifficulty() %></td>
    </tr>
    <%
            }
        } else {
    %>
    <tr><td colspan="4">등록된 레시피가 없습니다.</td></tr>
    <% } %>
</table>

<p>
    <a href="<%= request.getContextPath() %>/main.jsp">메인으로 돌아가기</a>
</p>

</body>
</html>