package com.example.project.controller;

import com.example.project.model.Generic;
import com.example.project.model.JsonMessage;
import com.example.project.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@EnableScheduling
@RequestMapping("generic")
public class GenericController {

    @Autowired
    private GenericRepository genericRepository;

    @PostMapping()
    public ResponseEntity<JsonMessage> insert(@RequestBody Generic obj) {
        try {
            log.info(String.valueOf(obj.getObj()));
            genericRepository.insert(obj);
            return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAll")
    public List<Generic> get() {
        return genericRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Generic> getById(@PathVariable String id) {
        try {
            if (genericRepository.findById(id).isPresent()) {
                return genericRepository.findById(id);
            } else {
                return Optional.empty();
            }
        }catch(Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonMessage> delete(@PathVariable String id) {
        try {
            genericRepository.deleteById(id);
            return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonMessage> put(@PathVariable String id, @RequestBody Generic generic) {
        try {
            Optional<Generic> obj = genericRepository.findById(id);
            if (obj.isPresent()) {
                generic.setId(id);
                genericRepository.save(generic);
                return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new JsonMessage("user not present on db"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.CONFLICT);
        }
    }

}
