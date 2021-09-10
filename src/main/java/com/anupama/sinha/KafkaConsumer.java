package com.anupama.sinha;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics="test1", groupId="anugroup")
    public void consumeFromTopic(String message) {
        System.out.println("Message received :"+message);
    }
}
