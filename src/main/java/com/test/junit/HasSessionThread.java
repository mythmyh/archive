package com.test.junit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.LinkedList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.translation.prime.GenerateHtml;
import com.translation.utils.SaveSound;

public class HasSessionThread extends Thread {

	public static String s = System.getProperty("catalina_home");
	public SessionFactory sessionFactory;
	public static int i = 0;

	public HasSessionThread(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {

		// Content content = new Content();
		// content.setRawUrl(sawUrl);
		// content.setTitle(title);
		// content.setTimestamp(time);

		// Phrase phrase2 = new Phrase("alleged", "根据", time);
		// Paragraph pa = new Paragraph(time, content);
		// pa.getSet1().add(phrase1);
		// pa.getSet1().add(phrase2);
		// phrase1.getParagraphs().add(pa);

		// session.save(content);

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

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		// ApplicationContext abc = new
		// FileSystemXmlApplicationContext("classpath:spring-servlet.xml");
		// System.out.print(abc.toString());
		//
		// System.setProperty("webdriver.chrome.driver","C:\\Users\\mythm\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		// WebDriver driver = null;
		//
		// // TODO Auto-generated method stub
		// // String window = "window.open('" + str + "')";
		// // ((JavascriptExecutor) dr).executeScript(window);
		// driver = new ChromeDriver();
		//
		// driver.get("http://www.baidu.com");
//		File filex = new File(HasSessionThread.class.getResource("").getPath());
//		String root = URLDecoder.decode(filex.getAbsolutePath(), "utf-8").replaceAll("\\+", "%20").substring(0, 22);
//		String jsp = root + "src\\main\\webapp\\index.jsp";
//
//		String java = root + "src\\main\\java\\com\\translation\\prime\\Gesture.java";
//		changeJSP(jsp, "showMessage");
//		changeJava(java, "ActiveAction", "showMessage");
		File soundtrack = new File(s + "\\webapps\\elimination\\final\\soundtrack\\news\\" + 50);
		System.out.println(soundtrack.getAbsolutePath());
		System.out.println(soundtrack.exists());
	}

	public static void deleter() {
		File soundtrack = new File(s + "\\webapps\\elimination\\final\\soundtrack\\news\\");
		File date = new File(s + "\\webapps\\elimination\\final\\date\\");
		File content = new File(s + "\\webapps\\elimination\\final\\content\\");
		SaveSound.deleteDir(soundtrack);
		SaveSound.deleteDir(date);
		SaveSound.deleteDir(content);
		soundtrack.mkdirs();
		date.mkdirs();
		content.mkdirs();
	}

	public static void changeJSP(String file, String function) throws IOException {

		File file1 = new File(file);
		// Reader reader = new FileReader(file1);
		InputStream in = new FileInputStream(file1);
		InputStreamReader isr = new InputStreamReader(in, "utf-8");// 或者utf-8
		BufferedReader br = new BufferedReader(isr);
		String str = null;

		LinkedList<String> strings = new LinkedList<>();
		while ((str = br.readLine()) != null) {
			strings.add(str);

		}
		System.out.println(strings.size());
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file1), "utf-8");
		BufferedWriter bufw = new BufferedWriter(fw);
		// for (String str1 : strings) {
		// bufw.write(str1);
		// bufw.newLine();
		//
		// }
		for (int i = 0; i < strings.size(); i++) {
			bufw.write(strings.get(i));
			bufw.newLine();
			if (i == strings.size() - 3) {
				bufw.write("<a href=\"" + function + "\">" + function + "</a><br><br>");
				bufw.newLine();
			}

		}
		bufw.flush();
		bufw.close();
		br.close();

	}

	public static void changeJava(String file, String clazz, String function) throws IOException {
		File filex = new File(HasSessionThread.class.getResource("").getPath());
		String root = URLDecoder.decode(filex.getAbsolutePath(), "utf-8").replaceAll("\\+", "%20").substring(0, 22);
		String module = root + "src\\main\\java\\config\\template";

		File template = new File(module);
		System.out.println(template.exists());
		// Reader reader = new FileReader(file1);
		InputStream tmpin = new FileInputStream(template);
		InputStreamReader tmpreader = new InputStreamReader(tmpin, "utf-8");// 或者utf-8
		BufferedReader tmpbr = new BufferedReader(tmpreader);

		File file1 = new File(file);
		// Reader reader = new FileReader(file1);
		InputStream in = new FileInputStream(file1);
		InputStreamReader isr = new InputStreamReader(in, "utf-8");// 或者utf-8
		BufferedReader br = new BufferedReader(isr);
		String str = null;

		LinkedList<String> strings = new LinkedList<>();
		while ((str = br.readLine()) != null) {
			strings.add(str);

		}
		System.out.println(strings.size());
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file1), "utf-8");
		BufferedWriter bufw = new BufferedWriter(fw);
		// for (String str1 : strings) {
		// bufw.write(str1);
		// bufw.newLine();
		//
		// }
		for (int i = 0; i < strings.size(); i++) {
			bufw.write(strings.get(i));
			bufw.newLine();
			if (i == strings.size() - 2) {
				String strtmp = null;
				while ((strtmp = tmpbr.readLine()) != null) {
					if (strtmp.contains("${class}"))
						strtmp = strtmp.replace("${class}", clazz);
					if (strtmp.contains("${function}"))
						strtmp = strtmp.replace("${function}", function);
					bufw.write(strtmp);
					bufw.newLine();

				}
			}

		}
		bufw.flush();
		bufw.close();
		tmpbr.close();
		br.close();

	}
}