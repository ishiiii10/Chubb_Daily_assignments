package com.bezkoder.spring.reddis.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "tutorials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutorial implements Serializable {
    //serializable converts this entity into stream of bytes so it can be stored or sent to reddis
    // because reddis , caches ,and  message queues can't store java objects directly
    @Id
    private String id;
    private String title;
    private String description;
    private boolean published;

    public Tutorial(String title, String description, boolean b) {
        this.title = title;
        this.description = description;
        this.published = b;
    }
}