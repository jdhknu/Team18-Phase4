<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식단 입력</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 600px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; margin-bottom: 20px; }
    table { width: 100%; border-collapse: separate; border-spacing: 0 10px; }
    th { text-align: left; width: 140px; color: #555; }
    input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
    .btn-area { text-align: center; margin-top: 20px; }
    button { background-color: #2ecc71; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-size: 1em; }
    button:hover { background-color: #27ae60; }
    a { color: #7f8c8d; text-decoration: none; margin-left: 10px; }
</style>
</head>
<body>

<div class="container">
    <h2>📅 새 식단 기록</h2>
    <form action="${pageContext.request.contextPath}/diet/add" method="post">
        <table>
            <tr><th>칼로리 (kcal)</th><td><input type="number" step="0.01" name="total_kcal" required></td></tr>
            <tr><th>탄수화물 (g)</th><td><input type="number" step="0.01" name="total_carbs" required></td></tr>
            <tr><th>단백질 (g)</th><td><input type="number" step="0.01" name="total_protein" required></td></tr>
            <tr><th>지방 (g)</th><td><input type="number" step="0.01" name="total_fat" required></td></tr>
            <tr><th>당 (g)</th><td><input type="number" step="0.01" name="total_sugar"></td></tr>
            <tr><th>나트륨 (mg)</th><td><input type="number" step="0.01" name="total_sodium"></td></tr>
            <tr><th>날짜</th><td><input type="date" name="record_date" required></td></tr>
        </table>
        
        <div class="btn-area">
            <button type="submit">저장하기</button>
            <a href="${pageContext.request.contextPath}/diet/list">취소</a>
        </div>
    </form>
</div>

</body>
</html>