package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.bean.AttendanceLog;

public class AttendacneLogDAO {
	
	static Configuration config = new Configuration().configure("hibernate.cfg.xml");
	
	public static boolean insert(AttendanceLog log) {
		boolean status = false;
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(log);
		
		session.getTransaction().commit();
		session.close();
		
		return status;
	}
	
	public static AttendanceLog get(int id) {
		Session session = config.buildSessionFactory().openSession();
		
		AttendanceLog log = (AttendanceLog) session.get(AttendanceLog.class, id);
		
		session.close();
		
		return log;
	}
	
	public static void update(AttendanceLog log) {
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		session.update(log);
		
		session.getTransaction().commit();
		session.close();
	};
	
	public static void delete(int id) {
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		session.delete(session.get(AttendanceLog.class, id));
		
		session.getTransaction().commit();
		session.close();
	};
	
	public static List<Object[]> getBySubject(String subject){
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		Query q = session.createSQLQuery("select a.id, roll,name,date,subject from student as s right join attendance_log a on s.id = a.student_id where subject=:subject order by date ");
		q.setParameter("subject", subject);
		List<Object []> log = q.getResultList();
		session.getTransaction().commit();
		session.close();
		
		
		return log;
	}
}
