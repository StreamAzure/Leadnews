package com.heima.kafka.sample;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Kafka 生产者
 */
public class ProducerQuickStart {
    public static void main(String[] args) {
        // 设置 kafka 配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //消息key的序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        //消息value的序列化器
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        // 创建生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
        // 发送消息
        ProducerRecord<String,String> record = new ProducerRecord<String,String>("topic-first","key-001","hello, kafka");
        producer.send(record);

        // 同步发送
        try {
            RecordMetadata recordMetadata = producer.send(record).get();
            System.out.println(recordMetadata.offset()); // 打印偏移量
        }catch (Exception e){
            e.printStackTrace();
        }

        // 异步发送
        try {
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e!=null){
                        e.printStackTrace();
                    }
                    System.out.println(recordMetadata.offset());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        // 关闭消息通道
        producer.close();
    }
}
