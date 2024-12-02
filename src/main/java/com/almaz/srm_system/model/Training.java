package com.almaz.srm_system.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Training {
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private String trainingType;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
