package com.team18.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.team18.dao.UserDAO;
import com.team18.user.User;

@WebServlet("/user/delete")
public class UserDeleteServlet extends HttpServlet {

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

        // DB에서 회원 및 연관 데이터 삭제
        boolean isDeleted = userDAO.deleteUser(loginUser.getUserId());

        if (isDeleted) {
            // 세션 무효화 (로그아웃 처리)
            session.invalidate();
            // 로그인 페이지로 이동
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            // 실패 시 다시 마이페이지로 (예: 에러 메시지 전달 가능)
            response.sendRedirect(request.getContextPath() + "/user/mypage");
        }
    }
}