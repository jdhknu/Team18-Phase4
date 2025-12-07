package com.team18.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team18.dao.RecipeDAO;
import com.team18.recipe.Recipe;
import com.team18.user.User;

@WebServlet("/recipe/detail")
public class RecipeDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RecipeDAO recipeDAO = new RecipeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 체크
        HttpSession session = request.getSession(false);
        User loginUser = (session == null) ? null : (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 파라미터에서 recipe_id 읽기
        String idStr = request.getParameter("recipe_id");
        if (idStr == null || idStr.isEmpty()) {
            // 잘못된 접근이면 목록으로
            response.sendRedirect(request.getContextPath() + "/recipe/list");
            return;
        }

        int recipeId;
        try {
            recipeId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            // 숫자 아닌 값 들어오면 목록으로
            response.sendRedirect(request.getContextPath() + "/recipe/list");
            return;
        }

        // 한 개 레시피 조회
        Recipe recipe = recipeDAO.findById(recipeId);

        if (recipe == null) {
            // 없는 아이디면 목록으로
            response.sendRedirect(request.getContextPath() + "/recipe/list");
            return;
        }

        // JSP로 전달
        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("/recipeDetail.jsp").forward(request, response);
    }
}
