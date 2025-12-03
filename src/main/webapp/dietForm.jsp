//새 식단 입력 페이지
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식단 입력</title>
</head>
<body>

<h2>식단 입력</h2>

<form action="${pageContext.request.contextPath}/diet/add" method="post">
    음식명: <input type="text" name="diet_item"><br>
    칼로리(kcal): <input type="number" step="0.01" name="total_kcal"><br>
    탄수화물(g): <input type="number" step="0.01" name="total_carbs"><br>
    단백질(g): <input type="number" step="0.01" name="total_protein"><br>
    지방(g): <input type="number" step="0.01" name="total_fat"><br>
    당(g): <input type="number" step="0.01" name="total_sugar"><br>
    나트륨(mg): <input type="number" step="0.01" name="total_sodium"><br>
    날짜: <input type="date" name="record_date"><br><br>

    <input type="submit" value="저장">
</form>


</body>
</html>