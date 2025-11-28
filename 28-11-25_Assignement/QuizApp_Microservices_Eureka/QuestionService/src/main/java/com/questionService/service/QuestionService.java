package com.questionService.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.questionService.model.Question;
import com.questionService.model.QuestionWrapper;
import com.questionService.model.Response;
import com.questionService.repository.QuestionRepo;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionDao;

    // add MCQ question
    public Response addQuestion(Question question) {
        questionDao.save(question);
        return new Response();
    }

    // get all questions
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    // get questions by category
    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }
    // task to send questions based on numbers and category--TODO

    public List<QuestionWrapper> getQuestionsForQuiz(int numQ, String category) {

        List<Question> allQuestions = questionDao.findByCategory(category);

        // validate num of questions < total questions in db --TODO


        return allQuestions.stream()
                .limit(numQ)
                .map(q -> {
                    QuestionWrapper wrapper = new QuestionWrapper();
                    wrapper.setId(q.getId());
                    wrapper.setQuestionTitle(q.getQuestionTitle());
                    wrapper.setOption1(q.getOption1());
                    wrapper.setOption2(q.getOption2());
                    wrapper.setOption3(q.getOption3());
                    wrapper.setOption4(q.getOption4());
                    return wrapper;
                })
                .collect(Collectors.toList());
    }

    public String getCorrectAnswer(String questionId) {
        return questionDao.findById(questionId)
                .map(Question::getRightAnswer)
                .orElse(null);
    }

    public QuestionWrapper getQuestionWrapperById(String id) {
        Question q = questionDao.findById(id).orElse(null);
        if (q == null) return null;

        QuestionWrapper w = new QuestionWrapper();
        w.setId(q.getId());
        w.setQuestionTitle(q.getQuestionTitle());
        w.setOption1(q.getOption1());
        w.setOption2(q.getOption2());
        w.setOption3(q.getOption3());
        w.setOption4(q.getOption4());

        return w;
    }

}