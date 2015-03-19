package com.zz.backend.cache;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.zz.backend.util.RunningProfile;

@Service
@Profile({RunningProfile.Dev,RunningProfile.SimpleCache,RunningProfile.Default})
public class MapCache implements Cache {
	private Map<String,String> cache =  Collections.synchronizedMap(new WeakHashMap<String,String>());
	@Override
	public void put(String key, String value) {
		cache.put(key, value);
	}

	@Override
	public String get(String key) {
		return cache.get(key);
	}

}
