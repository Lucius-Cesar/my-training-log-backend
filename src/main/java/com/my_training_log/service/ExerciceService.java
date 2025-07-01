package com.my_training_log.service;

import com.my_training_log.dto.ExerciceDto;

import java.util.List;
import java.util.UUID;

public interface ExerciceService {
    List<ExerciceDto> listAllExercices();
    List<ExerciceDto> listExercicesByReferenceMuscleGroupName(String muscleGroupName);
    ExerciceDto getExerciceById(UUID id);
    ExerciceDto updateExerciceById(UUID id, ExerciceDto exercice);
    ExerciceDto createExercice(ExerciceDto exercice);
    void deleteExerciceById(UUID id);
}
