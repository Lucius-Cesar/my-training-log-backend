package com.my_training_log.repository;

import com.my_training_log.entity.MuscleGroup;
import com.my_training_log.mapper.MuscleGroupMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
public class MuscleGroupRepositoryIT {

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    @Autowired
    MuscleGroupMapper muscleGroupMapper;

    @Test
    @Transactional
    @Rollback
    void createMuscleGroupFieldsRequired(){
        MuscleGroup chest = MuscleGroup.builder().name(null).build();

        assertThrows(ConstraintViolationException.class, () -> {
            muscleGroupRepository.saveAndFlush(chest);
        });

    }

    @Test
    @Transactional
    @Rollback
    void createMuscleGroupNameAlreadyExists(){
        MuscleGroup muscleGroup1 = MuscleGroup.builder().name("Existing MuscleGroup").build();
        MuscleGroup muscleGroup2 = MuscleGroup.builder().name("Existing MuscleGroup").build();

        muscleGroupRepository.saveAndFlush(muscleGroup1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            muscleGroupRepository.saveAndFlush(muscleGroup2);

        });

    }

    @Test
    @Transactional
    @Rollback
    void updateMuscleGroupFieldsRequired(){
        MuscleGroup muscleGroupForTest = muscleGroupRepository.findAll().get(0);
        muscleGroupForTest.setName(null);
        assertThrows(ConstraintViolationException.class, () -> {
            muscleGroupRepository.saveAndFlush(muscleGroupForTest);
        });

    }

    @Test
    @Transactional
    @Rollback
    void updateMuscleGroupNameAlreadyExists(){
        MuscleGroup muscleGroup1 = MuscleGroup.builder().name("Existing MuscleGroup").build();
        MuscleGroup muscleGroup2 = MuscleGroup.builder().name("MuscleGroup2").build();
        muscleGroupRepository.saveAndFlush(muscleGroup1);
        muscleGroupRepository.saveAndFlush(muscleGroup2);

        muscleGroup2.setName("Existing MuscleGroup");

        assertThrows(DataIntegrityViolationException.class, () -> {
            muscleGroupRepository.saveAndFlush(muscleGroup2);
        });
    }

    @Test
    void findByName(){
        String nameForTest = "test";
        MuscleGroup testMuscleGroup = MuscleGroup.builder().name(nameForTest).build();
        muscleGroupRepository.saveAndFlush(testMuscleGroup);

        Optional<MuscleGroup> muscleGroupFound = muscleGroupRepository.findByName(nameForTest);
        assertThat(muscleGroupFound.get().getName()).isEqualTo(nameForTest);
    }




}
