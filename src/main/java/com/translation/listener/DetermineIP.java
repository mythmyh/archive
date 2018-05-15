package com.translation.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.util.LinkedList;

public class DetermineIP {
	static InetAddress address = null;

	static String hostAddress = null;

	public static void changeIPAddress(String file) throws IOException {
		try {
			if (address == null) {
				address = InetAddress.getLocalHost();
				hostAddress = address.getHostAddress();
			}
			

		} catch (Exception e) {
			System.out.println("-----------------DIP");
		} finally {

		}
		File file1 = new File(file);
		// Reader reader = new FileReader(file1);
		InputStream in = new FileInputStream(file1);
		InputStreamReader isr = new InputStreamReader(in, "utf-8");// 或者utf-8
		BufferedReader br = new BufferedReader(isr);
		String str = null;

		LinkedList<String> strings = new LinkedList<>();
		while ((str = br.readLine()) != null) {
			if (str.contains("192.168.1.110"))
				str = str.replace("192.168.1.110", hostAddress);
			strings.add(str);

		}
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file1), "utf-8");
		BufferedWriter bufw = new BufferedWriter(fw);
		for (String str1 : strings) {
			bufw.write(str1);
			bufw.newLine();

		}
		bufw.flush();
		bufw.close();
		br.close();
		address = null;

	}

}
