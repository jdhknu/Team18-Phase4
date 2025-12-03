package com.team18.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.team18.util.DBUtil;

@WebServlet("/testConnection")
public class TestConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                out.println("<h1>DB 연결 성공!</h1>");
            } else {
                out.println("<h1>DB 연결 실패</h1>");
            }
        } catch (Exception e) {
            out.println("<h1>오류 발생: " + e.getMessage() + "</h1>");
        }
    }
}