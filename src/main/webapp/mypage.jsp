<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team18.user.User" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/user/mypage");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 관리</title>
<style>
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 600px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; margin-bottom: 20px; }

    table { width: 100%; border-collapse: separate; border-spacing: 0 15px; }
    th { text-align: left; width: 120px; color: #555; font-weight: bold; vertical-align: middle; }
    td { vertical-align: middle; }

    input[type="text"], input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 5px; box-sizing: border-box; font-size: 1em; }
    
    .readonly-text { background-color: #f9f9f9; padding: 10px; border-radius: 5px; color: #7f8c8d; border: 1px solid #eee; font-weight: bold; }

    .btn-area { text-align: center; margin-top: 20px; padding-bottom: 20px; border-bottom: 1px dashed #ccc; }
    button.btn-save { background-color: #2ecc71; color: white; border: none; padding: 12px 25px; border-radius: 5px; cursor: pointer; font-size: 1.05em; transition: 0.3s; }
    button.btn-save:hover { background-color: #27ae60; }
    a.btn-cancel { color: #7f8c8d; text-decoration: none; margin-left: 15px; font-weight: bold; }

    .danger-zone { margin-top: 30px; background-color: #fff5f5; border: 1px solid #ffcccc; padding: 20px; border-radius: 8px; text-align: center; }
    .danger-title { color: #c0392b; font-weight: bold; margin-bottom: 10px; display: block; }
    .danger-desc { font-size: 0.9em; color: #c0392b; margin-bottom: 15px; }
    a.btn-delete { background-color: #e74c3c; color: white; text-decoration: none; padding: 8px 16px; border-radius: 4px; font-size: 0.9em; display: inline-block; transition: 0.3s; }
    a.btn-delete:hover { background-color: #c0392b; }
</style>
<script>
    function confirmDelete() {
        return confirm("⚠️ 정말로 탈퇴하시겠습니까?\n\n탈퇴 시 회원님의 모든 정보(식단 기록, 냉장고 재료, 목표 설정 등)가 영구적으로 삭제되며, 복구할 수 없습니다.");
    }
</script>
</head>
<body>

<div class="container">
    <h2>👤 회원 정보 수정</h2>

    <form action="<%= request.getContextPath() %>/user/update" method="post">
        <table>
            <tr>
                <th>아이디</th>
                <td><div class="readonly-text"><%= user.getUserId() %></div></td> 
            </tr>
            <tr>
                <th>이름</th>
                <td><input type="text" name="name" value="<%= user.getName() %>" required></td>
            </tr>
            <tr>
                <th>성별</th>
                <td><div class="readonly-text"><%= user.getGender() %></div></td>
            </tr>
            <tr>
                <th>나이</th>
                <td><input type="number" name="age" value="<%= user.getAge() %>" required></td>
            </tr>
            <tr>
                <th>키 (cm)</th>
                <td><input type="number" step="0.1" name="height" value="<%= user.getHeight() %>" required></td>
            </tr>
            <tr>
                <th>몸무게 (kg)</th>
                <td><input type="number" step="0.1" name="weight" value="<%= user.getWeight() %>" required></td>
            </tr>
        </table>
        
        <div class="btn-area">
            <button type="submit" class="btn-save">정보 수정 저장</button>
            <a href="<%= request.getContextPath() %>/main.jsp" class="btn-cancel">메인으로 돌아가기</a>
        </div>
    </form>

    <div class="danger-zone">
        <span class="danger-title">⛔ 회원 탈퇴</span>
        <div class="danger-desc">
            정말로 탈퇴하시겠어요? 😢<br>
            탈퇴 시 모든 데이터가 삭제됩니다!
        </div>
        <a href="<%= request.getContextPath() %>/user/delete" onclick="return confirmDelete();" class="btn-delete">
            회원 탈퇴하기
        </a>
    </div>

</div>

</body>
</html>