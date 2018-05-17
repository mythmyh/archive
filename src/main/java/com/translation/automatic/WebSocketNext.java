package com.translation.automatic;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.hibernate.SessionFactory;

import com.translation.utils.SaveSound;
import com.unit.entities.Content;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@ServerEndpoint("/websocketNext")
public class WebSocketNext {
	public static Map<String, String> urls;;
	static String s = System.getenv("catalina_home");
	static Content content;
	static Integer contentid = null;
	public static SessionFactory sessionFactory;

	@OnMessage
	public void onMessage(String message, Session session) {

		// Print the client message for testing purposes
		// System.out.println("Received: " + message);

		// Send the first message to the client
		try {
			session.getBasicRemote().sendText("<p class=\"hometown\">正在扫描文章...</p>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i = 0;

		// CarrierMain.urls2 = urls;
		//
		// CountDownLatch count = new CountDownLatch(7);
		// Juicy.count = count;
		// try {
		//
		// for (String url : urls.keySet()) {
		// i++;
		// if (i == 7)
		// break;
		// new Thread(new Juicy(session, url)).start();
		// }
		//
		// } catch (Exception e) {
		//
		// }

		for (String url1 : urls.keySet()) {
			Juicy jx = new Juicy(session, url1);
			try {
				jx.transformer();
				Thread.sleep(60000);

				// 如果下一个报错会把这一个好的给删除了，不可取，借刀杀人，嫁祸他人。

			} catch (Throwable e) {
				if (!sessionFactory.isClosed())
					sessionFactory.close();
				System.out.println("==本篇文章未能成功翻译！");

				File soundtrack = new File(s + "\\webapps\\elimination\\final\\soundtrack\\news\\" + contentid);

				File html = new File(s + "\\webapps\\elimination\\final\\content\\" + contentid + ".html");
				if (html.exists())
					html.delete();
				System.out.println("声音路径是否存在：" + soundtrack.exists());
				if (soundtrack.exists())
					SaveSound.deleteDir(soundtrack);
				e.printStackTrace();
				// TODO Auto-generated catch block

			} finally {
				contentid = null;
			}
			i++;
			if (i == 7) {
				break;
			}

		}

		System.out.println("---------->");

	}

	public WebSocketNext() {
		super();
		// TODO Auto-generated constructor stub
	}

	@OnOpen
	public void onOpen() {
		System.out.println("Client connected+1");
	}

	@OnClose
	public void onClose() {
		System.out.println("Connection closed");
	}
}