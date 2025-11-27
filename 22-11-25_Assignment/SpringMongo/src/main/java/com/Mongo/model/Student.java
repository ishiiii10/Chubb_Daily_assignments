package com.Mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="springmongo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
	@Id
	private Integer RollNo;
	private String Name;
	private String Branch;

}
