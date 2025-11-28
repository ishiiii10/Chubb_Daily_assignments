package com.questionService.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class QuestionWrapper {

    private String id;
    private String questionTitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
}