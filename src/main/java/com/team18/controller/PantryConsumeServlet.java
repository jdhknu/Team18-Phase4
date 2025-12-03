package com.team18.controller;

import java.io.IOException;

import com.team18.dao.PantryDAO;
import com.team18.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/pantry/consume")
public class PantryConsumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String itemNoStr = request.getParameter("item_no");
        String amountStr = request.getParameter("amount");

        if (itemNoStr == null || amountStr == null ||
                itemNoStr.isEmpty() || amountStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/pantry/list");
            return;
        }

        int itemNo;
        double amount;

        try {
            itemNo = Integer.parseInt(itemNoStr);
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/pantry/list");
            return;
        }

        PantryDAO dao = new PantryDAO();
        boolean ok = dao.consumeItem(loginUser.getUserId(), itemNo, amount);

        // 수량 부족 등으로 실패했을 때는 나중에 메시지 보여주고 싶으면 request에 flag를 주어도 됨
        response.sendRedirect(request.getContextPath() + "/pantry/list");
    }
}
