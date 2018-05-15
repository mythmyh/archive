package com.translation.listener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.translation.utils.BatInit;
import com.translation.utils.SaveSound;

//监听器启动两个bat
public class BatInitListener implements ServletContextListener {
	public static String s = System.getenv("catalina_home");
	BatInit test;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//
		String changeNode = s + "\\webapps\\elimination\\WEB-INF\\classes\\com\\translation\\utils\\node.bat";
		try {
			DetermineIP.changeIPAddress(changeNode);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			
		}
		String changeJsp = s + "\\webapps\\elimination\\\\WEB-INF\\view\\chinese.jsp";
		try {
			DetermineIP.changeIPAddress(changeJsp);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			
		}
		//获取ip地址会占用五秒钟
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub
		Timestamp t = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dx = new SimpleDateFormat("yyyy-MM-dd");
		String today = dx.format(t);

		File file1 = new File(BatInitListener.class.getResource("").getPath() + "/date.txt");
		String fileDate = null;
		BufferedReader br = null;
		try {
			Reader reader = new FileReader(file1);
			br = new BufferedReader(reader);
			fileDate = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (fileDate != null && fileDate.equals(today)) {
			File soundtrack = new File(s + "\\webapps\\elimination\\final\\soundtrack\\news\\");
			File date = new File(s + "\\webapps\\elimination\\final\\date\\");
			File content = new File(s + "\\webapps\\elimination\\final\\content\\");
			soundtrack.mkdirs();
			date.mkdirs();
			content.mkdirs();
		} else {
			BufferedWriter bufw = null;
			try {
				File soundtrack = new File(s + "\\webapps\\elimination\\final\\soundtrack\\news\\");
				File date = new File(s + "\\webapps\\elimination\\final\\date\\");
				File content = new File(s + "\\webapps\\elimination\\final\\content\\");
				SaveSound.deleteDir(soundtrack);
				SaveSound.deleteDir(date);
				SaveSound.deleteDir(content);
				soundtrack.mkdirs();
				date.mkdirs();
				content.mkdirs();
				OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file1), "utf-8");
				bufw = new BufferedWriter(fw);
				bufw.write(today);

			} catch (Exception e) {

			} finally {
				if (bufw != null)
					try {
						bufw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		try {
			test = new BatInit();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		test.destroy();
	}

}
