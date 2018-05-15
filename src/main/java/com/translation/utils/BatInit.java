package com.translation.utils;
//初始化启动selenium hub和node
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;


public class BatInit {

	Process p1;
	Process p2;
	public BatInit() throws IOException, InterruptedException {
	

		URL uri = BatInit.class.getResource("hub.bat");
		String jpg = uri.toString().substring(6);
		 p1 = Runtime.getRuntime().exec("cmd /k start " + jpg);
		uri = BatInit.class.getResource("node.bat");
		Thread.sleep(3000);
		p2 = Runtime.getRuntime().exec("cmd /k start " + uri.toString().substring(6));
	}
	public void destroy() {
		p1.destroy();
		p2.destroy();
	}
}