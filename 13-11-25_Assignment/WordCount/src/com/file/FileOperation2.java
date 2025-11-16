package com.file;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileOperation2{

    public static void main(String[] args) {
        String filename = "/Users/anushreyshubham/eclipse-workspace/Chubb_Daily_Assignments/13-11-25_Assignment/WordCount/src/com/file/India.txt";

        try {
            String content = Files.readString(Path.of(filename));

            Pattern pattern = Pattern.compile("\\bindia\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);

            int count = 0;
            while (matcher.find()) {
                count++;
            }

            System.out.println("Count using regex Matcher: " + count);

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}

