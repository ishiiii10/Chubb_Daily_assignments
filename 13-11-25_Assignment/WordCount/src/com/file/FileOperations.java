package com.file;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileOperations {

    public static void main(String[] args) {
        String filename = "/Users/anushreyshubham/eclipse-workspace/Chubb_Daily_Assignments/13-11-25_Assignment/WordCount/src/com/file/India.txt";

        try {
            String content = Files.readString(Path.of(filename)).toLowerCase();

            // Split based on non-word characters
            long count = Arrays.stream(content.split("\\W+"))
                    .filter(word -> word.equals("india"))
                    .count();

            System.out.println("Count using readString(): " + count);

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}

