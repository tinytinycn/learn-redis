package com.example.springdataredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    @Qualifier("customizedRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/redis")
    public void redis() {
        redisTemplate.opsForValue().set("hello", "word");
        redisTemplate.opsForValue().set("integer", 666);
        String hello = (String) redisTemplate.opsForValue().get("hello");
        System.out.println("hello = " + hello);
        Integer integer = (Integer) redisTemplate.opsForValue().get("integer");
        System.out.println("integer = " + integer);

        stringRedisTemplate.opsForValue().set("halo", String.valueOf(Integer.valueOf(999)));

    }
}
