package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.AttendacneLogDAO;

@WebServlet("/LogController")
public class LogController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String subject = request.getParameter("subject");
		System.out.println(subject);
		
		List<Object []> list = AttendacneLogDAO.getBySubject(subject);
		for (Object[] objects : list) {
			System.out.println(Arrays.deepToString(objects));
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("list", list);
		session.setAttribute("subject", subject);
		
		response.sendRedirect("log.jsp");
		
	}

}
