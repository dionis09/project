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
@RequestMapping("/generic")
public class GenericController {

    @Autowired
    private GenericRepository genericRepository;

    private final String ID="/{id}";
    @PostMapping()
    public ResponseEntity<JsonMessage> insert(@RequestBody Generic obj) {
        try {
            log.info(String.valueOf(obj.getObj()));
            genericRepository.insert(obj);
            return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new ResponseEntity<>(new JsonMessage("ERROR "), HttpStatus.CONFLICT);
        }
    }

    @GetMapping()
    public List<Generic> get() {
        return genericRepository.findAll();
    }

    @GetMapping(ID)
    public Optional<Generic> getById(@PathVariable String id) {
                return genericRepository.findById(id);
    }

    @DeleteMapping(ID)
    public ResponseEntity<JsonMessage> delete(@PathVariable String id) {
        try {
            genericRepository.deleteById(id);
            return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR DELETE GENERIC BY ID {}", id, e);
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(ID)
    public ResponseEntity<JsonMessage> put(@PathVariable String id, @RequestBody Generic generic) {
        try {
            Optional<Generic> obj = genericRepository.findById(id);
            if (obj.isPresent()) {
                generic.setId(id);
                genericRepository.save(generic);
                return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new JsonMessage("User not present on db"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ERROR UPDATE GENERIC {} AND ID {}", generic.getObj(),id, e);
            return new ResponseEntity<>(new JsonMessage("ERROR"), HttpStatus.CONFLICT);
        }
    }

}
