package com.example.springdataredis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 自定义redisTemplate
     * - RedisConnectionFactory Springboot2.x 默认使用 LettuceConnectionFactory 进行注入
     * - 设置了 jackson2JsonRedisSerializer 序列化，会对对所有的键值加上 "" 引号 (不同于springboot 自带的 stringRedisTemplate)
     */
    @Bean("customizedRedisTemplate")
    public RedisTemplate<String, Object> customizedRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> customizedRedisTemplate = new RedisTemplate<>();
        // 采用 jackson2JsonRedisSerializer 代替默认序列化器 (默认使用JDK序列化器)
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);// 验证器用于验证要反序列化的实际子类型对于验证器使用的任何标准是否有效：在反序列化不受信任的内容的情况下很重要
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 指定 key value hashKey hashValue 的序列化
        customizedRedisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        customizedRedisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        customizedRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        customizedRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 指定 redisConnection 连接池工厂类
        customizedRedisTemplate.setConnectionFactory(connectionFactory);
        customizedRedisTemplate.afterPropertiesSet();
        return customizedRedisTemplate;
    }

}
