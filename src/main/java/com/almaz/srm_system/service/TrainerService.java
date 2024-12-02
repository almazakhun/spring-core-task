package com.almaz.srm_system.service;

import com.almaz.srm_system.model.Trainer;
import com.almaz.srm_system.repository.TrainerRepository;
import com.almaz.srm_system.utility.CustomUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public void createTrainer(Trainer trainer) {
        logger.info("Creating a new trainer with name: {} {}", trainer.getFirstName(), trainer.getLastName());
        String username = generateUsername(trainer.getFirstName(), trainer.getLastName());
        String password = CustomUtility.generateRandomPassword();
        trainer.setUsername(username);
        trainer.setPassword(password);
        logger.debug("Generated username: {} and password for trainer.", username);
        trainerRepository.createTrainer(trainer);
        logger.info("Trainer created successfully with ID: {}", trainer.getUserId());
    }

    public void updateTrainer(Trainer trainer) {
        logger.info("Updating trainer with ID: {}", trainer.getUserId());
        trainerRepository.updateTrainer(trainer);
        logger.info("Trainer updated successfully.");
    }

    public Optional<Trainer> getTrainer(Long id) {
        logger.info("Fetching trainer with ID: {}", id);
        Optional<Trainer> trainer = Optional.ofNullable(trainerRepository.getTrainerById(id));
        if (trainer.isPresent()) {
            logger.info("Trainer found: {}", trainer.get());
        } else {
            logger.warn("Trainer with ID: {} not found.", id);
        }
        return trainer;
    }

    private String generateUsername(String firstName, String lastName) {
        logger.debug("Generating username for: {} {}", firstName, lastName);
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        List<Trainer> trainers = trainerRepository.findAll();
        boolean isDuplicate = true;

        while (isDuplicate) {
            isDuplicate = false;
            for (Trainer t : trainers) {
                if (t.getUsername().equals(username)) {
                    isDuplicate = true;
                    username = baseUsername + suffix++;
                    break;
                }
            }
        }

        logger.debug("Final username generated: {}", username);
        return username;
    }
}
