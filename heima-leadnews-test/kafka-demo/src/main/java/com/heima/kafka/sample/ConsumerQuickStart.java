package com.heima.kafka.sample;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.util.Properties;
import java.util.Collections;

/**
 * kafka 消费者
 */
public class ConsumerQuickStart {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 指定消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        // 反序列化的 key 和 value
        //消息key的反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        //消息value的反序列化器
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        // 创建消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 订阅主题
        consumer.subscribe(Collections.singletonList("topic-first"));
        // 拉取并消费消息，然后手动提交偏移量
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    System.out.println(consumerRecord.key());
                    System.out.println(consumerRecord.value());
                }
                consumer.commitAsync();//异步提交偏移量
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally { //拉取消息时抛出了异常
            try{
                consumer.commitSync();//同步提交偏移量
            }finally {
                consumer.close();
            }
        }
    }
}
