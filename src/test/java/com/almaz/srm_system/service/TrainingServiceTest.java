package com.almaz.srm_system.service;

import com.almaz.srm_system.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrainingServiceTest {
    private TrainingService trainingService;

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Test
    void create() {
        Training newTraining = new Training();
        newTraining.setTrainerId(3L);
        newTraining.setTraineeId(4L);
        newTraining.setTrainingName("Yoga Basics");
        newTraining.setTrainingType("Yoga");
        newTraining.setTrainingDate(LocalDate.now().plusDays(1));
        newTraining.setTrainingDuration(Duration.ofMinutes(90)); // 90 minutes

        trainingService.create(newTraining);

        Optional<Training> retrievedTraining = trainingService.getTraining(newTraining.getTraineeId() + "-" + newTraining.getTrainerId());
        assertTrue(retrievedTraining.isPresent(), "Training should exist in the system");
    }

    @Test
    void getTraining() {
        Training existingTraining = new Training();
        existingTraining.setTrainerId(1L);
        existingTraining.setTraineeId(2L);
        existingTraining.setTrainingName("Cardio Session");
        existingTraining.setTrainingType("Fitness");
        existingTraining.setTrainingDate(LocalDate.now());
        existingTraining.setTrainingDuration(Duration.ofMinutes(60));

        trainingService.create(existingTraining);

        // Now run the actual test
        Optional<Training> retrievedTraining = trainingService.getTraining(existingTraining.getTraineeId() + "-" + existingTraining.getTrainerId());
        assertTrue(retrievedTraining.isPresent(), "Training should be retrievable");

    }
}