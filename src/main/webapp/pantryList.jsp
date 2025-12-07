<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.team18.pantry.PantryItem" %>

<%
    // 데이터 가져오기
    List<PantryItem> list = (List<PantryItem>) request.getAttribute("pantryList");
    
    Integer summaryKindsObj = (Integer) request.getAttribute("summaryKinds");
    int summaryKinds = (summaryKindsObj != null) ? summaryKindsObj : 0;

    Double summaryQtyObj = (Double) request.getAttribute("summaryQty");
    double summaryQty = (summaryQtyObj != null) ? summaryQtyObj : 0.0;

    List<String> unusedList = (List<String>) request.getAttribute("unusedList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 냉장고 목록</title>
<style>
    /* 전체 테마 적용 */
    body { font-family: 'Noto Sans KR', sans-serif; background-color: #f4f7f6; padding: 20px; display: flex; justify-content: center; }
    .container { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 900px; }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #2ecc71; padding-bottom: 10px; margin-bottom: 20px; }

    /* 요약 박스 스타일 */
    .summary-box { background-color: #e8f8f5; padding: 20px; border-radius: 8px; border-left: 5px solid #2ecc71; margin-bottom: 20px; text-align: center; font-size: 1.1em; color: #2c3e50; }
    .summary-box b { color: #27ae60; font-size: 1.2em; }

    /* 활용도 낮은 재료 알림 스타일 */
    .unused-box { background-color: #fff3cd; padding: 15px; border-radius: 8px; border: 1px solid #ffeeba; color: #856404; margin-bottom: 20px; text-align: center; }
    .tag { background: white; border: 1px solid #ddd; padding: 5px 10px; border-radius: 20px; margin: 3px; display: inline-block; font-weight: bold; font-size: 0.9em; color: #555; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }

    /* 테이블 스타일 */
    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th, td { border: 1px solid #ddd; padding: 12px; text-align: center; vertical-align: middle; }
    th { background-color: #2ecc71; color: white; }
    tr:nth-child(even) { background-color: #f9f9f9; }
    tr:hover { background-color: #f1f1f1; }

    /* 버튼 및 폼 스타일 */
    .consume-form { display: flex; justify-content: center; align-items: center; gap: 5px; }
    .consume-form input[type="number"] { width: 70px; padding: 6px; text-align: center; border: 1px solid #ccc; border-radius: 4px; }
    .btn-consume { background-color: #3498db; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; transition: 0.3s; }
    .btn-consume:hover { background-color: #2980b9; }
    
    .btn-delete { color: #e74c3c; text-decoration: none; font-weight: bold; padding: 5px 10px; border-radius: 4px; transition: 0.3s; }
    .btn-delete:hover { background-color: #e74c3c; color: white; }

    .nav-links { text-align: center; margin-top: 30px; }
    .nav-links a { margin: 0 15px; color: #333; text-decoration: none; font-weight: bold; font-size: 1.1em; transition: 0.3s; }
    .nav-links a:hover { color: #2ecc71; }
</style>
</head>
<body>

<div class="container">
    <h2>🥦 내 냉장고 목록</h2>

    <div class="summary-box">
        📊 <strong>냉장고 현황:</strong> 
        총 <b><%= summaryKinds %></b> 종류 보관 중 
        (총 수량: <%= String.format("%.2f", summaryQty) %>)
    </div>

    <% if (unusedList != null && !unusedList.isEmpty()) { %>
        <div class="unused-box">
            <strong>⚠️ 활용도 낮은 재료 (어떤 레시피에도 안 쓰임)</strong><br>
            <div style="margin-top:8px;">
            <% for (String name : unusedList) { %>
                <span class="tag"><%= name %></span>
            <% } %>
            </div>
        </div>
    <% } %>

    <table>
        <tr>
            <th style="width: 8%;">코드</th>
            <th style="width: 25%;">재료명</th>
            <th style="width: 15%;">수량</th>
            <th style="width: 20%;">유통기한</th>
            <th style="width: 32%;">관리 (소비/삭제)</th>
        </tr>

    <% if (list == null || list.isEmpty()) { %>
        <tr>
            <td colspan="5" style="padding: 30px; color: #888;">냉장고가 텅 비었어요! 재료를 추가해주세요. 🛒</td>
        </tr>
    <% } else {
         for (PantryItem i : list) {
    %>
        <tr>
            <td><%= i.getItemNo() %></td>
            <td style="font-weight:bold; color:#444;"><%= i.getIngredientName() %></td>
            <td><%= i.getQuantity() %></td>
            <td><%= (i.getExpiryDate() != null) ? i.getExpiryDate().toString() : "<span style='color:#ccc'>-</span>" %></td>
            <td>
                <div style="display: flex; justify-content: center; align-items: center; gap: 10px;">
                    <form action="<%=request.getContextPath()%>/pantry/consume" method="post" class="consume-form">
                        <input type="hidden" name="item_no" value="<%= i.getItemNo() %>">
                        <input type="number" name="amount" step="0.1" min="0" placeholder="양" required>
                        <button type="submit" class="btn-consume">소비</button>
                    </form>
                    
                    <a href="<%= request.getContextPath() %>/pantry/delete?item_no=<%= i.getItemNo() %>"
                       onclick="return confirm('정말 삭제하시겠습니까?');" class="btn-delete">삭제</a>
                </div>
            </td>
        </tr>
    <%  } } %>

    </table>

    <div class="nav-links">
        <a href="<%= request.getContextPath() %>/pantry/add">➕ 재료 추가하기</a> | 
        <a href="<%= request.getContextPath() %>/main.jsp">🏠 메인 메뉴로</a>
    </div>
</div>

</body>
</html>