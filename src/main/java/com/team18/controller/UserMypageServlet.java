package com.team18.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.team18.dao.UserDAO;
import com.team18.user.User;

@WebServlet("/user/mypage")
public class UserMypageServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User loginUser = (session == null) ? null : (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // DB에서 최신 정보 조회 (세션 정보는 로그인 시점 기준이라서 변경사항 반영 위해 다시 조회)
        User user = userDAO.findUserById(loginUser.getUserId());
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("/mypage.jsp").forward(request, response);
    }
}