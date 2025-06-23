package com.my_training_log.service;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.entity.Exercice;
import com.my_training_log.exception.NotFoundException;
import com.my_training_log.mapper.ExerciceMapper;
import com.my_training_log.repository.ExerciceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class ExerciceServiceJpa implements ExerciceService {

    private final ExerciceRepository exerciceRepository;
    private final ExerciceMapper exerciceMapper;

    @Override
    public ExerciceDto findExerciceById(UUID id) {
        Exercice foundExercice = exerciceRepository.findById(id).
                orElseThrow(NotFoundException::new);

        return exerciceMapper.toDto(foundExercice);
    }

    @Override
    public ExerciceDto updateExerciceById(UUID id, ExerciceDto exerciceDto) {
        return null;
    }

    @Override
    public ExerciceDto createExercice(ExerciceDto exerciceDto) {
        return null;
    }

    @Override
    public ExerciceDto deleteExerciceById(UUID id) {
        return null;
    }
}
