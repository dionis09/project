package com.example.project.engine;

import com.example.project.model.Storic;
import com.example.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "users";

    @Autowired
    private KafkaTemplate<String, Storic> kafkaTemplate;

    public void sendMessage(Storic storic) {
        logger.info(String.format("#### -> Producing message -> %s", storic));
        this.kafkaTemplate.send(TOPIC, storic);
    }
}
