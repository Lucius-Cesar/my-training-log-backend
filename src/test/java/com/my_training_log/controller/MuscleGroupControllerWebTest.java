package com.my_training_log.controller;

import com.my_training_log.config.JpaAuditingConfig;
import com.my_training_log.dto.MuscleGroupDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.my_training_log.exception.NotFoundException;
import com.my_training_log.service.ExerciceService;
import com.my_training_log.service.MuscleGroupService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = MuscleGroupController.class, excludeAutoConfiguration = {
        JpaAuditingConfig.class
})
public class MuscleGroupControllerWebTest {

    @Autowired
    MockMvc mockMvc;


    @MockitoBean
    MuscleGroupService muscleGroupService;

    @Autowired
    ObjectMapper objectMapper;

    List<MuscleGroupDto> buildMuscleGroupsListForTest() {
        MuscleGroupDto muscleGroup1 = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Chest").build();
        MuscleGroupDto muscleGroup2 = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Shoulders").build();
        return (Arrays.asList(muscleGroup1, muscleGroup2));
    }

    @Test
    void listMuscleGroups() throws Exception {
        List<MuscleGroupDto> muscleGroupsList = buildMuscleGroupsListForTest();
        given(muscleGroupService.listAllMuscleGroups()).willReturn(muscleGroupsList);

        mockMvc.perform(get(MuscleGroupController.MUSCLE_GROUP_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(muscleGroupsList.size()));
    }

    @Test
    void getMuscleGroupById() throws Exception {
        MuscleGroupDto testMuscleGroup = buildMuscleGroupsListForTest().get(0);
        given(muscleGroupService.getMuscleGroupById(any(UUID.class))).willReturn(testMuscleGroup);

        mockMvc.perform(get(MuscleGroupController.MUSCLE_GROUP_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testMuscleGroup.getName()));
    }

    @Test
    void getMuscleGroupByIdNotFound() throws Exception {
        given(muscleGroupService.getMuscleGroupById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(MuscleGroupController.MUSCLE_GROUP_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMuscleGroup() throws Exception {
        MuscleGroupDto newMuscleGroup = MuscleGroupDto.builder().id(UUID.randomUUID()).name("New muscle group").build();
        given(muscleGroupService.createMuscleGroup(any(MuscleGroupDto.class))).willReturn(newMuscleGroup);

        mockMvc.perform(post(MuscleGroupController.MUSCLE_GROUP_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMuscleGroup)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", MuscleGroupController.MUSCLE_GROUP_PATH + "/" + newMuscleGroup.getId()));

    }

    @Test
    void createMuscleGroupFieldsRequired() throws Exception {
        MuscleGroupDto newMuscleGroup = MuscleGroupDto.builder().name(null).build();

        mockMvc.perform(post(MuscleGroupController.MUSCLE_GROUP_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMuscleGroup)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void createMuscleGroupUniqueNameViolation() throws Exception {
        MuscleGroupDto newMuscleGroup = MuscleGroupDto.builder().name("Existing muscle group").build();
        given(muscleGroupService.createMuscleGroup(any(MuscleGroupDto.class))).willThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post(MuscleGroupController.MUSCLE_GROUP_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMuscleGroup)))
                .andExpect(status().isBadRequest());}


    @Test
    void updateMuscleGroupById() throws Exception {
        MuscleGroupDto testMuscleGroup = buildMuscleGroupsListForTest().get(0);
        given(muscleGroupService.updateMuscleGroupById(any(UUID.class), any(MuscleGroupDto.class))).willReturn(testMuscleGroup);

        mockMvc.perform(put(MuscleGroupController.MUSCLE_GROUP_PATH_ID, testMuscleGroup.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMuscleGroup)))
                .andExpect(status().isNoContent());

        ArgumentCaptor <UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);;
        ArgumentCaptor <MuscleGroupDto> muscleGroupDtoArgumentCaptor = ArgumentCaptor.forClass(MuscleGroupDto.class);
        verify(muscleGroupService).updateMuscleGroupById(uuidArgumentCaptor.capture(), muscleGroupDtoArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testMuscleGroup.getId());
        assertThat(muscleGroupDtoArgumentCaptor.getValue()).isEqualTo(testMuscleGroup);
    }

    @Test
    void updateMuscleGroupByIdNotFound() throws Exception {
        MuscleGroupDto testMuscleGroup = buildMuscleGroupsListForTest().get(0);
        given(muscleGroupService.updateMuscleGroupById(any(UUID.class), any(MuscleGroupDto.class))).willThrow(NotFoundException.class);

        mockMvc.perform(put(MuscleGroupController.MUSCLE_GROUP_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMuscleGroup)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMuscleGroupFieldsRequired() throws Exception {
        MuscleGroupDto testMuscleGroup = MuscleGroupDto.builder().id(UUID.randomUUID()).name(null).build();
        mockMvc.perform(put(MuscleGroupController.MUSCLE_GROUP_PATH_ID, testMuscleGroup.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMuscleGroup)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void updateMuscleGroupByIdUniqueNameViolation() throws Exception {
        MuscleGroupDto testMuscleGroup = buildMuscleGroupsListForTest().get(0);
        given(muscleGroupService.updateMuscleGroupById(any(UUID.class), any(MuscleGroupDto.class))).willThrow(DataIntegrityViolationException.class);

        mockMvc.perform(put(MuscleGroupController.MUSCLE_GROUP_PATH_ID, testMuscleGroup.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMuscleGroup)))
                .andExpect(status().isBadRequest()) ;}

    @Test
    void deleteMuscleGroupById() throws Exception {
        MuscleGroupDto testMuscleGroup = buildMuscleGroupsListForTest().get(0);

        mockMvc.perform(delete(MuscleGroupController.MUSCLE_GROUP_PATH_ID, testMuscleGroup.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor <UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);;
        verify(muscleGroupService).deleteMuscleGroupById(uuidArgumentCaptor.capture());
        assertThat(testMuscleGroup.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }


}
