package com.my_training_log.mapper;

import com.my_training_log.dto.MuscleDto;
import com.my_training_log.entity.Muscle;
import org.mapstruct.Mapper;

@Mapper
public interface MuscleMapper {
    Muscle muscleToMuscleDto(Muscle muscle);
    Muscle MuscleDtoToMuscle(MuscleDto muscleDto);
}
