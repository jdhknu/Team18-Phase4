package com.team18.controller;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team18.dao.IngredientDAO;
import com.team18.dao.PantryDAO;
import com.team18.pantry.PantryItem;
import com.team18.user.User;

@WebServlet("/pantry/add")
public class PantryAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // 로그인 안 되어 있으면 로그인 페이지로
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 단순히 입력 폼으로 포워드
        request.getRequestDispatcher("/pantryForm.jsp").forward(request, response);
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

        String userId = loginUser.getUserId();
        String ingredientName = request.getParameter("ingredient_name");  // 이름
        String quantityStr = request.getParameter("quantity");
        String expiryStr = request.getParameter("expiry_date");

        double quantity = 0.0;
        java.sql.Date expiryDate = null;

        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            quantity = 0.0;
        }

        if (expiryStr != null && !expiryStr.isEmpty()) {
            expiryDate = java.sql.Date.valueOf(expiryStr);   // "YYYY-MM-DD"
        }

        // 이름으로 ingredient_num 찾거나 새로 만들기
        IngredientDAO ingredientDAO = new IngredientDAO();
        int ingredientNum = ingredientDAO.findOrCreateIngredient(ingredientName);

        if (ingredientNum == -1) {
            // 실패한 경우: 간단히 목록으로 돌려보내거나 에러 메시지 처리
            response.sendRedirect(request.getContextPath() + "/pantry/list");
            return;
        }

        PantryItem item = new PantryItem();
        item.setUserId(userId);
        item.setIngredientNum(ingredientNum);
        item.setQuantity(quantity);
        item.setExpiryDate(expiryDate);

        PantryDAO dao = new PantryDAO();
        dao.insert(item);

        response.sendRedirect(request.getContextPath() + "/pantry/list");
    }
}
