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

        // 1. 로그인 체크
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        String userId = loginUser.getUserId();

        // 2. 파라미터 받기
        String searchDate = request.getParameter("searchDate"); // 예: "2025-12-07"
        
        int page = 1;
        int pageSize = 10; // 한 페이지에 10개
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // 3. 데이터 조회
        int totalCount = dietDAO.countDietRecords(userId, searchDate);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        
        List<DietRecord> list = dietDAO.findDietRecords(userId, searchDate, page, pageSize);

        // 4. 통계 데이터 조회
        double weeklyProtein = dietDAO.getWeeklyProtein(userId);
        double monthlyKcal = dietDAO.getMonthlyTotalKcal(userId);
        double weeklyAvgKcal = dietDAO.getWeeklyAvgKcal(userId);
        DietRecord avgNutrients = dietDAO.getNutrientAverages(userId);

        // 5. JSP로 전달
        request.setAttribute("dietList", list);
        request.setAttribute("searchDate", searchDate); // 검색어 유지용
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("weeklyProtein", weeklyProtein);
        request.setAttribute("monthlyKcal", monthlyKcal);
        request.setAttribute("weeklyAvgKcal", weeklyAvgKcal);
        request.setAttribute("avgNutrients", avgNutrients);

        request.getRequestDispatcher("/dietList.jsp").forward(request, response);
    }
}