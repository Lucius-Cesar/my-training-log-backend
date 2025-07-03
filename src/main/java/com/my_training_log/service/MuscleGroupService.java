package com.my_training_log.service;

import com.my_training_log.dto.MuscleGroupDto;

import java.util.List;
import java.util.UUID;

public interface MuscleGroupService {
    public List<MuscleGroupDto> listAllMuscleGroups();
    public MuscleGroupDto getMuscleGroupById(UUID id);
    public MuscleGroupDto createMuscleGroup(MuscleGroupDto muscleGroupDto);
    public MuscleGroupDto updateMuscleGroupById(UUID id, MuscleGroupDto muscleGroupDto);
    public void deleteMuscleGroupById(UUID id);
}
