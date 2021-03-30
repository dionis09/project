package com.example.project.engine;

import com.example.project.model.Storic;
import com.example.project.model.User;
import com.example.project.repository.StoricRepository;
import com.example.project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private StoricRepository storicRepository;
    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(User user) throws IOException {
        Storic storic=new Storic();
        storic.setDate(LocalDateTime.now());
        storic.setUser(user);
        if (user.getCreation().truncatedTo(ChronoUnit.MINUTES).isEqual(storic.getDate().truncatedTo(ChronoUnit.MINUTES))){
            storic.setDetails("Insert User");
        }else {
            storic.setDetails("Updated user");
        }
        storicRepository.insert(storic);
        logger.info(String.format("#### -> Consumed idUser -> %s", user.getId()));
    }
}
