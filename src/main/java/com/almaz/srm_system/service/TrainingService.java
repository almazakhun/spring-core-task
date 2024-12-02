package com.almaz.srm_system.service;

import com.almaz.srm_system.model.Training;
import com.almaz.srm_system.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public void create(Training training) {
        logger.info("Creating a new training with details: {}", training);
        trainingRepository.create(training);
        logger.info("Training created successfully with ID: {}", training.getTraineeId() + "-" + training.getTrainerId());
    }

    public Optional<Training> getTraining(String id) {
        logger.info("Fetching training with ID: {}", id);
        Optional<Training> training = Optional.ofNullable(trainingRepository.getTrainingById(id));
        if (training.isPresent()) {
            logger.info("Training found: {}", training.get());
        } else {
            logger.warn("Training with ID: {} not found.", id);
        }
        return training;
    }
}

