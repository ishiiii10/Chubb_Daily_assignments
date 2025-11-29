package com.app.feign;

import com.app.model.QuestionWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "question-service")
public interface QuestionClient {

    @GetMapping("question/generate")
    List<QuestionWrapper> getQuestionsForQuiz(@RequestParam int num, @RequestParam String category);

    @GetMapping("question/correct-answer/{id}")
    String getCorrectAnswer(@PathVariable String id);

    @GetMapping("question/{id}")
    QuestionWrapper getQuestionById(@PathVariable String id);
}
