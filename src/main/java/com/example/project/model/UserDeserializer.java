package com.example.project.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class UserDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper=new ObjectMapper();
        User user=new User();
        try{
            user=objectMapper.readValue(bytes,User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void close() {

    }
}
