package com.example.springredisstream.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long senderId;
    private Long receiverId;
    private String content;
    private List<String> addrs;
}
