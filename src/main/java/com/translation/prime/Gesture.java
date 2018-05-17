package com.translation.prime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openqa.selenium.WebElement;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.unit.entities.Content;

@Controller
public class Gesture {
	// fox新闻列表
	@RequestMapping("/abc")
	public String check() {
		return "abc";
	}

	public static Map<String, String> getUrls() {
		Map<String, String> urls = new HashMap<>();
		WebDriver driver = Juicy.chromeDriver();
		try {
			driver.get("http://www.foxnews.com/");

			// 设定过期时间
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			WebElement as = driver.findElement(By.className("main-content"));
			List<WebElement> elements = new ArrayList<>();
			elements = as.findElements(By.tagName("a"));
			// 处理图片网址加入map

			for (WebElement es : elements) {
				String url = es.getAttribute("href");
				if ((!url.contains("video")) && url.contains("2018")) {
					if (es.getText().length() > 0) {
						urls.put(url, es.getText());
					} else {
						urls.put(url, url);
					}

				}
			}
			System.out.println(urls.size());
		} catch (Exception e) {

		} finally {
			driver.close();
			driver.quit();
		}
		return urls;
	}

	@RequestMapping("/frank")
	public ModelAndView index(HttpSession session) {

		Map<String, String> urls = getUrls();

		ModelAndView model = new ModelAndView("translate", "urls", urls);

		return model;
	}

	// 翻译主要程序
	@RequestMapping("/translation")
	public ModelAndView vcs(HttpServletRequest request) {

		String uri = request.getParameter("url");
		WebSocketMain.url = uri;

		ModelAndView model = new ModelAndView("chinese");

		return model;
	}

	@RequestMapping("/generate")
	public String generate() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures
				// settings
				// from
				// hibernate.cfg.xml
				.build();
		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		GenerateListPage page = new GenerateListPage(sessionFactory);

		try {
			page.generateLink();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sessionFactory.close();

		return "abc";

	}

	// 翻译直接提供的网址
	@RequestMapping("/single")
	public ModelAndView single(HttpServletRequest request, @RequestParam(value = "news_url") String url) {

		// String uri = request.getParameter("url");
		WebSocketMain.url = url;
		ModelAndView model = new ModelAndView("automatic");

		return model;
	}

	// ABCnews列表
	@RequestMapping("/stab")
	public ModelAndView indefinite() {
		Map<String, String> urls = new HashMap<>();
		WebDriver driver = Juicy.chromeDriver();
		try {
			driver.get("http://abcnews.go.com/");

			// 设定过期时间
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			// 查找ul列表
			WebElement as = driver.findElement(By.className("headlines-ul"));
			List<WebElement> elements = new ArrayList<>();
			elements = as.findElements(By.tagName("a"));

			for (WebElement es : elements) {
				String url = es.getAttribute("href");

				if (es.getText().length() > 0) {
					urls.put(url, es.getText());
				} else {
					urls.put(url, url);
				}

			}
			System.out.println(urls.size());
		} catch (Exception e) {

		} finally {
			driver.close();
			driver.quit();
		}
		ModelAndView model = new ModelAndView("translate", "urls", urls);
		return model;
	}

	@RequestMapping("showMessage")
	public ModelAndView single() {
		ActiveAction.showMessage();
		ModelAndView model = new ModelAndView("abc");
		return model;
	}

	@RequestMapping("auto")
	public ModelAndView automatic() {
		Map<String, String> urls = getUrls();
		com.translation.automatic.WebSocketNext.urls=urls;
		ModelAndView model = new ModelAndView("automatic");
		return model;
	}
}
