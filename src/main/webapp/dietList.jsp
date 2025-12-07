<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team18.diet.DietRecord" %>
<%
    // 리스트 및 페이징 정보
    java.util.List<DietRecord> list = (java.util.List<DietRecord>) request.getAttribute("dietList");
    
    Integer currentPageObj = (Integer) request.getAttribute("currentPage");
    int currentPage = (currentPageObj != null) ? currentPageObj : 1;
    
    Integer totalPagesObj = (Integer) request.getAttribute("totalPages");
    int totalPages = (totalPagesObj != null) ? totalPagesObj : 0;
    
    String searchDate = (String) request.getAttribute("searchDate");
    if (searchDate == null) searchDate = "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식단 관리</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 950px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; }
    
    .stats-box { background-color: #e8f8f5; padding: 20px; border-radius: 8px; border-left: 5px solid #2ecc71; margin-bottom: 20px; }
    .stats-title { font-weight: bold; font-size: 1.1em; margin-bottom: 10px; color: #27ae60; }
    
    /* 검색바 스타일 */
    .search-bar { background-color: #f9f9f9; padding: 15px; border-radius: 8px; text-align: center; margin-bottom: 20px; border: 1px solid #ddd; }
    .search-bar form { display: flex; justify-content: center; align-items: center; gap: 10px; }
    .search-bar input[type="date"] { width: auto; padding: 8px; }
    .btn-search { background-color: #3498db; color: white; border: none; padding: 8px 15px; border-radius: 5px; cursor: pointer; }
    .btn-reset { background-color: #95a5a6; color: white; text-decoration: none; padding: 8px 15px; border-radius: 5px; font-size: 0.9em; display: inline-block; }

    table { width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 0.95em; }
    th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
    th { background-color: #2ecc71; color: white; }
    tr:nth-child(even) { background-color: #f9f9f9; }
    
    a.btn-del { display: inline-block; padding: 5px 10px; background-color: #e74c3c; color: white; text-decoration: none; border-radius: 4px; font-size: 0.8em; }
    
    .pagination { text-align: center; margin-top: 20px; }
    .pagination a { display: inline-block; padding: 8px 12px; margin: 0 2px; border: 1px solid #ddd; color: #333; text-decoration: none; border-radius: 4px; transition: 0.3s; }
    .pagination a:hover { background-color: #eee; }
    .pagination a.active { background-color: #2ecc71; color: white; border-color: #2ecc71; }
    
    .nav-links { text-align: center; margin-top: 20px; }
    .nav-links a { margin: 0 10px; color: #333; text-decoration: none; font-weight: bold; }
    .nav-links a:hover { color: #2ecc71; }
</style>
</head>
<body>

<div class="container">
    <h2>🍽️ 식단 관리</h2>

    <div class="stats-box">
        <div class="stats-title">[나의 영양 통계]</div>
        <ul style="margin: 0; padding-left: 20px;">
            <li><strong>주간 일평균 칼로리:</strong> <%= request.getAttribute("weeklyAvgKcal") %> kcal</li>
            <li><strong>주간 단백질 섭취량 (최근 7일):</strong> <%= request.getAttribute("weeklyProtein") != null ? request.getAttribute("weeklyProtein") : 0 %> g</li>
            <li><strong>최근 30일 총 칼로리:</strong> <%= request.getAttribute("monthlyKcal") != null ? request.getAttribute("monthlyKcal") : 0 %> kcal</li>
            <li>
                <strong>평균 영양소 섭취 (전체):</strong>
                <% DietRecord avg = (DietRecord) request.getAttribute("avgNutrients");
                   if (avg != null) { %>
                    단백질 <%= avg.getTotalProtein() %>g / 탄수화물 <%= avg.getTotalCarbs() %>g / 지방 <%= avg.getTotalFat() %>g
                <% } else { %> 데이터 없음 <% } %>
            </li>
        </ul>
    </div>

    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/diet/list" method="get">
            <label>📅 날짜 조회:</label>
            <input type="date" name="searchDate" value="<%= searchDate %>">
            <button type="submit" class="btn-search">검색</button>
            <a href="<%= request.getContextPath() %>/diet/list" class="btn-reset">전체 보기</a>
        </form>
    </div>

    <h3>📜 식단 기록 목록</h3>
    <table>
        <tr>
            <th>날짜</th>
            <th>칼로리</th>
            <th>탄수화물</th>
            <th>단백질</th>
            <th>지방</th>
            <th>당</th>
            <th>나트륨</th>
            <th>관리</th>
        </tr>
        <% if (list == null || list.isEmpty()) { %>
            <tr><td colspan="8">기록된 식단이 없습니다.</td></tr>
        <% } else {
                for (DietRecord dr : list) { %>
            <tr>
                <td><%= dr.getRecordDate() %></td>
                <td><%= dr.getTotalKcal() %></td>
                <td><%= dr.getTotalCarbs() %></td>
                <td><%= dr.getTotalProtein() %></td>
                <td><%= dr.getTotalFat() %></td>
                <td><%= dr.getTotalSugar() %></td>
                <td><%= dr.getTotalSodium() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/diet/delete?item=<%= dr.getDietItem() %>" 
                       onclick="return confirm('정말 삭제하시겠습니까?');" class="btn-del">삭제</a>
                </td>
            </tr>
        <% } } %>
    </table>

    <% if (totalPages > 1) { %>
    <div class="pagination">
        <% 
           String query = "";
           if (!searchDate.isEmpty()) query = "&searchDate=" + searchDate;
        %>
        
        <% if (currentPage > 1) { %>
            <a href="?page=<%= currentPage - 1 %><%= query %>">&laquo; 이전</a>
        <% } %>

        <% 
           int startPage = Math.max(1, currentPage - 5);
           int endPage = Math.min(totalPages, currentPage + 5);
           for (int i = startPage; i <= endPage; i++) { 
        %>
            <a href="?page=<%= i %><%= query %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
        <% } %>

        <% if (currentPage < totalPages) { %>
            <a href="?page=<%= currentPage + 1 %><%= query %>">다음 &raquo;</a>
        <% } %>
    </div>
    <% } %>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dietForm.jsp">➕ 식단 추가하기</a> | 
        <a href="${pageContext.request.contextPath}/main.jsp">🏠 메인 메뉴로</a>
    </div>
</div>

</body>
</html>