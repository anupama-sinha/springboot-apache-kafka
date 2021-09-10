package com.anupama.sinha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static String kafkaTopic = "test1";

    public void send(String message) {

        kafkaTemplate.send(kafkaTopic, message);
    }
}
