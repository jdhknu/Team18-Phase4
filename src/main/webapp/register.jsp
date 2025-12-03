<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>

<h2>회원가입</h2>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
<% } %>

<form action="register" method="post">
    아이디: <input type="text" name="userId" required><br>
    비밀번호: <input type="password" name="password" required><br>
    이름: <input type="text" name="name"><br>
    성별(M/F): <input type="text" name="gender" maxlength="1"><br>
    나이: <input type="number" name="age"><br>
    키(cm): <input type="number" step="0.1" name="height"><br>
    몸무게(kg): <input type="number" step="0.1" name="weight"><br>
    <button type="submit">가입</button>
</form>


<p><a href="login.jsp">로그인 화면으로</a></p>
</body>
</html>