package com.jobportal.utils;

import org.springframework.stereotype.Service;

@Service("cacheOperation")
public class CacheOperation {

//	@Autowired
//	private CacheConfig cc;
//
//	public CacheOperation() {
//		super();
//	}
//
//	public Boolean isKeyExist(String key1, String key2) {
//		return cc.redisTemplate().opsForHash().hasKey(key1, key2);
//	}
//
//	public void addInCache(String key1, String key2, Object val) {
//		cc.redisTemplate().opsForHash().put(key1, key2, val);
//	}
//
//	public Object getFromCache(String key1, String key2) {
//		return cc.redisTemplate().opsForHash().get(key1, key2);
//	}
//
//	public void removeKeyFromCache(String key) {
//		cc.redisTemplate().delete(key);
//		return;
//	}
//
//	public void removeAllKeysStartingWith() {
//		cc.redisConnectionFactory().getConnection().flushAll();
//
//	}

}
