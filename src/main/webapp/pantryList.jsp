<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.team18.pantry.PantryItem" %>

<%
    List<PantryItem> list = (List<PantryItem>) request.getAttribute("pantryList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재료 관리 (내 냉장고)</title>
<style>
    table { border-collapse: collapse; width: 90%; margin: 20px auto; }
    th, td { border: 1px solid #444; padding: 8px; text-align: center; }
    th { background-color: #eee; }
    h2 { text-align: center; }
    a { text-decoration: none; color: blue; }
</style>

</head>
<body>

<h2>내 냉장고 목록</h2>

<table>
    <tr>
        <th>번호</th>
        <th>재료명</th>
        <th>수량</th>
        <th>유통기한</th>
        <th>삭제</th>
    </tr>

<% if (list == null || list.isEmpty()) { %>
    <tr>
        <td colspan="5">등록된 재료가 없습니다.</td>
    </tr>
<% } else {
     for (PantryItem i : list) {
%>
    <tr>
        <td><%= i.getItemNo() %></td>
        <td><%= i.getIngredientName() %></td>
        <td><%= i.getQuantity() %></td>
        <td> <%= (i.getExpiryDate() != null) ? i.getExpiryDate().toString() : "-" %></td>
        <td>
            <!--그냥 삭제 버튼-->
            <a href="<%= request.getContextPath() %>/pantry/delete?item_no=<%= i.getItemNo() %>">삭제</a>
            
            <!--수량 소비 버튼-->
            <form action="<%=request.getContextPath()%>/pantry/consume" method="post" style="display:inline;">
            <input type="hidden" name="item_no" value="<%= i.getItemNo() %>">
            <input type="number" name="amount" step="0.1" min="0" required>
            <button type="submit">소비</button>
        </form>
        </td>
        
        
    </tr>
<%  } } %>

</table>

<br>
<div style="text-align:center;">
    <a href="<%= request.getContextPath() %>/pantry/add">재료 추가하기</a>
</div>


</body>
</html>