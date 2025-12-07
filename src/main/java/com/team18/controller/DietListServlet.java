package com.team18.controller;

import java.io.IOException;
import java.util.List;

import com.team18.dao.DietRecordDAO;
import com.team18.dao.GoalDAO; // [추가] 목표 DAO
import com.team18.diet.DietRecord;
import com.team18.goal.NutritionGoal; // [추가] 목표 DTO
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
    private GoalDAO goalDAO = new GoalDAO(); // [추가] 목표 DAO 인스턴스

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

        // 2. 파라미터 수신 (검색 날짜, 페이지 번호)
        String searchDate = request.getParameter("searchDate");
        
        int page = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // 3. 목록 조회 (검색어 + 페이징)
        int totalCount = dietDAO.countDietRecords(userId, searchDate);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        List<DietRecord> list = dietDAO.findDietRecords(userId, searchDate, page, pageSize);

        // 4. 통계 데이터 조회 (기존)
        double weeklyProtein = dietDAO.getWeeklyProtein(userId);
        double monthlyKcal = dietDAO.getMonthlyTotalKcal(userId);
        double weeklyAvgKcal = dietDAO.getWeeklyAvgKcal(userId);
        DietRecord avgNutrients = dietDAO.getNutrientAverages(userId);
        
        
        // (1) 최신 영양 목표 가져오기
        NutritionGoal myGoal = goalDAO.findRecentGoal(userId);
        
        // (2) 오늘 섭취한 총 영양소 계산 (DB에서 '오늘' 날짜로 다시 조회)
        List<DietRecord> todayList = dietDAO.getTodayDiet(userId);
        double todayKcal = 0, todayCarb = 0, todayProtein = 0, todayFat = 0;
        
        if (todayList != null) {
            for (DietRecord dr : todayList) {
                todayKcal += dr.getTotalKcal();
                todayCarb += dr.getTotalCarbs();
                todayProtein += dr.getTotalProtein();
                todayFat += dr.getTotalFat();
            }
        }
        
        // (3) 달성도(%) 계산
        // 0으로 나누는 것 방지랑 최대 100% 넘을 때 UI 처리는 JSP에서 함
        request.setAttribute("myGoal", myGoal);
        request.setAttribute("todayKcal", todayKcal);
        request.setAttribute("todayCarb", todayCarb);
        request.setAttribute("todayProtein", todayProtein);
        request.setAttribute("todayFat", todayFat);


        // 6. JSP로 전달
        request.setAttribute("dietList", list);
        request.setAttribute("searchDate", searchDate);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("weeklyProtein", weeklyProtein);
        request.setAttribute("monthlyKcal", monthlyKcal);
        request.setAttribute("weeklyAvgKcal", weeklyAvgKcal);
        request.setAttribute("avgNutrients", avgNutrients);

        request.getRequestDispatcher("/dietList.jsp").forward(request, response);
    }
}