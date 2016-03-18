# springcache
## 简介
　使用spring 自带的cache库做为缓存．demo是通过网上的例子，通过javaconfig改造而来．没有任何xml代码．
　使用过程中有一个坑，如果没加上，会无法调用缓存．
　
### 坑来了
在javaconfig中，需要增加注解．
```
@EnableCaching
```
否则无法得到结果．

### JavaConfig
```
@Configuration
@EnableCaching
public class JavaConfig 
{
  @Bean
  public AccountService accountService(){
	  return new AccountService();
  }
  
  @Bean
  public SimpleCacheManager cacheManager(){
	  SimpleCacheManager cacheManager = new SimpleCacheManager();
	  cacheManager.setCaches(caches());
	  return cacheManager;
  }
  
  @Bean
  public  Set<ConcurrentMapCache> caches() {
	  Set<ConcurrentMapCache> caches = new HashSet<ConcurrentMapCache>();
	  
	  caches.add(accountCache());
	  caches.add( defaultCache());
	  
	  
	  return caches;
  }
  
  @Bean(name="accountCache")
  public ConcurrentMapCache accountCache(){
	  ConcurrentMapCache accountCache = new ConcurrentMapCache("accountCache");
	  return  accountCache;
  }
  
  @Bean
  public ConcurrentMapCache defaultCache(){
	  ConcurrentMapCache defaultCache = new ConcurrentMapCache("defaultCache");
	  return  defaultCache;
  }
}
```

### Bean
```
public class Account {
	private int id;
	private String name;

	public Account(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
```


### Service
```
public class AccountService { 
  @Cacheable(value="accountCache")// 使用了一个缓存名叫 accountCache 
  public Account getAccountByName(String userName) { 
    // 方法内部实现不考虑缓存逻辑，直接实现业务
    System.out.println("real query account."+userName); 
    return getFromDB(userName); 
  } 
 
  private Account getFromDB(String acctName) { 
    System.out.println("real querying db..."+acctName); 
    return new Account(acctName); 
  } 
}
```

### Main
```
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
```


### 运行
启动运行，第二次直接从缓存中拿数据了．
```
first query...real query account.somebody
real querying db...somebody
second query...
```
