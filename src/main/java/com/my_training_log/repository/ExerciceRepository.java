package com.my_training_log.repository;

import com.my_training_log.entity.Exercice;
import com.my_training_log.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface ExerciceRepository extends JpaRepository <Exercice, UUID> {
    List<Exercice> findByReferenceMuscleGroupsContaining(MuscleGroup muscleGroup);

}
