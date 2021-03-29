package com.example.project.repository;

import com.example.project.model.Storic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoricRepository extends MongoRepository<Storic, String> {

}
