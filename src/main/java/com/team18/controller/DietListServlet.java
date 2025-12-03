package com.team18.controller;

import java.io.IOException;
import java.util.List;

import com.team18.dao.DietRecordDAO;
import com.team18.diet.DietRecord;
import com.team18.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/diet/list")
public class DietListServlet extends HttpServlet {

    private DietRecordDAO dietDAO = new DietRecordDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 세션에서 로그인 사용자 확인
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");

        // 2. 오늘 식단 목록 가져오기
        List<DietRecord> list = dietDAO.getTodayDiet(loginUser.getUserId());

        // 3. request에 담아서 JSP로 포워드
        request.setAttribute("dietList", list);
        request.getRequestDispatcher("/dietList.jsp").forward(request, response);
    }
}