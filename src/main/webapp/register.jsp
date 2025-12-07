<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 100%; max-width: 500px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; margin-bottom: 30px; }
    table { width: 100%; border-collapse: separate; border-spacing: 0 10px; }
    th { text-align: left; width: 100px; color: #555; }
    input[type="text"], input[type="password"], input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; font-size: 1em; background-color: white; height: 42px;}
    button { width: 100%; padding: 12px; background-color: #2ecc71; color: white; border: none; border-radius: 5px; font-size: 1.1em; cursor: pointer; margin-top: 20px; }
    button:hover { background-color: #27ae60; }
    .back-link { display: block; text-align: center; margin-top: 15px; color: #7f8c8d; text-decoration: none; }
    .error { color: red; text-align: center; margin-bottom: 15px; }
</style>
</head>
<body>

<div class="container">
    <h2>📝 회원가입</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p class="error"><%= error %></p>
    <% } %>

    <form action="register" method="post">
        <table>
            <tr><th>아이디</th><td><input type="text" name="userId" required></td></tr>
            <tr><th>비밀번호</th><td><input type="password" name="password" required></td></tr>
            <tr><th>이름</th><td><input type="text" name="name" required></td></tr>
			<tr>
                <th>성별</th>
                <td>
                    <select name="gender" required style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; font-size: 1em; background-color: white;">
                        <option value="">선택해주세요</option>
                        <option value="M">남성 (M)</option>
                        <option value="F">여성 (F)</option>
                    </select>
                </td>
            </tr>
            <tr><th>나이</th><td><input type="number" name="age"></td></tr>
            <tr><th>키 (cm)</th><td><input type="number" step="0.1" name="height"></td></tr>
            <tr><th>체중 (kg)</th><td><input type="number" step="0.1" name="weight"></td></tr>
        </table>
        <button type="submit">가입 완료</button>
    </form>

    <a href="login.jsp" class="back-link">로그인 화면으로 돌아가기</a>
</div>

</body>
</html>