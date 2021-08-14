package com.bean;

import java.util.Arrays;
import java.util.List;

import com.dao.AttendacneLogDAO;
import com.dao.StudentDAO;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//		Session session = factory.openSession();
//		session.beginTransaction();
		
		
//		Student s1 = new Student("Ppppppppppp","1700419938");
//		s1.setId(1);
		
//		StudentDAO.insert(s1);
		
//		System.out.println(StudentDAO.get(3));

//		StudentDAO.update(s1);
//		StudentDAO.delete(4);
		
//		List<Object []> list = AttendacneLogDAO.getBySubject("Hindi");
//		for (Object[] objects : list) {
//			System.out.println(Arrays.deepToString(objects));
//		}
		
		
		
		
		
		AttendanceLog log = new AttendanceLog(StudentDAO.get(8).getId(),"English");
		AttendacneLogDAO.insert(log);
		
		
//		System.out.println(AttendacneLogDAO.get(1));
//		
//		AttendanceLog log = new AttendanceLog(StudentDAO.get(1).getId(),"Hindi");
//		log.setId(1);
//		AttendacneLogDAO.update(log);
		
//		AttendacneLogDAO.delete(1);
		
//		session.save(s1);
//		
//		session.getTransaction().commit();
//		
//		session.close();
//		factory.close();
	}

}