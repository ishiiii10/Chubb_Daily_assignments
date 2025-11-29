package com.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("quizzes")
public class Quiz {

    @Id
    private String id;

    private String title;
    private List<String> questionIds;
}
