package com.team18.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.team18.dao.UserDAO;
import com.team18.user.User;

@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        User loginUser = (session == null) ? null : (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 파라미터 받기
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        double height = Double.parseDouble(request.getParameter("height"));
        double weight = Double.parseDouble(request.getParameter("weight"));

        // 객체 업데이트 (ID, PW, 성별은 유지하고 나머지만 변경)
        User updateUser = new User();
        updateUser.setUserId(loginUser.getUserId());
        updateUser.setName(name);
        updateUser.setAge(age);
        updateUser.setHeight(height);
        updateUser.setWeight(weight);

        // DB 업데이트
        int result = userDAO.updateUser(updateUser);

        if (result > 0) {
            // 성공 시 세션 정보도 최신화 (중요: 그래야 메인 화면 이름 등이 바로 바뀜)
            loginUser.setName(name);
            loginUser.setAge(age);
            loginUser.setHeight(height);
            loginUser.setWeight(weight);
            session.setAttribute("loginUser", loginUser);
            
            // 메인으로 이동
            response.sendRedirect(request.getContextPath() + "/main.jsp");
        } else {
            // 실패 시 다시 마이페이지로
            response.sendRedirect(request.getContextPath() + "/user/mypage");
        }
    }
}