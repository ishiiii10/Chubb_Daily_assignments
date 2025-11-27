package com.Mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Mongo.model.Student;
import com.Mongo.repository.StudentRepo;

@RestController
public class MainController {
	@Autowired
	StudentRepo studentRepo;
	@PostMapping("/addStudent")
	public void addStudent(@RequestBody Student student) {
		studentRepo.save(student);
		
		
	}

}
