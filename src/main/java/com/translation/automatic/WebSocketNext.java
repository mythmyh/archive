package com.translation.automatic;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

@ServerEndpoint("/websocketNext")
public class WebSocketNext {
	public static Map<String, String> urls;;

	@OnMessage
	public void onMessage(String message, Session session)  {

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
		// 声音文件有可能不能用，判断是更新文件还是翻译网址
		// for (String url1 : urls.keySet()) {
		// try {
		// Juicy jx = new Juicy(session, url1);
		// jx.transformer();
		// i++;
		// if (i == 7) {
		// break;
		// }
		// } catch (Throwable e) {
		// // TODO Auto-generated catch block
		// }
		// }

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