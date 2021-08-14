package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.AttendanceLog;
import com.dao.AttendacneLogDAO;
import com.dao.StudentDAO;


@WebServlet("/MarkAttendanceController")
public class MarkAttendanceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String subject = request.getParameter("subject");
		String log = request.getParameter("log");
		String [] list = log.split(",");
		out.print("attendance marked"+Arrays.deepToString(list)+subject);
		for (String id : list) {
			AttendanceLog l = new AttendanceLog(StudentDAO.get(Integer.parseInt(id)).getId(),subject);
			AttendacneLogDAO.insert(l);
		}
		response.sendRedirect("log.jsp");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
