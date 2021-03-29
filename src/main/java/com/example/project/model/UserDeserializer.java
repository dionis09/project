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
        Storic storic=new Storic();
        try{
            storic=objectMapper.readValue(bytes,Storic.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return storic;
    }

    @Override
    public void close() {

    }
}
