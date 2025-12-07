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

        // 1. 로그인 여부 체크
        HttpSession session = request.getSession(false);
        User loginUser = (session == null) ? null : (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. 파라미터 받기
        String filter = request.getParameter("filter");
        String typeStr = request.getParameter("type");
        
        // 페이지 설정!!!!
        int page = 1;
        int pageSize = 10; // 한 페이지에 10개씩 보기
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch(NumberFormatException e) {
                page = 1;
            }
        }

        List<Recipe> recipes = null;
        int totalPages = 0; // 전체 페이지 수

        // 3. 페이지화
        if ("pantry".equals(filter)) {
            recipes = recipeDAO.findCookableRecipes(loginUser.getUserId());
            request.setAttribute("filterName", "내 냉장고 재료로 가능한 레시피 (Top 5)");
            
        } else if ("goal".equals(filter)) {
            int type = 0;
            try { type = Integer.parseInt(typeStr); } catch (Exception e) {}
            
            recipes = recipeDAO.findRecipesByGoal(type);
            
            if (type == 1) request.setAttribute("filterName", "고단백 레시피 추천 (Top 10)");
            else if (type == 2) request.setAttribute("filterName", "저칼로리 레시피 추천 (Top 10)");
            else if (type == 3) request.setAttribute("filterName", "저지방 레시피 추천 (Top 10)");
            else request.setAttribute("filterName", "조건 검색 결과");

        } else {
            // 전체 목록 조회 -> 여기만 페이지네이션 적용
            int totalCount = recipeDAO.countAll();
            
            totalPages = (int) Math.ceil((double)totalCount / pageSize);
            
            recipes = recipeDAO.findAll(page, pageSize);
            request.setAttribute("filterName", "전체 레시피 목록");
        }

        // 4. JSP 전달
        request.setAttribute("recipes", recipes);
        request.setAttribute("currentFilter", filter); // 현재 필터 유지용
        
        // 페이지 정보 전달
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/recipeList.jsp").forward(request, response);
    }
}