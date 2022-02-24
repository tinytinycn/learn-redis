package com.example.springredisstream.listener;

import com.example.springredisstream.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyStreamListener implements StreamListener<String, ObjectRecord<String, Message>> {
    @Override
    public void onMessage(ObjectRecord<String, Message> message) {
        log.info("myStreamListener 接收到: {}", message.getValue().toString());
        System.out.println(message.getValue().getClass());
    }
}
