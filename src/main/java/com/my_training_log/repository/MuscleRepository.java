package com.my_training_log.repository;

import com.my_training_log.entity.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuscleRepository extends JpaRepository<Muscle, UUID> {
}
