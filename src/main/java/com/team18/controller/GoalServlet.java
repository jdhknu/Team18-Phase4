package com.team18.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.team18.dao.GoalDAO;
import com.team18.goal.NutritionGoal;
import com.team18.user.User;

@WebServlet("/goal/view")
public class GoalServlet extends HttpServlet {

    private GoalDAO goalDAO = new GoalDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 최신 목표 가져오기
        NutritionGoal currentGoal = goalDAO.findRecentGoal(loginUser.getUserId());
        request.setAttribute("goal", currentGoal);

        // JSP로 이동
        request.getRequestDispatcher("/goalView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 폼 데이터 받기
        double kcal = Double.parseDouble(request.getParameter("target_calories"));
        double protein = Double.parseDouble(request.getParameter("target_protein"));
        double carb = Double.parseDouble(request.getParameter("target_carb"));
        double fat = Double.parseDouble(request.getParameter("target_fat"));

        NutritionGoal newGoal = new NutritionGoal(loginUser.getUserId(), kcal, protein, carb, fat);

        // 저장
        goalDAO.insertGoal(newGoal);

        // 다시 조회 화면으로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/goal/view");
    }
}