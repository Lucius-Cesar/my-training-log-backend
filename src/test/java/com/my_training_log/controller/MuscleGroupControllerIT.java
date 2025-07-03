package com.my_training_log.controller;

import com.my_training_log.dto.MuscleDto;
import com.my_training_log.dto.MuscleGroupDto;
import com.my_training_log.entity.MuscleGroup;
import com.my_training_log.exception.NotFoundException;
import com.my_training_log.mapper.MuscleGroupMapper;
import com.my_training_log.repository.MuscleGroupRepository;
import com.my_training_log.repository.MuscleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.my_training_log.utils.UrlUtils.extractIdFromLocationHeader;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MuscleGroupControllerIT {

    @Autowired
    MuscleGroupController muscleGroupController;

    @Autowired
    MuscleGroupRepository muscleGroupRepository;
    @Autowired
    MuscleRepository muscleRepository;
    @Autowired
    MuscleGroupMapper muscleGroupMapper;


    @Test
    @Transactional
    void listMuscleGroups(){
        ResponseEntity <List <MuscleGroupDto>> responseEntity = muscleGroupController.listMuscleGroups();
        List <MuscleGroupDto> muscleGroupsList = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(muscleGroupsList).isNotEmpty();
        assertThat(muscleGroupsList).size().isEqualTo(12);

    }

    @Test
    void testGetMuscleGroupById(){

        MuscleGroup muscleGroupEntity = muscleGroupRepository.findAll().get(0);

        ResponseEntity <MuscleGroupDto> responseEntity = muscleGroupController.getMuscleGroupById(muscleGroupEntity.getId());
        MuscleGroupDto muscleGroupDto = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(muscleGroupDto.getId()).isEqualTo(muscleGroupEntity.getId());
        assertThat(muscleGroupDto.getCreatedDate()).isNotNull();

    }

    @Test
    void testGetMuscleGroupByIdNotFound(){

        assertThrows(NotFoundException.class, () -> {
            muscleGroupController.getMuscleGroupById(UUID.randomUUID());
        });

    }

    @Test
    @Transactional
    @Rollback
    void testCreateMuscleGroup(){
        MuscleGroupDto muscleGroupPayload = MuscleGroupDto.builder()
                .name("New muscle Group")
                .build();

        ResponseEntity <Void> responseEntity = muscleGroupController.createMuscleGroup(muscleGroupPayload);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String location = responseEntity.getHeaders().getLocation().toString();

        String[] parts = location.split("/");
        String idString = parts[parts.length - 1];
        UUID id = UUID.fromString(idString);

        ResponseEntity <MuscleGroupDto> createdMuscleGroupResponseEntity = muscleGroupController.getMuscleGroupById(id);
        MuscleGroupDto createdMuscleGroup = createdMuscleGroupResponseEntity.getBody();

        assertThat(createdMuscleGroup.getName()).isEqualTo(muscleGroupPayload.getName());
        assertThat(createdMuscleGroup.getCreatedDate()).isNotNull().isEqualTo(createdMuscleGroup.getLastModifiedDate());
    }



    @Test
    @Transactional
    @Rollback

    void updateMuscleGroupById() {
        MuscleGroup muscleGroupToUpdate = muscleGroupRepository.findAll().get(0);
        String newName = "New name";
        muscleGroupToUpdate.setName(newName);
        ResponseEntity <Void> responseEntityUpdate = muscleGroupController.updateMuscleGroupById(muscleGroupToUpdate.getId(), muscleGroupMapper.toDto(muscleGroupToUpdate));

        assertThat(responseEntityUpdate.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity <MuscleGroupDto> responseEntityGet = muscleGroupController.getMuscleGroupById(muscleGroupToUpdate.getId());
        MuscleGroupDto updatedMuscleGroup = responseEntityGet.getBody();

        assertThat(updatedMuscleGroup.getName()).isEqualTo(newName);

    }

    @Test
    @Transactional
    @Rollback
    void testUpdateMuscleGroupNotFound() {
        MuscleGroupDto testMuscleGroup = MuscleGroupDto.builder().name("test").build();

        assertThrows(NotFoundException.class, () -> {
            muscleGroupController.updateMuscleGroupById(UUID.randomUUID(), testMuscleGroup);
        });
    }


    @Test
    @Transactional
    @Rollback
    void testDeleteMuscleGroupById() {
        // Arrange - Créer un nouveau groupe musculaire
        MuscleGroupDto newMuscleGroupDto = MuscleGroupDto.builder()
                .name("New Muscle Group")
                .build();

        ResponseEntity<Void> createResponse = muscleGroupController.createMuscleGroup(newMuscleGroupDto);
        muscleGroupRepository.flush();

        UUID createdId = extractIdFromLocationHeader(createResponse);

        ResponseEntity<Void> deleteResponse = muscleGroupController.deleteMuscleGroupById(createdId);
        muscleGroupRepository.flush();

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Optional<MuscleGroup> deletedEntity = muscleGroupRepository.findById(createdId);
        assertThat(deletedEntity).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteMuscleGroupByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            muscleGroupController.deleteMuscleGroupById(UUID.randomUUID());
        });

    }



}
