package com.example.project.repository;

import com.example.project.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
     Optional<User> findByUsernameOrFiscalCode(String username,String fiscalcode);
     Optional<User> findByUsername(String username);
     void deleteByUsername(String username);
}
