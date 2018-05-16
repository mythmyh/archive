package com.translation.prime;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.translation.utils.SaveSound;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ServerEndpoint("/websocketMain")
public class WebSocketMain {
	public static String url;

	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {

		// Print the client message for testing purposes
		// System.out.println("Received: " + message);

		// Send the first message to the client

		message = URLDecoder.decode(message, "utf-8");
		// 声音文件有可能不能用，判断是更新文件还是翻译网址

		try {
			session.getBasicRemote().sendText("准备翻译...<br>");
			Juicy jx = new Juicy(session, url);
			jx.transformer();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println("本篇文章未能成功翻译！");
		}

		// Send 3 messages to the client every 5 seconds
		// int sentMessages = 7;
		// while (sentMessages < 13) {
		// Thread.sleep(5000);
		// session.getBasicRemote().sendText("<div data-id=\""+
		// sentMessages+"\">"+sentMessages+"</div");
		// sentMessages++;
		// }

	}

	public WebSocketMain() {
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