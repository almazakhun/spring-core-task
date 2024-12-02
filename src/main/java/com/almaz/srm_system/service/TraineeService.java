package com.almaz.srm_system.service;

import com.almaz.srm_system.model.Trainee;
import com.almaz.srm_system.repository.TraineeRepository;
import com.almaz.srm_system.utility.CustomUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public void createTrainee(Trainee trainee) {
        logger.info("Creating a new trainee with name: {} {}", trainee.getFirstName(), trainee.getLastName());
        String username = generateUsername(trainee.getFirstName(), trainee.getLastName());
        String password = CustomUtility.generateRandomPassword();
        trainee.setUsername(username);
        trainee.setPassword(password);
        logger.debug("Generated username: {} and password for trainee.", username);
        traineeRepository.create(trainee);
        logger.info("Trainee created successfully with ID: {}", trainee.getUserId());
    }

    public void updateTrainee(Trainee trainee) {
        logger.info("Updating trainee with ID: {}", trainee.getUserId());
        traineeRepository.update(trainee);
        logger.info("Trainee updated successfully.");
    }

    public void deleteTrainee(Long id) {
        logger.info("Deleting trainee with ID: {}", id);
        traineeRepository.delete(id);
        logger.info("Trainee deleted successfully.");
    }

    public Optional<Trainee> getTrainee(Long id) {
        logger.info("Fetching trainee with ID: {}", id);
        Optional<Trainee> trainee = Optional.ofNullable(traineeRepository.read(id));
        if (trainee.isPresent()) {
            logger.info("Trainee found: {}", trainee.get());
        } else {
            logger.warn("Trainee with ID: {} not found.", id);
        }
        return trainee;
    }

    private String generateUsername(String firstName, String lastName) {
        logger.debug("Generating username for: {} {}", firstName, lastName);
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        List<Trainee> trainees = traineeRepository.findAll();
        boolean isDuplicate = true;

        while (isDuplicate) {
            isDuplicate = false;
            for (Trainee t : trainees) {
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