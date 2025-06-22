package com.my_training_log.repository;

import com.my_training_log.entity.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciceRepository extends JpaRepository <Exercice, UUID> {
}
