package com.my_training_log.mapper;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.entity.Exercice;
import org.mapstruct.Mapper;

@Mapper
public interface ExerciceMapper {
    ExerciceDto exerciceToExerciceDto(Exercice exercice);
    Exercice exerciceDtoToExercice(ExerciceDto exerciceDto);
}
