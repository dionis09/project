package com.example.project.repository;

import com.example.project.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressInterface extends MongoRepository<Address, String> {

}
