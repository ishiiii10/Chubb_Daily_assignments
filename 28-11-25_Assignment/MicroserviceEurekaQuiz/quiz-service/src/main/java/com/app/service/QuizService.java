package com.app.service;

import com.app.feign.QuestionClient;
import com.app.model.QuestionWrapper;
import com.app.model.Quiz;
import com.app.repo.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionClient questionClient;

        // create quiz

    // we do three task -> create a quiz
    //2. fetch a question using quizId
    //3. submit a quiz to calulcate score
    public Quiz createQuiz(String title, int num, String category) {
        List<QuestionWrapper> questions = questionClient.getQuestionsForQuiz(num, category);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);

        quiz.setQuestionIds(
                questions.stream().map(QuestionWrapper::getId).toList()
        );

        return quizRepository.save(quiz);
    }

    // fetch full questions for quiz
    public List<QuestionWrapper> getQuizQuestions(String quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        return quiz.getQuestionIds().stream()
                .map(questionClient::getQuestionById)
                .toList();
    }

    // submit quiz + score calculation
    public int submitQuiz(String quizId, List<String> userAnswers) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<String> questionIds = quiz.getQuestionIds();
        int score = 0;
        for (int i = 0; i < questionIds.size(); i++) {
            String correctAns = questionClient.getCorrectAnswer(questionIds.get(i));
            if (correctAns.equalsIgnoreCase(userAnswers.get(i))) {
                score++;
            }
        }

        return score;
    }
}
