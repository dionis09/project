package com.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JsonMessage<T> {
    private String message;
    private T payload;

    public JsonMessage(String message) {
        this.message = message;
    }
}
