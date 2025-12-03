<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재료 추가 (내 냉장고)</title>
<style>
    body { font-family: Arial, sans-serif; }
    h2 { margin-left: 10px; }
    form { margin: 20px; }
    label { display: inline-block; width: 120px; margin-bottom: 8px; }
    input[type="text"], input[type="number"], input[type="date"] {
        padding: 4px;
        width: 200px;
    }
    .btn-area { margin-top: 15px; }
</style>
</head>
<body>

<h2>새 재료 추가</h2>

<form action="<%= request.getContextPath() %>/pantry/add" method="post">
    <div>
    	<label>재료 이름:</label>
    	<input type="text" name="ingredient_name" required>
    </div>
    <div>
        <label>수량(g):</label>
    	<input type="number" step="0.1" name="quantity" required><br><br>
    </div>
    <div>
        <label>유통기한:</label>
        <input type="date" name="expiry_date">
        <!-- 비워두면 NULL로 저장 -->
    </div>

    <div class="btn-area">
        <button type="submit">저장</button>
        <a href="<%= request.getContextPath() %>/pantry/list">취소</a>
    </div>
</form>

</body>
</html>