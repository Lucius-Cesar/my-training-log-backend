package com.my_training_log.service;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.entity.Exercice;

import java.util.UUID;

public interface ExerciceService {
    ExerciceDto findExerciceById(UUID id);
    ExerciceDto updateExerciceById(UUID id, ExerciceDto exercice);
    ExerciceDto createExercice(ExerciceDto exercice);
    ExerciceDto deleteExerciceById(UUID id);
}
