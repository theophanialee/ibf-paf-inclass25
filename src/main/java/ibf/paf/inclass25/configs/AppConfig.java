package ibf.paf.inclass25.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import ibf.paf.inclass25.services.MessageSubscriber;



@Configuration
public class AppConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost; 
	@Value("${spring.data.redis.port}")
    private Integer redisPort;
    @Value("${spring.data.redis.username}")
    private String redisUser;
    @Value("${spring.data.redis.password}")
    private String redisPassword;
    @Value("${spring.data.redis.database}")
    private int redisDatabase;

	@Autowired
	private MessageSubscriber subscriber;

	public RedisConnectionFactory createConnectionFactory() {
		final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		config.setDatabase(redisDatabase);
		if ((null != redisUser) && (!redisUser.equals(""))) {
			config.setUsername(redisUser);
			config.setPassword(redisPassword);
		}

		final JedisClientConfiguration jedisClient = JedisClientConfiguration
				.builder().build();
		JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
		jedisFac.afterPropertiesSet();
		return jedisFac;
	}

	// @Bean("myredis")
	// public RedisTemplate<String, String> createRedisTemplate() {
	// 	RedisConnectionFactory fac = createConnectionFactory();
	// 	RedisTemplate<String, String> template = new RedisTemplate<>();
	// 	template.setConnectionFactory(fac);
	// 	template.setKeySerializer(new StringRedisSerializer());
	// 	template.setValueSerializer(new StringRedisSerializer());
	// 	template.setHashKeySerializer(new StringRedisSerializer());
	// 	template.setHashValueSerializer(new StringRedisSerializer());
	// 	return template;
	// }

	@Bean
	public RedisMessageListenerContainer createMessageListener() {
		RedisConnectionFactory fac = createConnectionFactory();
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(fac);

		container.addMessageListener(subscriber, ChannelTopic.of("mytopic"));
		return container;
	}
}