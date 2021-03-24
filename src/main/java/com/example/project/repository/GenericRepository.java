package com.example.project.repository;

import com.example.project.model.Generic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenericRepository extends MongoRepository<Generic,String> {

}
