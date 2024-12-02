package com.almaz.srm_system.service;

import com.almaz.srm_system.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrainerServiceTest {

    private TrainerService trainerService;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Test
    void createTrainer() {
        Trainer newTrainer = new Trainer();
        newTrainer.setUserId(3L);
        newTrainer.setFirstName("Jane");
        newTrainer.setLastName("Doe");
        newTrainer.setSpecialization("Yoga");

        trainerService.createTrainer(newTrainer);

        Optional<Trainer> retrievedTrainer = trainerService.getTrainer(newTrainer.getUserId());
        assertTrue(retrievedTrainer.isPresent(), "Trainer should exist in the system");
    }

    @Test
    void updateTrainer() {
        Trainer trainerToUpdate = new Trainer();
        trainerToUpdate.setUserId(4L);
        trainerToUpdate.setFirstName("John");
        trainerToUpdate.setLastName("Smith");
        trainerToUpdate.setSpecialization("Fitness");
        trainerService.createTrainer(trainerToUpdate);

        trainerToUpdate.setSpecialization("Advanced Fitness");
        trainerService.updateTrainer(trainerToUpdate);

        Optional<Trainer> updatedTrainer = trainerService.getTrainer(trainerToUpdate.getUserId());
        assertEquals("Advanced Fitness", updatedTrainer.get().getSpecialization(), "Trainer specialization should be updated");
    }

    @Test
    void getTrainer() {
        Trainer trainerToRetrieve = new Trainer();
        trainerToRetrieve.setUserId(5L);
        trainerToRetrieve.setFirstName("Mark");
        trainerToRetrieve.setLastName("Johnson");
        trainerToRetrieve.setSpecialization("Boxing");
        trainerService.createTrainer(trainerToRetrieve);

        Optional<Trainer> retrievedTrainer = trainerService.getTrainer(trainerToRetrieve.getUserId());
        assertTrue(retrievedTrainer.isPresent(), "Trainer should be retrievable");
    }
}
