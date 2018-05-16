package com.translation.automatic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.Session;
import javax.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.translation.utils.DriverMain;
import com.translation.utils.SaveSound;
import com.unit.entities.Content;
import com.unit.entities.Paragraph;
import com.unit.entities.Phrase;

//主要线程类
public class Critical implements Runnable {

	static AtomicInteger i = new AtomicInteger(1);
	static HttpSession session2;
	public static Content content;
	String eng;
	int index;
	Session session;
	public SessionFactory sessionFactory;

	public Critical(String eng, int index, Session session, SessionFactory sessionFactory) {
		this.eng = eng;
		this.index = index;
		this.session = session;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {

		Timestamp time1 = new Timestamp(System.currentTimeMillis());
		Paragraph paragraph = null;
		WebDriver driver = null;

		// TODO Auto-generated method stub
		// String window = "window.open('" + str + "')";
		// ((JavascriptExecutor) dr).executeScript(window);
		ChromeOptions option = new ChromeOptions();
		/*
		 * 隐藏浏览器窗口
		 */
		option.addArguments("headless");
		option.addArguments("disable-infobars");
		// option.addArguments("user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User
		// Data");
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setCapability(ChromeOptions.CAPABILITY, option);

		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//

		// WebElement container =
		// dr.findElement(By.className("keywords-container"));
		// System.out.println(container.getText());

		String encodeString = null;
		try {
			// 转换字符串
			encodeString = URLEncoder.encode(eng, "utf-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 保存声音文件
		SaveSound save = new SaveSound();
		try {
			save.save(index, encodeString);
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			System.out.println("保存出错！");
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {

			driver.get("http://fanyi.baidu.com/?aldtype=16047#en/zh/" + encodeString);
		} catch (Exception e) {

		}

		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {

		}
		LinkedHashSet<String> words = new LinkedHashSet<>();
		WebElement container2=null;
		try{
		 container2 = driver.findElement(By.className("target-output"));
		}catch(Exception e){
			
		}
		String chinese=null;
		if(container2 != null ){
			chinese=container2.getText();
		}else {
			chinese="服务器未能响应！";
			
		}
		

		//
		// 给重要单词上色
		Map<String, String> couples = new HashMap<>();
		try {
			couples = new DriverMain().getWords(driver);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//if (couples != null && couples.size() > 0) {
		 {
			org.hibernate.Session sessionx = sessionFactory.openSession();
			sessionx.getTransaction().begin();
			paragraph = new Paragraph(time1, content,index);
			paragraph.setTranslation(chinese);
			
			paragraph.setRawContent(encodeString);
			// encodeString =URLDecoder.decode(encodeString, "utf-8");
			for (String se : couples.keySet()) {
				int x = i.getAndAdd(1);
				int c = i.getAndAdd(1);
				String jk = se.trim();
				Phrase phrase1;

				try {
			
					if ((phrase1 = sessionx.get(Phrase.class, jk)) == null) {
						if (words.add(jk)) {
							Phrase phrase = new Phrase(jk, couples.get(se), time1);
							if (jk.matches("\\w+\\W\\w+")) {
								phrase.setSingleWord(false);
							} else {
								phrase.setSingleWord(true);
							}
							phrase.getParagraphs().add(paragraph);
							paragraph.getSet1().add(phrase);
							sessionx.save(phrase);
						}
					} else {
						paragraph.getSet1().add(phrase1);
					}

				} catch (Exception e) {
					e.printStackTrace();

				} finally {

				}

				// 处理字符串加class，js点击事件等
				if (jk.matches("\\w+\\W\\w+")) {
					eng = eng.replace(se,
							"<span  class=\"ss\" id=\" " + x + "\" onclick=\"hide(this)\">" + se
									+ "</span><span class=\"word\" id=\"" + c + "\" style=\"display: none;\">【"
									+ couples.get(se) + "】</span>");

				} else {
					eng = eng.replace(se, "<span  id=\" " + x + "\" onclick=\"hide(this)\">" + se + "</span><span id=\""
							+ c + "\" style=\"display: none;\" class=\"word\">【" + couples.get(se) + "】</span>");
				}
			}
			sessionx.save(paragraph);
			sessionx.getTransaction().commit();
			sessionx.close();

		}

		// Send the first message to the client
		String j = "    <div data-id=\"" + index + "\"><p>" + index + "  " + chinese+ "</p><br><p >" + eng
				+ "<span class=\"icon\" onmouseover=\"playSolo(this.id)\" id=\"" + index
				+ ".mp3\"></span></p><br><br></div>";

		try {
			try {
//				synchronized (session) {
//					session.getBasicRemote().sendText(j);
//				}
				Thread.sleep(3000);
			} catch ( InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {

			driver.close();
			driver.quit();
		}
	}
}
