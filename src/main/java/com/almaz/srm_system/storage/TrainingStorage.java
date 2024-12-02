package com.almaz.srm_system.storage;

import com.almaz.srm_system.model.Training;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainingStorage {
    @Getter
    private final Map<String, Training> trainingMap = new HashMap<>();
    private final String trainingsFilePath;
    private static final Logger logger = LoggerFactory.getLogger(TrainingStorage.class);

    public TrainingStorage(@Value("${data.trainings.file}") String trainingsFilePath) {
        this.trainingsFilePath = trainingsFilePath;
    }

    @PostConstruct
    private void loadTrainings() {
        logger.info("Starting to load trainings from file: {}", trainingsFilePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(trainingsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("traineeId")) continue; // Skip header
                String[] parts = line.split(",");
                Training training = new Training();
                training.setTraineeId(Long.parseLong(parts[0]));
                training.setTrainerId(Long.parseLong(parts[1]));
                training.setTrainingName(parts[2]);
                training.setTrainingType(parts[3]);
                training.setTrainingDate(LocalDate.parse(parts[4]));
                training.setTrainingDuration(Duration.parse(parts[5]));
                trainingMap.put(training.getTraineeId() + "-" + training.getTrainerId(), training);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load trainings", e);
        }
    }
}
