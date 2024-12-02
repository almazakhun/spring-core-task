package com.almaz.srm_system.storage;

import com.almaz.srm_system.model.Trainee;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TraineeStorage {
    @Getter
    private final Map<Long, Trainee> traineeMap = new HashMap<>();
    private final String traineesFilePath;
    private static final Logger logger = LoggerFactory.getLogger(TraineeStorage.class);

    public TraineeStorage(@Value("${data.trainees.file}") String traineesFilePath) {
        this.traineesFilePath = traineesFilePath;
    }

    @PostConstruct
    private void loadTrainees() {
        logger.info("Starting to load trainees from file: {}", traineesFilePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(traineesFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("userId")) continue; // Skip header
                String[] parts = line.split(",");
                Trainee trainee = new Trainee();
                trainee.setUserId(Long.parseLong(parts[0]));
                trainee.setFirstName(parts[1]);
                trainee.setLastName(parts[2]);
                trainee.setDateOfBirth(LocalDate.parse(parts[3]));
                trainee.setAddress(parts[4]);
                trainee.setUsername(parts[1] + "." + parts[2]);
                trainee.setPassword(CustomUtility.generateRandomPassword());
                traineeMap.put(trainee.getUserId(), trainee);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load trainees", e);
        }
    }
}
