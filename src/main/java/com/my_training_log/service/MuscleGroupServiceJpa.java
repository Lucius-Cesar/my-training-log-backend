package com.my_training_log.service;

import com.my_training_log.dto.MuscleGroupDto;
import com.my_training_log.entity.MuscleGroup;
import com.my_training_log.exception.NotFoundException;
import com.my_training_log.mapper.MuscleGroupMapper;
import com.my_training_log.repository.MuscleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class MuscleGroupServiceJpa implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleGroupMapper muscleGroupMapper;

    @Override
    public List<MuscleGroupDto> listAllMuscleGroups() {
        List<MuscleGroup> muscleGroupList = muscleGroupRepository.findAll(Sort.by("name"));
        return muscleGroupList.stream()
                .map(muscleGroupMapper::toDto)
                .toList();
    }

    @Override
    public MuscleGroupDto getMuscleGroupById(UUID id) {
        MuscleGroup foundMuscleGroup = muscleGroupRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return muscleGroupMapper.toDto(foundMuscleGroup);
    }

    @Override
    public MuscleGroupDto createMuscleGroup(MuscleGroupDto muscleGroupDto) {
        MuscleGroup savedMuscleGroup = muscleGroupRepository.save(muscleGroupMapper.toEntity(muscleGroupDto));
        return muscleGroupMapper.toDto(savedMuscleGroup);
    }

    @Override
    public MuscleGroupDto updateMuscleGroupById(UUID id, MuscleGroupDto muscleGroupDto) {
        // Verify exists first
        muscleGroupRepository.findById(id).orElseThrow(NotFoundException::new);
        // Save updated entity (make sure dto contains the id)
        MuscleGroup entityToUpdate = muscleGroupMapper.toEntity(muscleGroupDto);
        MuscleGroup savedMuscleGroup = muscleGroupRepository.save(entityToUpdate);
        return muscleGroupMapper.toDto(savedMuscleGroup);
    }

    @Override
    public void deleteMuscleGroupById(UUID id) {
        MuscleGroup foundMuscleGroup = muscleGroupRepository.findById(id).orElseThrow(NotFoundException::new);
        muscleGroupRepository.delete(foundMuscleGroup);
    }
}
