import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
 
public class Test {
 
	public static void main(String[] args){
		List<String> strList = new ArrayList<String>();
		strList.add("string1");
		strList.add("string2");
		strList.add("string3");
		strList.add("string4");
		strList.add("string5");
		strList.add("string6");
		
	// 操作方式1：while（Iterator）；报错
		/*Iterator<String> it = strList.iterator();
		while(it.hasNext()) {
			String s = it.next();
			if("string2".equals(s)) {
				strList.remove(s);
			}
		}*/
		
		// 解决方案1：使用Iterator的remove方法删除元素
		// 操作方式1：while（Iterator）：不报错
/*		Iterator<String> it = strList.iterator();
	while(it.hasNext()) {
			String s = it.next();
			if("string2".equals(s)) {
				it.remove();
			}
			System.out.println(it);
		}*/
		
		// 操作方式2：foreach（Iterator）；报错
//		for(String s : strList) {
//			if("string2".equals(s)) {
//				strList.remove(s);
//			}
//		}
		
		// 解决方案2：不使用Iterator遍历，注意索引的一致性
		// 操作方式3：for（非Iterator）；不报错；注意修改索引
		for(int i=0; i<strList.size(); i++) {
			String s = strList.get(i);
			if("string2".equals(s)) {
				strList.remove(s);
				System.out.println(strList.size());
				//strList.remove(i);
//				i--;// 元素位置发生变化，修改i
			}
		}
		
		// 解决方案3：新建一个临时列表，暂存要删除的元素，最后一起删除
//		List<String> templist = new ArrayList<String>();
//		for (String s : strList) {
//		    if(s.equals("string2")) {
//		        templist.add(s);
//		    }
//		}
//		// 查看removeAll源码，其使用Iterator进行遍历
//		strList.removeAll(templist);
		
		// 解决方案4：使用线程安全CopyOnWriteArrayList进行删除操作
//		List<String> strList = new CopyOnWriteArrayList<String>();
//		strList.add("string1");
//		strList.add("string2");
//		strList.add("string3");
//		strList.add("string4");
//		strList.add("string5");
//		strList.add("string6");
//		Iterator<String> it = strList.iterator();
//		while (it.hasNext()) {
//		    String s = it.next();
//		     if (s.equals("string2")) {
//		    	 strList.remove(s);
//		    }
//		}
		
	}	
}
