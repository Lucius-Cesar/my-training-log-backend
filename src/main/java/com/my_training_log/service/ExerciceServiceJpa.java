package com.my_training_log.service;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.entity.Exercice;
import com.my_training_log.entity.MuscleGroup;
import com.my_training_log.exception.NotFoundException;
import com.my_training_log.mapper.ExerciceMapper;
import com.my_training_log.repository.ExerciceRepository;
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
public class ExerciceServiceJpa implements ExerciceService {

    private final ExerciceRepository exerciceRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciceMapper exerciceMapper;


    @Override
    public List<ExerciceDto> listAllExercices(){
        List<Exercice> exerciceList = exerciceRepository.findAll(Sort.by("referenceMuscleGroups"));
        List <ExerciceDto> exerciceDtoList = exerciceList.stream().map(exerciceMapper::toDto).toList();
        return exerciceDtoList;
    }

    @Override
    public List <ExerciceDto> listExercicesByReferenceMuscleGroupName(String muscleGroupName){
        MuscleGroup muscleGroupFound = muscleGroupRepository.findByName(muscleGroupName).orElseThrow(NotFoundException::new);
        List <Exercice> exerciceList = exerciceRepository.findByReferenceMuscleGroupsContaining(muscleGroupFound);
        return(exerciceList.stream().map(exerciceMapper::toDto).toList());
    };

    @Override
    public ExerciceDto getExerciceById(UUID id) {
        Exercice foundExercice = exerciceRepository.findById(id).
                orElseThrow(NotFoundException::new);

        return exerciceMapper.toDto(foundExercice);
    }

    @Override
    public ExerciceDto updateExerciceById(UUID id, ExerciceDto exerciceDto) {
        Exercice foundExercice = exerciceRepository.findById(id).orElseThrow(NotFoundException::new);
        Exercice savedExercice = exerciceRepository.save(exerciceMapper.toEntity(exerciceDto));
        return exerciceMapper.toDto(savedExercice);
    }

    @Override
    public ExerciceDto createExercice(ExerciceDto exerciceDto) {
        Exercice savedExercice = exerciceRepository.save(exerciceMapper.toEntity(exerciceDto));
        return(exerciceMapper.toDto(savedExercice));
    }

    @Override
    public void deleteExerciceById(UUID id) {

        Exercice foundExercice = exerciceRepository.findById(id).orElseThrow(NotFoundException::new);
        exerciceRepository.delete(foundExercice);

    }
}
