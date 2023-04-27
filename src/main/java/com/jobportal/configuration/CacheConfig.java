package com.jobportal.configuration;

//@Configuration
//@EnableCaching
public class CacheConfig {
//
//	@Value("${spring.redis.host}")
//	private String redisHost;
//
//	@Value("${spring.redis.port}")
//	private int redisPort;
//
//	@Value("${spring.redis.password}")
//	private String redisPassword;
//
//	public CacheConfig(String redisHost, int redisPort, String redisPassword) {
//		super();
//		this.redisHost = redisHost;
//		this.redisPort = redisPort;
//		this.redisPassword = redisPassword;
//	}
//
//	public CacheConfig() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	@Bean
//	public LettuceConnectionFactory redisConnectionFactory() {
//		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//
//		configuration.setHostName(redisHost);
//		configuration.setPort(redisPort);
//		configuration.setPassword(RedisPassword.of(redisPassword.trim().length() > 0 ? redisPassword : ""));
//		return new LettuceConnectionFactory(configuration);
//
//	}
//
//	@Bean
//	public RedisCacheConfiguration cacheConfiguration() {
//
//		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//				.entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
//		return cacheConfig;
//
//	}
//
//	@Bean
//	public RedisCacheManager cacheManager() {
//
//		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration())
//				.transactionAware().build();
//		return rcm;
//
//	}
//
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> template = new RedisTemplate<>();
//		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
//		LettuceConnectionFactory factory = redisConnectionFactory();
//		factory.afterPropertiesSet();
//		template.setHashKeySerializer(redisSerializer);
//		template.setConnectionFactory(factory);
//		template.setHashValueSerializer(jdkSerializationRedisSerializer);
//		template.setKeySerializer(redisSerializer);
//		template.setEnableDefaultSerializer(true);
//		template.afterPropertiesSet();
//		template.setValueSerializer(jdkSerializationRedisSerializer);
//		return template;
//
//	}
}