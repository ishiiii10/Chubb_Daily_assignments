package com.questionService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.questionService.model.Question;

import java.util.List;

public interface QuestionRepo extends MongoRepository<Question, String> {


    List<Question> findByCategory(String category);
}
