<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 - 식단 관리 시스템</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
    .login-container { background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 350px; text-align: center; }
    h2 { color: #2ecc71; margin-bottom: 30px; }
    input[type="text"], input[type="password"] { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
    button { width: 100%; padding: 12px; background-color: #2ecc71; color: white; border: none; border-radius: 5px; font-size: 1.1em; cursor: pointer; margin-top: 10px; transition: background 0.3s; }
    button:hover { background-color: #27ae60; }
    .links { margin-top: 20px; font-size: 0.9em; }
    a { color: #7f8c8d; text-decoration: none; }
    a:hover { color: #2ecc71; }
    .error { color: #e74c3c; margin-bottom: 15px; font-size: 0.9em; }
</style>
</head>
<body>

<div class="login-container">
    <h2>🥗 로그인</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div class="error">⚠️ <%= error %></div>
    <% } %>

    <form action="login" method="post">
        <input type="text" name="userId" placeholder="아이디" required>
        <input type="password" name="password" placeholder="비밀번호" required>
        <button type="submit">로그인</button>
    </form>

    <div class="links">
        <a href="register.jsp">회원가입 하기</a>
    </div>
</div>
<%
    String registered = request.getParameter("registered");
    if ("true".equals(registered)) {
%>
    <script>
        alert("회원 가입이 완료되었습니다!\n로그인 후 이용해 주세요.");
    </script>
<%
    }
%>
</body>
</html>