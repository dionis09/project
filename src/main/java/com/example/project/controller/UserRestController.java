package com.example.project.controller;

import com.example.project.model.JsonMessage;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@EnableScheduling
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    private final String USER_ID = "/{id}";
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

    @GetMapping(USER_ID)
    public Optional<User> getById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @GetMapping(GET_BY_USERNAME)
    public Optional<User> getByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping()
    public List<User> getAll() {
        return userRepository.findAll();
    }


    @PostMapping()
    public ResponseEntity<JsonMessage> add(@RequestBody User user) {
        try {
            Optional<User> username = userRepository.findByUsernameOrFiscalCode(user.getUsername(), user.getFiscalCode());
            if (username.isEmpty()) {
                if (StringUtils.isEmpty(user.getEmail().trim()) || StringUtils.isEmpty(user.getFiscalCode().trim())
                        || StringUtils.isEmpty(user.getUsername().trim())) {
                    return new ResponseEntity<>(new JsonMessage("Username or email or fiscalcode are empty"),
                            HttpStatus.BAD_REQUEST);
                } else {
                    userRepository.insert(user);
                    return new ResponseEntity<>(new JsonMessage("Performed"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new JsonMessage("User with this username or fiscalcode already exist, please change it"),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ERROR ADD USER {}", user.toString(), e);
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(USER_ID)
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
            log.error("ERROR UPDATE USER {}", userBody.toString(), e);
            return new ResponseEntity<>(new JsonMessage("Error, please contact administrator"), HttpStatus.OK);
        }
    }

    @DeleteMapping(USER_ID)
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
            log.error("ERROR DELETE USER BY ID {}", id, e);
            return new ResponseEntity<>(new JsonMessage("Error"), HttpStatus.CONFLICT);
        }
    }
}
