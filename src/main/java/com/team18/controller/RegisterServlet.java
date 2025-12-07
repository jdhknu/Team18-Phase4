package com.team18.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.team18.dao.UserDAO;
import com.team18.user.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAO();
	
 	@Override
 	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String ageStr = request.getParameter("age");
        String heightStr = request.getParameter("height");
        String weightStr = request.getParameter("weight");

        // 아이디 중복 체크
        if (userDAO.exists(userId)) {
            request.setAttribute("error", "이미 존재하는 아이디입니다.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Integer age = (ageStr == null || ageStr.isBlank()) ? null : Integer.parseInt(ageStr);
        Double height = (heightStr == null || heightStr.isBlank()) ? null : Double.parseDouble(heightStr);
        Double weight = (weightStr == null || weightStr.isBlank()) ? null : Double.parseDouble(weightStr);

        User user = new User(userId, password, name, gender, age, height, weight);

        boolean success = userDAO.register(user);

        if (success) {
        	response.sendRedirect("login.jsp?registered=true");
        } else {
            request.setAttribute("error", "회원가입 중 오류가 발생했습니다.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }   
 
 	@Override
 	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }  
}