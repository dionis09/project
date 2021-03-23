package com.example.project.controller;

import com.example.project.model.JsonMessage;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@EnableScheduling
@RequestMapping("users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    private final String ALL="/all";
    private final String UPDATE="/update";
    private final String DELETE="/delete/{id}";
    private final String ADD="/add";
    private final String GET_BY_ID="/getId/{id}";
    private final String GET_BY_USERNAME="/getUsername/{username}";

    @PostConstruct
    private void initUsers() {
        if (userRepository.count() == 0) {
            User Dionis = User.builder().username("Dionis").email("topallidionis.dt@gmail.com")
                    .name("Dionis").surname("Topalli")
                    .phone("").fiscalCode("")
                    .creation(LocalDateTime.now()).build();
            userRepository.insert(Dionis);
        }
    }

    @GetMapping(GET_BY_ID)
    public Optional<User> getById(@PathVariable String id) {
        try {
            return userRepository.findById(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping(ALL)
    public List<User> getAll(){
        return userRepository.findAll();
    }


    @PostMapping(ADD)
    public ResponseEntity<JsonMessage> add(@RequestBody User user){
        return null;
    }

    @PutMapping(UPDATE)
    public ResponseEntity<JsonMessage> update(@PathVariable String id, @RequestBody User user){
        return null;
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<JsonMessage> delete(@PathVariable String id){
        try{
            Optional<User> user= userRepository.findById(id);
            if (user.isEmpty()){
                return new ResponseEntity<>(new JsonMessage("Id not present on DB"), HttpStatus.BAD_REQUEST);
            }else{
                userRepository.deleteById(id);
                return new ResponseEntity<>(new JsonMessage("User deleted"), HttpStatus.OK);
            }
        }catch(Exception e){
            e.printStackTrace();
            log.info("Error");
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);        }
    }
}
