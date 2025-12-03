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
        List<PantryItem> list = pantryDAO.findByUserId(loginUser.getUserId());

        request.setAttribute("pantryList", list);

        request.getRequestDispatcher("/pantryList.jsp").forward(request, response);
    }
}
