<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
//식단 기록 및 함유물? 제공 페이지
<title>Insert title here</title>
</head>
<body>

<table border="1" cellpadding="5">
    <tr>
        <th>날짜</th>
        <th>기록번호</th>
        <th>칼로리</th>
        <th>탄수화물</th>
        <th>단백질</th>
        <th>지방</th>
        <th>당</th>
        <th>나트륨</th>
    </tr>
    <%
        java.util.List<com.team18.diet.DietRecord> list =
            (java.util.List<com.team18.diet.DietRecord>) request.getAttribute("dietList");

        if (list == null || list.isEmpty()) {
    %>
        <tr><td colspan="8">오늘 기록된 식단이 없습니다.</td></tr>
    <%
        } else {
            for (com.team18.diet.DietRecord dr : list) {
    %>
        <tr>
            <td><%= dr.getRecordDate() %></td>
            <td><%= dr.getDietItem() %></td>
            <td><%= dr.getTotalKcal() %></td>
            <td><%= dr.getTotalCarbs() %></td>
            <td><%= dr.getTotalProtein() %></td>
            <td><%= dr.getTotalFat() %></td>
            <td><%= dr.getTotalSugar() %></td>
            <td><%= dr.getTotalSodium() %></td>
            
            <td>
        	<a href="${pageContext.request.contextPath}/diet/delete?item=<%= dr.getDietItem() %>">삭제</a>
    		</td>
        </tr>
    <%
            }
        }
    %>
    
    
    <a href="${pageContext.request.contextPath}/dietForm.jsp">식단 추가하기</a>
    
    
</table>

</body>
</html>