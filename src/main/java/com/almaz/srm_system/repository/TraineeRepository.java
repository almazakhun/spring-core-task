package com.almaz.srm_system.repository;

import com.almaz.srm_system.model.Trainee;
import com.almaz.srm_system.storage.TraineeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TraineeRepository {
    private TraineeStorage traineeStorage;

    @Autowired
    public void setTraineeStorage(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    public void create(Trainee entity) {
        traineeStorage.getTraineeMap().put(entity.getUserId(), entity);
    }

    public Trainee read(Long id) {
        return traineeStorage.getTraineeMap().get(id);
    }

    public void update(Trainee entity) {
        traineeStorage.getTraineeMap().put(entity.getUserId(), entity);
    }

    public void delete(Long id) {
        traineeStorage.getTraineeMap().remove(id);
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(traineeStorage.getTraineeMap().values());
    }
}
