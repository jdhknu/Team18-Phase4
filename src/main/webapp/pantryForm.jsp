<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재료 추가</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 500px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; }
    label { display: block; margin-top: 15px; font-weight: bold; color: #555; }
    input { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
    .btn-area { text-align: center; margin-top: 25px; }
    button { background-color: #2ecc71; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; }
    button:hover { background-color: #27ae60; }
    a { color: #7f8c8d; text-decoration: none; margin-left: 10px; }
</style>
</head>
<body>

<div class="container">
    <h2>🥦 새 재료 추가</h2>
    <form action="<%= request.getContextPath() %>/pantry/add" method="post">
        <label>재료 이름</label>
        <input type="text" name="ingredient_name" placeholder="예: onion" required>
        
        <label>양 (g)</label>
        <input type="number" step="0.1" name="quantity" placeholder="0" required>
        
        <label>유통기한</label>
        <input type="date" name="expiry_date">

        <div class="btn-area">
            <button type="submit">저장하기</button>
            <a href="<%= request.getContextPath() %>/pantry/list">취소</a>
        </div>
    </form>
</div>

</body>
</html>