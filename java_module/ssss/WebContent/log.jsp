<%@page import="com.bean.Student"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css">
<title>Log</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">Smart Attendance System</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="log.jsp">Attendance <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="studentRegistration.jsp">Student Registration</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="http://127.0.0.1:5000/recog">Class Room</a>
      </li>
      
    </ul>
  </div>
</nav>

<div class="box">
	<form action="LogController">
	<label class="select" for="slct">
		<select id="inputState" class="form-control" name="subject">
			<option value='English'>English</option>
			<option value='Hindi'>Hindi</option>
			<option value='DS'>DS</option>
		</select>
		</label>
		<input type="submit" class="simple">
	</form>
	
	
	<%
	String subject = (String)session.getAttribute("subject");
	if(subject != null){
		%>
		
		<h3>Attendance for <%= subject%></h3>
		<% 
	}
	int count= 0;
	List<Object []> list = (List<Object []>)session.getAttribute("list");
	if(list!=null){
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	    
	
		Date previousDate = (Date) list.get(0)[3];
		String previous =  df.format(previousDate);
		%>
		
		<h4 style="padding:10px;font-size:20px;font-weight: normal;font-style: normal;"><%= previous %></h4>
		
		<table class=" text-center tableBox">
		<tr>
			<th style="border:2px solid white;">S.no</th>
			<th style="border:2px solid white;">Roll No.</th>
			<th style="border:2px solid white;">Name</th>
		</tr>
		<% 
		for (Object[] objects : list) {
			if(! previous.equals(df.format((Date) objects[3]))){
				count = 0;
				previous = df.format((Date) objects[3]);
				//out.print(df.format((Date)objects[3])+"<br>");
				%>
				</table>
				<h4 style="padding:10px;font-size:20px;font-weight: normal;font-style: normal;"><%= df.format((Date)objects[3]) %></h4>
				<table class="text-center tableBox">
		<tr>
			<th style="border:2px solid white;">S.no</th>
			<th style="border:2px solid white;">Roll No.</th>
			<th style="border:2px solid white;">Name</th>
		</tr>
				<% 
			}
			//out.println(Arrays.deepToString(objects)+"<br>");
			//out.print();
			%>
			<tr>
			<td style="border:2px solid white;"><%= ++count %></td>
			<td style="border:2px solid white;"><%=objects[1] %></td>
			<td style="border:2px solid white;"><%=objects[2] %></td>
		</tr>
			<%
			
		}
	}
		
		%>
</div>



<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>