package com.heima.kafka.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/hello")
    public String hello(){
        //参数：topics, 消息内容
        kafkaTemplate.send("kafka-hello", "StreamAzure");
        return "ok";
    }
}
