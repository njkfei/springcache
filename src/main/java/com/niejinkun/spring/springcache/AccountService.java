package com.niejinkun.spring.springcache;

import org.springframework.cache.annotation.CacheEvict; 
import org.springframework.cache.annotation.Cacheable; 

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