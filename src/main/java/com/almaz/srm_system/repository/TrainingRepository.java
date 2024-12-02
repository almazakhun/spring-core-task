package com.almaz.srm_system.repository;

import com.almaz.srm_system.model.Training;
import com.almaz.srm_system.storage.TrainingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRepository {
    private TrainingStorage trainingStorage;

    @Autowired
    public void setTrainingStorage(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public void create(Training training) {
        trainingStorage.getTrainingMap().put(training.getTraineeId() + "-" + training.getTrainerId(), training);
    }

    public Training getTrainingById(String id) {
        return trainingStorage.getTrainingMap().get(id);
    }
}
