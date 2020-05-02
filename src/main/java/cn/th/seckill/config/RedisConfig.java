package cn.th.seckill.config;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.Result;
import cn.th.seckill.entity.SeckillGoods;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * @author tianh
 */
@Configuration
public class RedisConfig {
    public CacheKeyPrefix myCacheKeyPrefix(){
        return cacheName -> cacheName+":";
    }

    @Bean
    @Primary
    public RedisTemplate<String, Goods> goodsRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException{
        RedisTemplate<String, Goods> goodsRedisTemplate = new RedisTemplate<>();
        goodsRedisTemplate.setDefaultSerializer(new FastJsonRedisSerializer<>(Goods.class));
        goodsRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return goodsRedisTemplate;
    }
    @Bean
    public RedisTemplate<String, OrderInfo> orderInfoRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException{
        RedisTemplate<String, OrderInfo> goodsRedisTemplate = new RedisTemplate<>();
        goodsRedisTemplate.setDefaultSerializer(new FastJsonRedisSerializer<>(OrderInfo.class));
        goodsRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return goodsRedisTemplate;
    }
    @Bean
    public RedisTemplate<String, SeckillGoods> secGoodsRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException{
        RedisTemplate<String, SeckillGoods> goodsRedisTemplate = new RedisTemplate<>();
        goodsRedisTemplate.setDefaultSerializer(new FastJsonRedisSerializer<>(SeckillGoods.class));
        goodsRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return goodsRedisTemplate;
    }
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    @Bean
    public RedisTemplate<String, Result<Object>> resultRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Result<Object>> redisTemplate=new RedisTemplate<>();
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Result.class));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
    @Bean
    @Primary
    RedisCacheManager redisGoodCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Goods.class))).computePrefixWith(myCacheKeyPrefix());
        RedisCacheManager.RedisCacheManagerBuilder manager= RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration);
        return manager.build();
    }
    @Bean
    RedisCacheManager redisOrderCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(OrderInfo.class))).computePrefixWith(myCacheKeyPrefix());
        RedisCacheManager.RedisCacheManagerBuilder manager= RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration);
        return manager.build();
    }
    @Bean
    RedisCacheManager redisSecGoodsCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(SeckillGoods.class))).computePrefixWith(myCacheKeyPrefix());
        RedisCacheManager.RedisCacheManagerBuilder manager= RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration);
        return manager.build();
    }
    @Bean
    RedisCacheManager redisObjectCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class))).computePrefixWith(myCacheKeyPrefix());
        RedisCacheManager.RedisCacheManagerBuilder manager= RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration);
        return manager.build();
    }
    @Bean
    RedisCacheManager redisStringCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).computePrefixWith(myCacheKeyPrefix());
        RedisCacheManager.RedisCacheManagerBuilder manager= RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration);
        return manager.build();
    }

}
