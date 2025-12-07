package com.team18.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team18.dao.PantryDAO;
import com.team18.user.User;

@WebServlet("/pantry/delete")
public class PantryDeleteServlet extends HttpServlet {

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

        String itemNoStr = request.getParameter("item_no");
        if (itemNoStr == null || itemNoStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/pantry/list");
            return;
        }

        int itemNo = 0;
        try {
            itemNo = Integer.parseInt(itemNoStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/pantry/list");
            return;
        }

        PantryDAO dao = new PantryDAO();
        dao.delete(itemNo, loginUser.getUserId());

        response.sendRedirect(request.getContextPath() + "/pantry/list");
    }
}
