package com.almaz.srm_system.service;

import com.almaz.srm_system.model.Trainee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TraineeServiceTest {

    private TraineeService traineeService;

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Test
    void createTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        traineeService.createTrainee(trainee);

        Optional<Trainee> updatedTrainee = traineeService.getTrainee(trainee.getUserId());
        assertTrue(updatedTrainee.isPresent(), "Trainee should exist in the system");
    }

    @Test
    void updateTrainee() {
        // Create a new trainee to ensure test isolation
        Trainee trainee = new Trainee();
        trainee.setFirstName("Jane");
        trainee.setLastName("Doe");
        traineeService.createTrainee(trainee);

        // Update the trainee
        trainee.setAddress("123 Main St");
        traineeService.updateTrainee(trainee);

        Trainee updatedTrainee = traineeService.getTrainee(trainee.getUserId()).get();
        assertEquals("123 Main St", updatedTrainee.getAddress(), "Address should be updated");
    }

    @Test
    void deleteTrainee() {
        // Create a new trainee for testing deletion
        Trainee trainee = new Trainee();
        trainee.setFirstName("Jane");
        trainee.setLastName("Doe");
        traineeService.createTrainee(trainee);

        Long userId = trainee.getUserId();
        traineeService.deleteTrainee(userId);

        Optional<Trainee> deletedTrainee = traineeService.getTrainee(userId);
        assertTrue(deletedTrainee.isEmpty(), "Trainee should be deleted");
    }

    @Test
    void getTrainee() {
        // Create a new trainee for retrieval test
        Trainee trainee = new Trainee();
        trainee.setFirstName("Jane");
        trainee.setLastName("Smith");
        traineeService.createTrainee(trainee);

        Optional<Trainee> retrievedTrainee = traineeService.getTrainee(trainee.getUserId());
        assertTrue(retrievedTrainee.isPresent(), "Trainee should exist in the system");
    }
}
