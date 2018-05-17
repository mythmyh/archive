package com.translation.automatic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.process.utils.PathFormat;
import com.unit.entities.Content;
import com.unit.entities.Paragraph;
import com.unit.entities.Phrase;

public class GenerateHtml extends Thread {
	public SessionFactory sessionFactory;
	public static int i = 0;

	public GenerateHtml(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void generate(int cid) {

		try {
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			String hql = "from Paragraph c where c.content.id = :id";
			List<Paragraph> paragraphs = session.createQuery(hql, Paragraph.class).setParameter("id", cid)
					.getResultList();

			// Integer s = 0;
			// for (Paragraph p : paragraphs) {
			// if (s < p.getIndex())
			// s = p.getIndex();
			// }
			// System.out.println("最后一个段落是：" + s);
			// if (s != 0)
			// return;
			Collections.sort(paragraphs);
			Content content = paragraphs.get(0).getContent();
			System.out.println(
					content.getId() + " 相等吗？？map:" + (content.getTotalParagraphs() + ",实际插入:" + paragraphs.size()));
			
			if( content.getTotalParagraphs()!= paragraphs.size()) {
				System.out.println("数据不完整！");
				return;
			}
			int contentid = content.getId();
			String title = content.getTitle();
			File file1 = new File(GenerateHtml.class.getResource("").getPath() + "/chinese.jsp");
			Reader reader = new FileReader(file1);
			BufferedReader br = new BufferedReader(reader);
			String path = PathFormat.rootPath(GenerateHtml.class);

			// FileWriter fw = new FileWriter(path.substring(0, path.length() -
			// 1) + "//content//" + contentid + ".html");

			OutputStreamWriter fw = new OutputStreamWriter(
					new FileOutputStream(
							path.substring(0, path.length() - 1) + "//final//content//" + contentid + ".html"),
					"utf-8");
			BufferedWriter bufw = new BufferedWriter(fw);

			String str = null;
			int i = 0, v = 0;
			Set<String> words = new LinkedHashSet<>();
			while ((str = br.readLine()) != null) {
				if (str.contains("button")) {
					bufw.write(str);
					bufw.newLine();
					for (Paragraph p : paragraphs) {
						Set<Phrase> phrases = p.getSet1();
						String eng = URLDecoder.decode(p.getRawContent(), "utf-8");

						for (Phrase word : phrases) {
							words.add(word.getWord());
							if (!word.isSingleWord()) {
								eng = eng.replace(word.getWord(),
										"<span  class=\"ss\" id=\" " + i + "\" onclick=\"hide(this)\">" + word.getWord()
												+ "</span><span class=\"word\" id=\"" + i
												+ "\" style=\"display: none;\">【" + word.getTranslate_in()
												+ "】</span>");

							} else {
								eng = eng.replace(word.getWord(),
										"<span  id=\" " + i + "\" onclick=\"hide(this)\">" + word.getWord()
												+ "</span><span id=\"" + i
												+ "\" style=\"display: none;\" class=\"word\">【"
												+ word.getTranslate_in() + "】</span>");
							}
							i++;
						}

						String j = "    <div><p>  " + p.getTranslation() + "</p><br><p >" + v + " " + eng
								+ "<span class=\"icon\" onmouseover=\"playSolo(this.id)\" id=\"" + v
								+ ".mp3\"></span></p><br><br></div>";
						bufw.write(j);
						bufw.newLine();
						v++;
					}

				} else if (str.contains("/soundtrack/news/")) {
					str = str.replace("/soundtrack/news/", "/soundtrack/news/" + contentid + "/");
					bufw.write(str);
					bufw.newLine();
				} else if (str.contains("<title>")) {

					bufw.write("<title>" + title + "</title>");
					bufw.newLine();
				} else {

					bufw.write(str);
					bufw.newLine();
				}
			}
			bufw.write("<p>本文一共有：" + words.size() + "个单词或词组，" + paragraphs.size() + "个段落。</p>");
			bufw.newLine();
			bufw.write("<p>Copyrights Reserved</p>");
			br.close();
			bufw.flush();
			bufw.close();

			// Phrase phrase = session.get(Phrase.class, "admitted");

			// for(Phrase p: lists){
			// System.out.println(p.getWord()+"--->"+p.getTranslate_in());
			// }
			// session.save(phrase1);
			// content.setTotalParagraphs(paragraphs.size());
			content.setTotalPhrases(words.size());
			session.save(content);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// session.save(pa);
		// 为当前线程绑定一个session对象,让dao中使用 getCurrentSession的方法可以获取到对应的session

	}

	// 复制文件
	public void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, n);
		}

		ins.close();
		out.close();
	}
}