package com.translation.prime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ActiveAction {
	static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
																								// from
																								// hibernate.cfg.xml
			.build();
	static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

	public static void showMessage() {
		System.out.println("hello world");
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		String hql = "delete database hibernate;create database hibernate;";
		session.createNativeQuery(hql).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

}
