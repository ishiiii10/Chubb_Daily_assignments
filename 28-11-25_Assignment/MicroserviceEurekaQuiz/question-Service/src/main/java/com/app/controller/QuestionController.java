package com.app.controller;

import com.app.model.Question;
import com.app.model.QuestionWrapper;
import com.app.model.Response;
import com.app.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // get all questions
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    // add MCQ question
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.addQuestion(question).getMessage());
    }

    // generate quiz question wrappers (without correct answers)
    @GetMapping("/generate")
    public ResponseEntity<List<QuestionWrapper>> generateQuiz(
            @RequestParam int num,
            @RequestParam String category) {
        return ResponseEntity.ok(questionService.getQuestionsForQuiz(num, category));
    }

    // quiz-service needs to check correct answer
    @GetMapping("/correct-answer/{id}")
    public ResponseEntity<String> getCorrectAnswer(@PathVariable String id) {
        return ResponseEntity.ok(questionService.getCorrectAnswer(id));
    }
    //implement getScore --TODO

    @GetMapping("/{id}")
    public QuestionWrapper getQuestionById(@PathVariable String id) {
        return questionService.getQuestionWrapperById(id);
    }




}
