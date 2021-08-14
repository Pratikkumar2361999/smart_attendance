package com.dao;

import java.util.Arrays;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.bean.Student;

public class StudentDAO {

	static Configuration config = new Configuration().configure("hibernate.cfg.xml");
	
	public static boolean insert(Student student) {
		boolean status = false;
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(student);
		
		session.getTransaction().commit();
		session.close();
		
		return status;
	}
	
	public static Student get(int id) {
		Session session = config.buildSessionFactory().openSession();
		
		Student student = session.get(Student.class, id);
		
		session.close();
		
		return student;
	}
	
	public static void update(Student student) {
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		session.update(student);
		
		session.getTransaction().commit();
		session.close();
	};
	
	public static void delete(int id) {
		Session session = config.buildSessionFactory().openSession();
		session.beginTransaction();
		
		session.delete(session.get(Student.class, id));
		
		session.getTransaction().commit();
		session.close();
	};
	
//	public static int getbyRoll(int id) {
//		Session session = config.buildSessionFactory().openSession();
//		
////		Student student = session.get(Student.class, id);
//		
//		
//		
////		Query q = session.createQuery("from Student where id=:id");
//		List<Object []> list4 = q.getResultList();
//		for (Object[] objects : list4) {
//			System.out.println(Arrays.toString(objects));
//		}
//		
//		session.close();
//		
//		return student;
//	}
	
	
	
}
