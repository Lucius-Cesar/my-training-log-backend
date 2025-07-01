package com.my_training_log.mapper;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.entity.Exercice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciceMapper {
    ExerciceDto toDto(Exercice exercice);
    Exercice toEntity (ExerciceDto exerciceDto);
}
