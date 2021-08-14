package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Student;
import com.dao.StudentDAO;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String roll = request.getParameter("roll");
		System.out.println(name+roll);
		RequestDispatcher rd = request.getRequestDispatcher("studentRegistration.jsp");
		Student student = new Student(name, roll);
		StudentDAO.insert(student);
//		System.out.println(student.getId());
		response.sendRedirect("http://127.0.0.1:5000/collection?id="+student.getId());
//		out.print("<script>alert('Inserted')</script>");
//		rd.include(request, response);
		
	}

}
