package com.niejinkun.spring.springcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
	 public static void main(String[] args) { 
			AbstractApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
	    
	     AccountService s = (AccountService) context.getBean("accountService"); 
	     // 第一次查询，应该走数据库
	     System.out.print("first query..."); 
	     s.getAccountByName("somebody"); 
	     // 第二次查询，应该不查数据库，直接返回缓存的值
	     System.out.print("second query..."); 
	     s.getAccountByName("somebody"); 
	     System.out.println(); 
	     
	     context.start();
	   } 
}
