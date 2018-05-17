package com.translation.prime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.process.utils.PathFormat;
import com.unit.entities.Content;

public class GenerateListPage {
	public SessionFactory sessionFactory;

	public GenerateListPage(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void generateLink() throws IOException {
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		Date today = new Date();
		SimpleDateFormat dx = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from Content";
		List<Content> contents = session.createQuery(hql, Content.class).getResultList();

		File file1 = new File(GenerateHtml.class.getResource("").getPath() + "/list.html");
		Reader reader = new FileReader(file1);
		BufferedReader br = new BufferedReader(reader);
		String path = PathFormat.rootPath(GenerateHtml.class);
		// 防止乱码
		OutputStreamWriter fw = new OutputStreamWriter(
				new FileOutputStream(
						path.substring(0, path.length() - 1) + "//final//date//" + dx.format(today) + ".html"),
				"utf-8");
		BufferedWriter bufw = new BufferedWriter(fw);
		String str1 = null;
		while ((str1 = br.readLine()) != null) {
			if (str1.contains("<div class=\"entry\">")) {

				bufw.write(str1);
				bufw.newLine();
				for (Content c : contents) {
					if (dx.format(today).equals(dx.format(c.getTimestamp()))) {
						if (c.getId() == null)
							session.delete(c);
						bufw.write("<a href =\"..\\content\\" + c.getId() + ".html\">" + c.getTitle() + "</a><br>");
						bufw.newLine();
						bufw.write(c.toString() + "<br>");
						bufw.newLine();

					}

				}

			} else {
				bufw.write(str1);
				bufw.newLine();
			}

		}
		br.close();
		bufw.flush();
		bufw.close();
		session.close();
		sessionFactory.close();

	}

	public static void main(String[] args) throws IOException {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures
				// settings
				// from
				// hibernate.cfg.xml
				.build();
		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		new GenerateListPage(sessionFactory).generateLink();
		sessionFactory.close();
	}
}
