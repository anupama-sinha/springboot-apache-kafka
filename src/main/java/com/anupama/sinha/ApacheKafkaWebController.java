package com.anupama.sinha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class ApacheKafkaWebController {

    @Autowired
    KafkaProducer kafkaSender;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("message") String message) {
        kafkaSender.send(message);

        return "Message sent to the Kafka Topic test Successfully";
    }

}

