package com.almaz.srm_system.repository;

import com.almaz.srm_system.model.Trainer;
import com.almaz.srm_system.storage.TrainerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainerRepository {
    private TrainerStorage trainerStorage;

    @Autowired
    public void setTrainerStorage(TrainerStorage trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    public void createTrainer(Trainer trainer) {
        trainerStorage.getTrainerMap().put(trainer.getUserId(), trainer);
    }

    public Trainer getTrainerById(Long id) {
        return trainerStorage.getTrainerMap().get(id);
    }

    public void updateTrainer(Trainer trainer) {
        trainerStorage.getTrainerMap().put(trainer.getUserId(), trainer);
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(trainerStorage.getTrainerMap().values());
    }
}
