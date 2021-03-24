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

    private final String ALL = "/all";
    private final String UPDATE = "/update";
    private final String DELETE = "/delete/{id}";
    private final String ADD = "/add";
    private final String GET_BY_ID = "/getId/{id}";
    private final String GET_BY_USERNAME = "/getUsername/{username}";

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
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    @GetMapping(GET_BY_USERNAME)
    public Optional<User> getByUsername(@PathVariable String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    @GetMapping(ALL)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping(ADD)
    public ResponseEntity<JsonMessage> add(@RequestBody User user) {
        try {
            Optional<User> username = userRepository.findByUsername(user.getUsername());
            Optional<User> userFiscalcode = userRepository.findByFiscalCode(user.getFiscalCode());
            if (username.isEmpty()) {
                if (userFiscalcode.isEmpty()) {
                    if (user.getUsername().isBlank() || user.getEmail().isBlank() || user.getFiscalCode().isBlank()) {
                        return new ResponseEntity<>(new JsonMessage("Username or email or fiscalcode are empty"),
                                HttpStatus.BAD_REQUEST);
                    } else {
                        userRepository.insert(user);
                        return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new JsonMessage("User with this fiscalcode already exist"),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new JsonMessage("User with this username already exist, please change it"),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(UPDATE)
    public ResponseEntity<JsonMessage> update(@PathVariable String id, @RequestBody User userBody) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                if (userBody.getUsername().isBlank() || userBody.getEmail().isBlank() || userBody.getFiscalCode().isBlank()) {
                    return new ResponseEntity<>(new JsonMessage("Username, email and fiscalcode  are mandatory"), HttpStatus.OK);
                }
                userBody.setId(user.get().getId());
                userRepository.save(userBody);
                return new ResponseEntity<>(new JsonMessage("User updated"), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new JsonMessage("User with this id not exist"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return new ResponseEntity<>(new JsonMessage("Error, please contact administrator"), HttpStatus.OK);
        }
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<JsonMessage> delete(@PathVariable String id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return new ResponseEntity<>(new JsonMessage("Id not present on DB"), HttpStatus.BAD_REQUEST);
            } else {
                userRepository.deleteById(id);
                return new ResponseEntity<>(new JsonMessage("User deleted"), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error");
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);
        }
    }
}
