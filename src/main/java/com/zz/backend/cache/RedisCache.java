package com.zz.backend.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.zz.backend.util.RunningProfile;

@Service
@Profile({RunningProfile.Production,RunningProfile.ExternalCache})
public class RedisCache implements Cache {

	@Autowired
	private StringRedisTemplate template;
	
	@Override
	public void put(String key,String value){
		template.opsForValue().set(key, value);
	}

	@Override
	public String get(String key) {
		return template.opsForValue().get(key);
	}
}
