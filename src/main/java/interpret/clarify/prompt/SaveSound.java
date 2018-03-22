package interpret.clarify.prompt;
//保存声音文件
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class SaveSound {
    	public static String s = System.getenv("catalina_home");

	public SaveSound() {
		super();

		// TODO Auto-generated constructor stub
	}

	public void save(int i, String source) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		URL url = new URL(

				"http://fanyi.baidu.com///gettts?lan=en&text=" + source);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误

		// 得到输入流
		InputStream in = conn.getInputStream();
		//System.out.print(in.available());
		// 获取自己数组

		byte[] data = new byte[1024];
		int len = 0;
		while ((len = in.read(data)) != -1) {
			out.write(data, 0, len);
		}

		byte[] sound = out.toByteArray();

		File soundtrack = new File(s + "\\webapps\\elimination\\soundtrack\\news\\" + i + ".mp3");

		FileOutputStream output = new FileOutputStream(soundtrack);
		output.write(sound);
		in.close();
		output.close();

		// System.out.println(element.getText());
		// 关闭浏览器
		// driver.close();
	}


	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

}
