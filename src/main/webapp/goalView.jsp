<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team18.goal.NutritionGoal" %>
<%
    NutritionGoal goal = (NutritionGoal) request.getAttribute("goal");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영양 목표 관리</title>
<style>
    /* 공통 스타일 적용 */
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; color: #333; margin: 0; padding: 20px; display: flex; flex-direction: column; align-items: center; }
    .container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); width: 100%; max-width: 600px; margin-bottom: 20px; }
    h2 { color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; margin-bottom: 20px; text-align: center; }
    table { width: 100%; border-collapse: collapse; margin: 20px 0; }
    th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: center; }
    th { background-color: #e8f8f5; color: #16a085; }
    input[type="number"] { width: 100px; padding: 5px; text-align: center; border: 1px solid #ccc; border-radius: 4px; }
    button { background-color: #2ecc71; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-size: 1em; width: 100%; margin-top: 10px; }
    button:hover { background-color: #27ae60; }
    a { text-decoration: none; color: #7f8c8d; display: block; text-align: center; margin-top: 15px; }
    a:hover { color: #2c3e50; }
</style>
</head>
<body>

<div class="container">
    <h2>🎯 나의 영양 목표</h2>

    <% if (goal != null) { %>
        <div style="text-align: center; margin-bottom: 20px; color: #555;">
            현재 목표 설정일: <strong><%= goal.getCreatedAt() %></strong>
        </div>
        <table>
            <tr><th>목표 칼로리</th><td><%= goal.getTargetCalories() %> kcal</td></tr>
            <tr><th>목표 단백질</th><td><%= goal.getTargetProtein() %> g</td></tr>
            <tr><th>목표 탄수화물</th><td><%= goal.getTargetCarb() %> g</td></tr>
            <tr><th>목표 지방</th><td><%= goal.getTargetFat() %> g</td></tr>
        </table>
    <% } else { %>
        <p style="text-align:center; color:#e74c3c;">아직 설정된 목표가 없습니다. 아래에서 설정해주세요!</p>
    <% } %>

    <hr style="margin: 30px 0; border: 0; border-top: 1px dashed #ccc;">

    <h3>새 목표 설정하기</h3>
    <form action="<%= request.getContextPath() %>/goal/view" method="post">
        <table>
            <tr>
                <td>칼로리 (kcal)</td>
                <td><input type="number" step="0.01" name="target_calories" required></td>
            </tr>
            <tr>
                <td>단백질 (g)</td>
                <td><input type="number" step="0.01" name="target_protein" required></td>
            </tr>
            <tr>
                <td>탄수화물 (g)</td>
                <td><input type="number" step="0.01" name="target_carb" required></td>
            </tr>
            <tr>
                <td>지방 (g)</td>
                <td><input type="number" step="0.01" name="target_fat" required></td>
            </tr>
        </table>
        <button type="submit">새 목표 저장</button>
    </form>
    
    <a href="<%= request.getContextPath() %>/main.jsp">메인 메뉴로 돌아가기</a>
</div>

</body>
</html>