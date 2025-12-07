package com.team18.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.team18.dao.UserDAO;
import com.team18.user.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	private UserDAO userDAO = new UserDAO();
	
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = userDAO.login(userId, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            response.sendRedirect("main.jsp");
        } else {
            request.setAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

}
