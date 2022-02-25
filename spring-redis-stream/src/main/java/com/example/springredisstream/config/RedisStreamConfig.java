package com.example.springredisstream.config;

import com.example.springredisstream.entity.Message;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@Slf4j
public class RedisStreamConfig {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StreamListener<String, ObjectRecord<String, Message>> streamListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) {
        checkStreamGroup();
        // options
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Message>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(Message.class)
                .build();
        // listenerContainer
        StreamMessageListenerContainer<String, ObjectRecord<String, Message>> streamMessageListenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory, options);
        Subscription subscription = streamMessageListenerContainer.receiveAutoAck(
                Consumer.from("testStreamGroup", "localhost"),
                StreamOffset.create("message", ReadOffset.lastConsumed()),
                streamListener
        );
        // start()
        // 返回 subscription
        return subscription;
    }

    private void checkStreamGroup() {
        List<String> consumers = new ArrayList<>();
        consumers.add("testStreamGroup");
        StreamInfo.XInfoGroups infoGroups = null;
        try{
            // 查看 message stream的所有消费组group
            infoGroups = stringRedisTemplate.opsForStream().groups("message");
        } catch (RedisSystemException | RedisException | InvalidDataAccessApiUsageException ex) {
            log.error("group key not exist or commend error", ex);
        }

        for (String consumer: consumers){
            boolean consumerExist = false;
            if( Objects.nonNull(infoGroups) ){
                if(infoGroups.stream().anyMatch(t->Objects.equals(consumer,t.groupName()))){
                    consumerExist = true;
                }
            }
            // 不存在testStreamGroup名称的消费组，则创建一个
            if(!consumerExist){
                stringRedisTemplate.opsForStream().createGroup("message",consumer);
            }
        }

    }
}
