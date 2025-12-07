<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.team18.recipe.Recipe" %>
<%
    List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
    String filterName = (String) request.getAttribute("filterName");
    if (filterName == null) filterName = "전체 레시피 목록";
    
    Integer currentPageObj = (Integer) request.getAttribute("currentPage");
    int currentPage = (currentPageObj != null) ? currentPageObj : 1;
    
    Integer totalPagesObj = (Integer) request.getAttribute("totalPages");
    int totalPages = (totalPagesObj != null) ? totalPagesObj : 0;
    
    String currentFilter = (String) request.getAttribute("currentFilter");
    boolean showPagination = (currentFilter == null || currentFilter.isEmpty()) && totalPages > 1;
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피 목록</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 900px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; }
    
    .filter-bar { background-color: #e8f8f5; padding: 15px; border-radius: 8px; margin-bottom: 20px; text-align: center; }
    .filter-bar a { display: inline-block; margin: 5px 10px; text-decoration: none; color: #333; font-weight: bold; padding: 5px 10px; border-radius: 20px; transition: 0.3s; background: white; border: 1px solid #ddd; }
    .filter-bar a:hover { background-color: #2ecc71; color: white; border-color: #2ecc71; }
    
    h3 { color: #27ae60; margin-bottom: 15px; }
    table { width: 100%; border-collapse: collapse; table-layout: fixed; } /* 고정 너비 */
    th, td { border: 1px solid #ddd; padding: 12px; text-align: center; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
    th { background-color: #2ecc71; color: white; }
    tr:hover { background-color: #f1f1f1; }
    
    a.recipe-link { color: #2980b9; text-decoration: none; font-weight: bold; }
    a.recipe-link:hover { text-decoration: underline; }
    
    .home-link { display: block; text-align: center; margin-top: 20px; color: #555; text-decoration: none; }

    .pagination { text-align: center; margin-top: 20px; }
    .pagination a {
        display: inline-block;
        padding: 8px 12px;
        margin: 0 2px;
        border: 1px solid #ddd;
        color: #333;
        text-decoration: none;
        border-radius: 4px;
        transition: 0.3s;
    }
    .pagination a:hover { background-color: #eee; }
    .pagination a.active {
        background-color: #2ecc71;
        color: white;
        border-color: #2ecc71;
    }
</style>
</head>
<body>

<div class="container">
    <h2>👨‍🍳 레시피 관리 / 추천</h2>

    <div class="filter-bar">
        <span>💡 추천 메뉴: </span>
        <a href="<%= request.getContextPath() %>/recipe/list">전체 보기</a>
        <a href="<%= request.getContextPath() %>/recipe/list?filter=pantry">🛒 내 냉장고 재료로 가능</a>
        <a href="<%= request.getContextPath() %>/recipe/list?filter=goal&type=1">💪 고단백</a>
        <a href="<%= request.getContextPath() %>/recipe/list?filter=goal&type=2">🥗 저칼로리</a>
        <a href="<%= request.getContextPath() %>/recipe/list?filter=goal&type=3">📉 저지방</a>
    </div>

    <h3>[ <%= filterName %> ]</h3>

    <table>
        <colgroup>
            <col width="10%">
            <col width="40%">
            <col width="15%">
            <col width="15%">
            <col width="20%">
        </colgroup>
        <tr><th>ID</th><th>제목</th><th>칼로리</th><th>조리시간</th><th>난이도</th></tr>
        <% if (recipes != null && !recipes.isEmpty()) { 
            for (Recipe r : recipes) { %>
        <tr>
            <td><%= r.getRecipeId() %></td>
            <td style="text-align:left; padding-left:15px;">
                <a href="<%= request.getContextPath() %>/recipe/detail?recipe_id=<%= r.getRecipeId() %>" class="recipe-link">
                    <%= r.getTitle() %>
                </a>
            </td>
            <td><%= r.getKcal() %></td>
            <td><%= r.getCookingTime() %>분</td>
            <td><%= r.getDifficulty() %></td>
        </tr>
        <% } } else { %>
        <tr><td colspan="5">해당하는 레시피가 없습니다.</td></tr>
        <% } %>
    </table>

    <% if (showPagination) { %>
    <div class="pagination">
        <% if (currentPage > 1) { %>
            <a href="?page=<%= currentPage - 1 %>">&laquo; 이전</a>
        <% } %>

        <% 
           int startPage = Math.max(1, currentPage - 5);
           int endPage = Math.min(totalPages, currentPage + 5);
           
           for (int i = startPage; i <= endPage; i++) { 
        %>
            <a href="?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
        <% } %>

        <% if (currentPage < totalPages) { %>
            <a href="?page=<%= currentPage + 1 %>">다음 &raquo;</a>
        <% } %>
    </div>
    <% } %>

    <a href="<%= request.getContextPath() %>/main.jsp" class="home-link">🏠 메인 메뉴로 돌아가기</a>
</div>

</body>
</html>