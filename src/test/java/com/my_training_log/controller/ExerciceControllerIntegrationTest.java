package com.my_training_log.controller;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.dto.MuscleGroupDto;
import com.my_training_log.exception.NotFoundException;
import com.my_training_log.repository.ExerciceRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ExerciceControllerIntegrationTest {

    @Autowired
    ExerciceController exerciceController;

    @Autowired
    ExerciceRepository exerciceRepository;

    List< ExerciceDto> getExercicesListForTest(){
        ResponseEntity <List<ExerciceDto>> responseEntity = exerciceController.listExercices();
        List< ExerciceDto> exercicesList = responseEntity.getBody();
        return exercicesList;
    }
    @Transactional
    @Test
    void listExercices() {
        ResponseEntity <List<ExerciceDto>> responseEntity = exerciceController.listExercices();
        List< ExerciceDto> exercicesList = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exercicesList.size()).isEqualTo(2);
    }

    @Transactional
    @Rollback
    @Test
    void listExercicesEmpty() {
        exerciceRepository.deleteAll();

        ResponseEntity <List<ExerciceDto>> responseEntity = exerciceController.listExercices();
        List< ExerciceDto> exercicesList = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exercicesList).isEmpty();
    }

    @Transactional
    @Test
    void ListExercicesByReferenceMuscleGroupName() {

        ResponseEntity <List<ExerciceDto>> responseEntity = exerciceController.listExercices("Chest");
        List< ExerciceDto> exercicesList = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exercicesList.size()).isEqualTo(1);
    }


    @Transactional
    @Test
    void ListExercicesByReferenceMuscleGroupNameNotFound() {
        //Hack: we just use UUID to generate random string
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

        assertThrows(NotFoundException.class, () -> {
                exerciceController.listExercices(randomString);
        });

    }

    @Test
    @Transactional

    void getExerciceById() {
        List< ExerciceDto> exercicesList = getExercicesListForTest();
        ExerciceDto exerciceToGet = exercicesList.get(0);

        ResponseEntity <ExerciceDto> responseEntity = exerciceController.getExerciceById(exerciceToGet.getId());

        ExerciceDto testExercice = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testExercice.getId()).isEqualTo(exerciceToGet.getId());
    }

    @Test
    void getExerciceByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            exerciceController.getExerciceById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void updateExerciceById() {
        List< ExerciceDto> exercicesList = getExercicesListForTest();
        ExerciceDto testExercice = exercicesList.get(0);

        String exerciceNewName = "updatedExerciceName";
        testExercice.setName("updatedExerciceName");
        exerciceController.updateExerciceById(testExercice.getId(), testExercice);

        ExerciceDto updatedExercice = exerciceController.getExerciceById(testExercice.getId()).getBody();

        assertThat(updatedExercice.getName()).isEqualTo(exerciceNewName);
    }

    @Test
    @Transactional
    @Rollback
    void updateExerciceByIdNotFound(){
        //just to have a payload for update
        List< ExerciceDto> exercicesList = getExercicesListForTest();
        ExerciceDto testExercice = exercicesList.get(0);

        assertThrows(NotFoundException.class, () -> {
                exerciceController.updateExerciceById(UUID.randomUUID(), testExercice);
        });

    }

    @Transactional
    @Rollback
    @Test
    void createExercice() {
        // Given
        MuscleGroupDto chest = MuscleGroupDto.builder()
                .name("Chest")
                .build();

        ExerciceDto newExercice = ExerciceDto.builder()
                .name("testExercice")
                .referenceMuscleGroups(List.of(chest))
                .primaryMuscleGroups(List.of(chest))
                .build();

        ResponseEntity<Void> responseEntity = exerciceController.createExercice(newExercice);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String location = responseEntity.getHeaders().getLocation().toString();
        System.out.println(location);
        String[] parts = location.split("/");
        String idStr = parts[parts.length - 1];
        UUID id = UUID.fromString(idStr);


        ResponseEntity<ExerciceDto> created = exerciceController.getExerciceById(id);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(created.getBody().getName()).isEqualTo("testExercice");
    }

    @Test
    @Transactional
    @Rollback
    void deleteExerciceById() {

        List< ExerciceDto> exercicesList = getExercicesListForTest();
        ExerciceDto testExercice = exercicesList.get(0);

        ResponseEntity <Void> responseEntity = exerciceController.deleteExerciceById(testExercice.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThrows(NotFoundException.class, () -> {
                    exerciceController.getExerciceById(testExercice.getId());
                }
        );



    }

    @Test
    void deleteExerciceByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
                    exerciceController.deleteExerciceById(UUID.randomUUID());
                }
        );

    }
}