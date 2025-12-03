package com.team18.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team18.dao.RecipeDAO;
import com.team18.recipe.Recipe;
import com.team18.user.User;

@WebServlet("/recipe/list")
public class RecipeListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RecipeDAO recipeDAO = new RecipeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 여부 체크 (다른 서블릿에서 쓰는 방식과 동일하게 유지)
        HttpSession session = request.getSession(false);
        User loginUser = (session == null) ? null : (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 로그인 안 되어 있으면 로그인 페이지로
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 전체 레시피 목록 조회
        List<Recipe> recipes = recipeDAO.findAll();

        // JSP로 전달
        request.setAttribute("recipes", recipes);

        // /src/main/webapp/recipe/list.jsp 로 포워딩
        request.getRequestDispatcher("/recipeList.jsp").forward(request, response);
    }
}