package com.almaz.srm_system.storage;

import com.almaz.srm_system.model.Trainer;
import com.almaz.srm_system.utility.CustomUtility;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainerStorage {
    @Getter
    private final Map<Long, Trainer> trainerMap = new HashMap<>();
    private final String trainersFilePath;
    private static final Logger logger = LoggerFactory.getLogger(TrainerStorage.class);

    public TrainerStorage(@Value("${data.trainers.file}") String trainersFilePath) {
        this.trainersFilePath = trainersFilePath;
    }

    @PostConstruct
    private void loadTrainers() {
        logger.info("Starting to load trainers from file: {}", trainersFilePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(trainersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("userId")) continue; // Skip header
                String[] parts = line.split(",");
                Trainer trainer = new Trainer();
                trainer.setUserId(Long.parseLong(parts[0]));
                trainer.setFirstName(parts[1]);
                trainer.setLastName(parts[2]);
                trainer.setSpecialization(parts[3]);
                trainer.setUsername(parts[1] + "." + parts[2]);
                trainer.setPassword(CustomUtility.generateRandomPassword());
                trainerMap.put(trainer.getUserId(), trainer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load trainers", e);
        }
    }
}
