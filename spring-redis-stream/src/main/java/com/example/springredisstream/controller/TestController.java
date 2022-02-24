package com.example.springredisstream.controller;

import com.example.springredisstream.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/send")
    public String send() {
        Message message = Message.builder()
                .senderId(1L)
                .receiverId(2L)
                .content("这是一条消息body")
                .addrs(Arrays.asList("a", "b", "c"))
                .build();
        Record<String, Message> record = StreamRecords
                .objectBacked(message)
                .withStreamKey("message"); // 指定stream名称
        stringRedisTemplate.opsForStream().add(record);
        return "发送成功";
    }
}
