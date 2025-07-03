package com.my_training_log.repository;

import com.my_training_log.entity.Exercice;
import com.my_training_log.entity.MuscleGroup;
import com.my_training_log.mapper.ExerciceMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ExerciceRepositoryIT {

    @Autowired
    ExerciceRepository exerciceRepository;

    @Autowired
    ExerciceMapper exerciceMapper;

    @Test
    void getExerciceById(){
        Exercice testExercice = exerciceRepository.findAll().get(0);

        System.out.println(testExercice.getCreatedDate());
    }

    @Test
    @Transactional
    @Rollback
    void createExerciceFieldsRequired() {
        Exercice invalidExercice = new Exercice();
        invalidExercice.setName(null);
        invalidExercice.setReferenceMuscleGroups(null);
        invalidExercice.setPrimaryMuscleGroups(null);


        assertThrows(ConstraintViolationException.class, () -> {
            exerciceRepository.saveAndFlush(invalidExercice); // forces flush and validation
        });
    }


    @Test
    @Transactional
    @Rollback
    void updateExerciceFieldsRequired() {
        Exercice testExercice = exerciceRepository.findAll().get(0);
        testExercice.setName(null);
        testExercice.setReferenceMuscleGroups(null);
        testExercice.setPrimaryMuscleGroups(null);


        assertThrows(ConstraintViolationException.class, () -> {
            exerciceRepository.saveAndFlush(testExercice); // forces flush and validation
        });
    }

    @Test
    @Transactional
    void findByReferenceMuscleGroupsContaining(){
        Exercice testExercice = exerciceRepository.findAll().get(0);
        MuscleGroup testMuscleGroup = testExercice.getReferenceMuscleGroups().get(0);
        assertThat(exerciceRepository.findByReferenceMuscleGroupsContaining(testMuscleGroup)).contains(testExercice);
    }

}
