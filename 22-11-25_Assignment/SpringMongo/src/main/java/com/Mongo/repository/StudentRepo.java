package com.Mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Mongo.model.Student;

public interface StudentRepo extends MongoRepository<Student, Integer>{
	

}
