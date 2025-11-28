package com.quizApp.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.quizApp.model.Quiz;

public interface QuizRepo extends MongoRepository<Quiz,String>{

}