package com.my_training_log.mapper;

import com.my_training_log.dto.MuscleGroupDto;
import com.my_training_log.entity.MuscleGroup;
import org.mapstruct.Mapper;

@Mapper
public interface MuscleGroupMapper {
    MuscleGroupDto MuscleGroupToMuscleGroupDto(MuscleGroup muscleGroup);
    MuscleGroup MuscleGroupDtoToMuscleGroup(MuscleGroup muscleGroup);
}
