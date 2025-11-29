package com.app.controller;

import com.app.model.Quiz;
import com.app.model.QuestionWrapper;
import com.app.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Quiz> createQuiz(
            @RequestParam String title,
            @RequestParam int num,
            @RequestParam String category
    ) {
        return ResponseEntity.ok(quizService.createQuiz(title, num, category));
    }

    @GetMapping("{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable String quizId) {
        return ResponseEntity.ok(quizService.getQuizQuestions(quizId));
    }

    //on submit display score --TODO

    @PostMapping("submit/{quizId}")
    public ResponseEntity<String> submitQuiz(
            @PathVariable String quizId,
            @RequestBody List<String> userAnswers) {
        int score = quizService.submitQuiz(quizId, userAnswers);
        return ResponseEntity.ok("your score: " + score);
    }
}
