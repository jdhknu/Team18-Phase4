package com.team18.controller;

import java.io.IOException;

import com.team18.dao.DietRecordDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/diet/delete")
	public class DietDeleteServlet extends HttpServlet {

	    private DietRecordDAO dao = new DietRecordDAO();

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String itemStr = request.getParameter("item");
	        int item = Integer.parseInt(itemStr);

	        dao.deleteRecord(item);

	        response.sendRedirect(request.getContextPath() + "/diet/list");
	    }
	}
	

