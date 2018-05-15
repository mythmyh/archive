package com.test.junit;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ThreadTech {
	
	public static void main(String[] args) {
		 StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.configure() // configures settings from hibernate.cfg.xml
					.build();
			SessionFactory sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
	     new Thread(new HasSessionThread(sessionFactory)).start();
	     new Thread(new HasSessionThread(sessionFactory)).start();

	     new Thread(new HasSessionThread(sessionFactory)).start();

	     new Thread(new HasSessionThread(sessionFactory)).start();
           try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     sessionFactory.close();
	}

}
