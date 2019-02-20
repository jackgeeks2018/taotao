package fastDFSdemo;

import java.util.ArrayList;
import java.util.List;

public class test {

	public List<String> datesubstring(String date) {
		List<String> date1 = new ArrayList<String>();
		String[] split = date.split("-");
		String a = split[0];
		String b = split[1];
		StringBuffer sb = new StringBuffer();
		Integer a1 = Integer.parseInt(a.substring(0, 2));
		String a2 = b.substring(2);
		StringBuffer sb1 = new StringBuffer();
		Integer a3 = Integer.parseInt(b.substring(0, 2));
		String a4 = b.substring(2);
		
		if (a1 <= 12 && a1 >= 1) {
			sb.append("上午").append(a1).append(":").append(a2);
			date1.add(sb + "");

		} else if (a1 >= 12 && a1 <= 24) {

			sb.append("下午").append(a1 - 12).append(":").append(a2);
			date1.add(sb + "");

		}

		if (a3 <= 12 && a3 >= 1) {

			sb1.append("上午").append(a3).append(":").append(a4);
			date1.add(sb1 + "");

		} else if (a3 >= 12 && a3 <= 24) {

			sb1.append("下午").append(a3 - 12).append(":").append(a4);
			date1.add(sb1 + "");

		}
		return date1;
	}

	public static void main(String[] args) {
		test t = new test();
		List<String> datesubstring = t.datesubstring("2400-1200");
		for (String string : datesubstring) {
			System.out.println(5);
			System.out.println(string);
		}
	}

}
