package com.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attendance_log")
public class AttendanceLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "student_id")
	private int studentId;
	
	private String subject;
	
	private Date date;
	
	
	public AttendanceLog() {
	
	}


	public AttendanceLog(int studentId,String subject) {
		this.studentId = studentId;
		this.subject = subject;
		this.date = new Date();
	}


	public AttendanceLog(int studentId, String subject, Date date) {
		this.studentId = studentId;
		this.subject = subject;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "AttendanceLog [id=" + id + ", studentId=" + studentId + ", subject=" + subject + ", date=" + date + "]";
	}
	
}
