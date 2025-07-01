package com.my_training_log.repository;

import com.my_training_log.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, UUID> {
    Optional<MuscleGroup> findByName(String name);


}
