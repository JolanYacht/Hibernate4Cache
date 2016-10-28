package com.zhenqi.pojo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

	private static SessionFactory sessionFactory = null;

	@BeforeClass
	public static void beforeClass() {
		Configuration configuration = new Configuration();
		try {
			configuration.configure();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				//.buildServiceRegistry();
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	@Test
	public void testEhcache() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User u1 = (User) session.load(User.class, 3);
		System.out.println(u1.getName());
		session.getTransaction().commit();
		session.close();
		Session session2 = sessionFactory.openSession();

		session2.beginTransaction();
		User u2 = (User) session2.load(User.class, 3);
		System.out.println(u2.getName());
		session2.getTransaction().commit();
		session2.close();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testListEhcache() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<User> users1 = (List<User>) session.createQuery("from User").setCacheable(true).list();
		for (User user : users1) {
			System.out.println(user.getName());
		}
		session.getTransaction().commit();
		session.close();

		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		List<User> users2 = (List<User>) session2.createQuery("from User").setCacheable(true).list();
		for (User user : users2) {
			System.out.println(user.getName());
		}
		session2.getTransaction().commit();
		session2.close();
	}
}