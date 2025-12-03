package com.team18.controller;

import com.team18.dao.DietRecordDAO;
import com.team18.diet.DietRecord;
import com.team18.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/diet/add")
public class DietAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("../login.jsp");
            return;
        }

        // 로그인한 사용자 객체 꺼내기
        User loginUser = (User) session.getAttribute("loginUser");
        String userId = loginUser.getUserId();
        
        
        String dateStr = request.getParameter("record_date");
        java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);

        DietRecord record = new DietRecord();
        record.setUserId(userId);
        //record.setDietItem(request.getParameter("diet_item"));
        record.setTotalKcal(Double.parseDouble(request.getParameter("total_kcal")));
        record.setTotalCarbs(Double.parseDouble(request.getParameter("total_carbs")));
        record.setTotalProtein(Double.parseDouble(request.getParameter("total_protein")));
        record.setTotalFat(Double.parseDouble(request.getParameter("total_fat")));
        record.setTotalSugar(Double.parseDouble(request.getParameter("total_sugar")));
        record.setTotalSodium(Double.parseDouble(request.getParameter("total_sodium")));
        record.setRecordDate(sqlDate);

        DietRecordDAO dao = new DietRecordDAO();
        dao.insertRecord(record);

        response.sendRedirect(request.getContextPath() + "/diet/list");
    }
}