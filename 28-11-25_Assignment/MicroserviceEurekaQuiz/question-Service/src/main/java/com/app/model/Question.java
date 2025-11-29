package com.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("questions")
public class Question {

    @Id
    private String id;

    private String questionTitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String rightAnswer;

    private String category;
    private String difficultyLevel;
}
