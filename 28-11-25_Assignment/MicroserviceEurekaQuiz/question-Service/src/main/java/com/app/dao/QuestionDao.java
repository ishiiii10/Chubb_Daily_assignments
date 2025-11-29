package com.app.dao;

import com.app.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionDao extends MongoRepository<Question, String> {


    List<Question> findByCategory(String category);
}
