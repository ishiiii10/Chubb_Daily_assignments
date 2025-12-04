package com.bezkoder.spring.reddis.service;

import com.bezkoder.spring.reddis.model.Tutorial;
import com.bezkoder.spring.reddis.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Cacheable(value = "tutorials", key = "'allTutorials'")
    public List<Tutorial> getAllTutorials(String title) {
        if (title == null) {
            return tutorialRepository.findAll();
        }
        return tutorialRepository.findByTitleContaining(title);
    }
    @Cacheable(value = "tutorials", key = "#id")
    public Optional<Tutorial> getTutorialById(String id) {
        return tutorialRepository.findById(id);
    }

    public Tutorial createTutorial(Tutorial tutorial) {
        return tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
    }
    @Cacheable(value = "tutorials", key = "'published'")
    public List<Tutorial> getPublishedTutorials() {
        System.out.println("Fetching published tutorials from database...");
        return tutorialRepository.findByPublished(true);
    }




}