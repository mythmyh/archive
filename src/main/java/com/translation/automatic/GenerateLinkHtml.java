package com.translation.automatic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.process.utils.PathFormat;

public class GenerateLinkHtml {

	public void generateLink() throws IOException {
		File file1 = new File(GenerateHtml.class.getResource("").getPath() + "/secondmenu.html");
		Reader reader = new FileReader(file1);
		BufferedReader br = new BufferedReader(reader);
		String path = PathFormat.rootPath(GenerateHtml.class);
		FileWriter fw = new FileWriter(path.substring(0, path.length() - 1) + "//final//index.html");
		BufferedWriter bufw = new BufferedWriter(fw);
		String str1 = null;
		while ((str1 = br.readLine()) != null) {

			if (str1.contains("<ul style=\"display: none\">")) {
				bufw.write("<ul style=\"display: block\">");
				bufw.newLine();

				Date now = new Date();
				SimpleDateFormat f1 = new SimpleDateFormat("MM");
				SimpleDateFormat f2 = new SimpleDateFormat("dd");
				boolean first = true;
				int year = 2018;
				int month = Integer.parseInt(f1.format(now));
				int day = Integer.parseInt(f2.format(now));
				for (; month < 13; month++) {
					bufw.write("<li><a href=\"#\" class=\"inactive active\">" + month + "月</a>");
					bufw.write("<ul>");
					bufw.newLine();
					String str = year + "-" + month + "-" + day;
			
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					Date d;
					try {
						d = (Date) f.parseObject(str);
						Calendar c = Calendar.getInstance();
						c.setTime(d);
						// System.out.println(month + " 月==============");
						int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
						if (first) {
							for (int i = day; i <= totalDays; i++) {
								c.set(Calendar.DAY_OF_MONTH, i);
								Date date = c.getTime();
								String day1 = f.format(date);
								bufw.write("	<li><a href=\"\\date\\" + day1 + ".html\">" + day1 + "号</a></li>");
							}
							first = false;
						} else {
							for (int i = 1; i <= totalDays; i++) {
								c.set(Calendar.DAY_OF_MONTH, i);
								Date date = c.getTime();
								String day1 = f.format(date);
								bufw.write("	<li><a href=\"\\date\\" +  day1 + ".html\">" + day1 + "号</a></li>");
							}

						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bufw.write("</ul>");
					bufw.newLine();
				}

			} else {
				bufw.write(str1);
				bufw.newLine();

			}
		}
		bufw.write("</ul>");
		bufw.newLine();
		br.close();
		bufw.flush();
		bufw.close();

	}

	public static void main(String[] args) {

		try {
			new GenerateLinkHtml().generateLink();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
