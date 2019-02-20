package test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

import redis.clients.jedis.Jedis;

public class rdiesTest {
/*	@Test
	public void testJedis() throws Exception {
		// 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。
		Jedis jedis = new Jedis("192.168.44.101", 6379);
		// 第二步：使用Jedis对象操作数据库，每个redis命令对应一个方法。
		
		String result = jedis.get("k");
		// 第三步：打印结果。
		System.out.println(result);
		// 第四步：关闭Jedis
		jedis.close();
	}*/

	 /*
		@Test
		public void testdanji(){
			//1.初始化spring容器
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
			//2.获取实现类实例
			JedisClient bean = context.getBean(JedisClient.class);
			//3.调用方法操作
			bean.set("jedisclientkey11", "jedisclientkey");
			System.out.println(bean.get("jedisclientkey11"));
		}*/
	
	
}
