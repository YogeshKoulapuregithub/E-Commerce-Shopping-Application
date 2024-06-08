package com.yogesh.ecom.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yogesh.ecom.model.User;

@Configuration
public class CacheBeanConfig {

	@Bean
	CacheStore<String> optCache()
	{
		return new CacheStore<String>(5);
	}
	@Bean
	CacheStore<User> userCache()
	{
		return new CacheStore<User>(30);
	}

}
