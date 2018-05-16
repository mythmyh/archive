package com.translation.prime;

import java.io.IOException;
import java.sql.Timestamp;
//多线程类使用了cyclicbarrier，轮询开启线程
import java.util.LinkedHashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.websocket.Session;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.translation.utils.SaveSound;
import com.unit.entities.Content;

class WaitThread implements Runnable {
	public CyclicBarrier barrier;

	public WaitThread(CyclicBarrier barrier) {
		super();
		this.barrier = barrier;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Horse implements Runnable {
	SessionFactory sessionFactory;
	Session session;
	String translate;

	public CyclicBarrier barrier;
	public static LinkedHashMap<Integer, String> abc;
	public ExecutorService exec;
	int index;
	static int ex;

	public Horse(CyclicBarrier barrier, ExecutorService exe, Session session, SessionFactory sessionFactory) {
		this.barrier = barrier;
		this.exec = exe;
		this.sessionFactory = sessionFactory;
		// TODO Auto-generated constructor stub
		this.session = session;
	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted() && (abc.size() > 0)) {

				synchronized (abc) {
					index = abc.size();

					if (index > 0) {

						translate = abc.get(--index);

						abc.remove(index);
						if (abc.size() == 0) {
							for (int i = 0; i < ex; i++) {
								new Thread(new WaitThread(barrier)).start();
							}

						}
					}

				}

				// barrier靠后
				if (translate != null && (!translate.equals(" "))) {

					new Thread(new Critical(translate, index, session, sessionFactory)).run();
				}
				barrier.await();

			}
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block

		}
	}

}

public class CarrierMain {
	String rawUrl = null;
	String title = null;
	public int contentid;
	StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures
																						// settings
																						// from
																						// hibernate.cfg.xml
			.build();
	SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	private CyclicBarrier barrier;
	public LinkedHashMap<Integer, String> map;
	ExecutorService exec = Executors.newCachedThreadPool();
	Session session;

	public CarrierMain(int t, LinkedHashMap<Integer, String> map, Session session, String rawUrl, String title) {
		this.rawUrl = rawUrl;
		this.title = title;
		this.session = session;
		this.map = map;
		Content content = new Content(title, rawUrl, new Timestamp(System.currentTimeMillis()));

		System.out.println(content.getId());

		org.hibernate.Session sessionx = sessionFactory.openSession();
		sessionx.getTransaction().begin();
		sessionx.save(content);
		Critical.content = content;
		SaveSound.contentid = content.getId();
		contentid = content.getId();
		SaveSound.makeDirs();
		sessionx.getTransaction().commit();
		sessionx.close();
		barrier = new CyclicBarrier(t, new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					Thread.sleep(2000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (Horse.abc.size() == 0) {
					new GenerateHtml(sessionFactory).generate(contentid);
				
					sessionFactory.close();
					exec.shutdownNow();

				}

			}

		});

		Horse.abc = map;

		for (int i = 0; i < t; i++) {
			exec.execute(new Horse(barrier, exec, session, sessionFactory));

		}
		// abc.add("consecutive");
		// exec.execute(new Horse(barrier,abc));
		// abc.add("abandone");
		// exec.execute(new Horse(barrier,abc));
		// abc.add("riqiu");
		// exec.execute(new Horse(barrier,abc));
		//
	}
}
