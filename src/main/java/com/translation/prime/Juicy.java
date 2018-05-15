package com.translation.prime;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.translation.utils.SaveSound;

//封装类，websocket打开时候启动访问
public class Juicy {
	Session session;
	String title;
	// static SelectDriver sd = SelectDriver.instance();
	// static WebDriver driver = sd.driverName("chrome");
	//
	// boolean ElementExist(By Locator) {
	// try {
	// driver.findElement(Locator);
	// return true;
	// } catch (org.openqa.selenium.NoSuchElementException ex) {
	// return false;
	// }
	// }
	String url;

	public Juicy(Session session, String url) {
		super();
		this.session = session;
		this.url = url;
	}

	/*
	 * 处理字符串，把空格替换为%20
	 */
	public static WebDriver chromeDriver() {
		WebDriver driver = null;
		// TODO Auto-generated method stub
		// String window = "window.open('" + str + "')";
		// ((JavascriptExecutor) dr).executeScript(window);
		ChromeOptions option = new ChromeOptions();
		option.addArguments("headless");
		option.addArguments("disable-infobars");
		// option.addArguments("user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User
		// Data");
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setCapability(ChromeOptions.CAPABILITY, option);

		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
			return driver;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void transformer() throws Throwable {
		WebDriver driver = null;
		// TODO Auto-generated method stub
		// String window = "window.open('" + str + "')";
		// ((JavascriptExecutor) dr).executeScript(window);
		ChromeOptions option = new ChromeOptions();
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

		int x = 0;

		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		try {
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			driver.get(url);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			session.getAsyncRemote().sendText("    <p class=\"hometown\">正在扫描文章...</p>");
		} catch (Exception e) {
		} finally {
			// foxnews换成article-body,abcnews换成artile-copy
			try {
				WebElement head_line = driver.findElement(By.className("head1"));
				title = head_line.getText();
			} catch (Exception e) {
				System.out.print("未能获取标题！");
				title = "error";
			}

			WebElement element = driver.findElement(By.className("article-body"));
			Thread.sleep(3000);
			List<WebElement> sets = element.findElements(By.tagName("p"));
			for (WebElement ele : sets) {

				// ==0是为了适配abcnews才加的
				if (ele.getText().equals(" ") || (ele.getText().length() == 0)) {
					continue;
				} else {
					int p = x++;
					map.put(p, new String(ele.getText()));
				}
			}

			driver.close();
			driver.quit();

		}

		Thread.sleep(2000);

		String s = SaveSound.s + "\\webapps\\elimination\\soundtrack\\news\\";
		File f = new File(s);
		if (!f.exists()) {
			File se = new File(s);
			se.mkdirs();
		}

		int t = 0;
		if (map.size() > 5) {
			if (map.size() % 6 != 0) {
				t = 6 - map.size() % 6;
			}
			session.getBasicRemote().sendText("<p class=\"hometown\">计算补偿线程...</p>");
			session.getBasicRemote()
					.sendText("<input type =\"hidden\" name =\"heat\"value=\"" + map.size() + "\" id=\"csb\">");
			System.out.println("需要" + t + "个补偿线程...");
			Horse.ex = t;

			new CarrierMain(6, map, session, url, title);
			// // barrier加速线程
		} else {
			new CarrierMain(map.size(), map, session, url, title);
		}

	}

	public Juicy(Session session) {
		super();
		this.session = session;
	}
}
