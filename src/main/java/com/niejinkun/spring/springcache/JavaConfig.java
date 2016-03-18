package com.niejinkun.spring.springcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
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
