package com.team18.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team18.dao.PantryDAO;
import com.team18.pantry.PantryItem;
import com.team18.user.User;

@WebServlet("/pantry/list")
public class PantryListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // 로그인 체크
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        PantryDAO pantryDAO = new PantryDAO();

        // 1. 전체 목록 조회
        List<PantryItem> list = pantryDAO.findByUserId(loginUser.getUserId());
        
        // 2. [추가] 재료 요약 정보 조회
        double[] summary = pantryDAO.getPantrySummary(loginUser.getUserId());
        
        // 3. [추가] 레시피에 안 쓰이는 재료 목록 조회
        List<String> unusedList = pantryDAO.findUnusedIngredients(loginUser.getUserId());

        request.setAttribute("pantryList", list);
        request.setAttribute("summaryKinds", (int)summary[0]); // 종류
        request.setAttribute("summaryQty", summary[1]);       // 총 수량
        request.setAttribute("unusedList", unusedList);

        request.getRequestDispatcher("/pantryList.jsp").forward(request, response);
    }
}
