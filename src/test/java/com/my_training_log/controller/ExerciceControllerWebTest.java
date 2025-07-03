package com.my_training_log.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my_training_log.config.JpaAuditingConfig;
import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.dto.MuscleGroupDto;
import com.my_training_log.exception.NotFoundException;
import com.my_training_log.service.ExerciceService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExerciceController.class, excludeAutoConfiguration = {
        JpaAuditingConfig.class
})
class ExerciceControllerWebTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ExerciceService exerciceService;

    @Autowired
    ObjectMapper objectMapper;



    //arbitrary list of exercice to test controller,
    // Not true data and not fully completed but we don't care ( perform mock controller test).
    private static List<ExerciceDto> buildListExercicesForTest() {
        MuscleGroupDto chest = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Chest").build();
        MuscleGroupDto shoulder = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Shoulder").build();
        MuscleGroupDto dorsal = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Dorsal").build();
        MuscleGroupDto bicepsAndBrachial = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Biceps & brachial").build();
        MuscleGroupDto triceps = MuscleGroupDto.builder().id(UUID.randomUUID()).name("Triceps").build();
        ExerciceDto benchPress =
                ExerciceDto.builder().id(UUID.randomUUID()).name("Bench press").referenceMuscleGroups(Arrays.asList(chest)).primaryMuscleGroups(Arrays.asList(chest, shoulder, triceps)).build();

        ExerciceDto pullUp =
                ExerciceDto.builder().id(UUID.randomUUID()).name("Pull up").referenceMuscleGroups(Arrays.asList(dorsal)).primaryMuscleGroups(Arrays.asList(dorsal, bicepsAndBrachial)).build();

        ExerciceDto pullOver =
                ExerciceDto.builder().id(UUID.randomUUID()).name("PullOver").referenceMuscleGroups(Arrays.asList(dorsal, chest)).primaryMuscleGroups(Arrays.asList(dorsal, chest)).build();


        return new ArrayList<>(List.of(
                benchPress, pullUp, pullOver
        ));
    }

    @Test
    void listExercices() throws Exception {
        List <ExerciceDto> testExercicesList = buildListExercicesForTest();

        given(exerciceService.listAllExercices()).willReturn(testExercicesList);

        mockMvc.perform(get(ExerciceController.EXERCICE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(testExercicesList.size()));
    }

    @Test
    void ListExercicesByReferenceMuscleGroupName() throws Exception {
        List <ExerciceDto> testExercicesList = buildListExercicesForTest();
        given(exerciceService.listExercicesByReferenceMuscleGroupName(any(String.class))).willReturn(Arrays.asList(testExercicesList.get(0)));

        mockMvc.perform(get(ExerciceController.EXERCICE_PATH)
                        .param("muscleGroupName", "Chest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void ListExercicesByReferenceMuscleGroupNameNotFound() throws Exception {
        given(exerciceService.listExercicesByReferenceMuscleGroupName(any(String.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(ExerciceController.EXERCICE_PATH)
                        .param("muscleGroupName", "NotFoundMuscleGroup")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




    @Test
    void getExerciceById () throws Exception {
        ExerciceDto testExercice = buildListExercicesForTest().get(0);
        given(exerciceService.getExerciceById(any(UUID.class))).willReturn(testExercice);

        mockMvc.perform(get(ExerciceController.EXERCICE_PATH_ID, testExercice.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testExercice.getName()));
    }

    @Test
    void getExerciceByIdNotFound () throws Exception {
        given(exerciceService.getExerciceById(any(UUID.class))).willThrow(new NotFoundException());

        mockMvc.perform(get(ExerciceController.EXERCICE_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createExercice() throws Exception {
        ExerciceDto testExercice = buildListExercicesForTest().get(0);

        given(exerciceService.createExercice(any(ExerciceDto.class))).willReturn(testExercice);

        mockMvc.perform(post(ExerciceController.EXERCICE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testExercice)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", ExerciceController.EXERCICE_PATH + "/" + testExercice.getId()));

    }

    @Test
    void createExerciceNoRequiredFields() throws Exception {
        ExerciceDto testExercice = buildListExercicesForTest().get(0);
        testExercice.setName(null);
        testExercice.setReferenceMuscleGroups(null);
        testExercice.setPrimaryMuscleGroups(null);

        mockMvc.perform(post(ExerciceController.EXERCICE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testExercice)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void updateExerciceById() throws Exception{
        ExerciceDto updateExercice = buildListExercicesForTest().get(0);
        updateExercice.setName("testUpdate");

        mockMvc.perform(put(ExerciceController.EXERCICE_PATH_ID, updateExercice.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateExercice))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor <UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor <ExerciceDto> exerciceArgumentCaptor = ArgumentCaptor.forClass(ExerciceDto.class);

        verify(exerciceService).updateExerciceById(uuidArgumentCaptor.capture(),exerciceArgumentCaptor.capture() );
        assertThat(updateExercice.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(updateExercice).isEqualTo(exerciceArgumentCaptor.getValue());

    }

    @Test
    void updateExerciceByIdNoRequiredFields() throws Exception {
        // let fields empty, the required field will be invalidated
        ExerciceDto invalidDto = ExerciceDto.builder().build();

        mockMvc.perform(put(ExerciceController.EXERCICE_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    void deleteExerciceById() throws Exception {
        ExerciceDto testExercice = buildListExercicesForTest().get(0);

        mockMvc.perform(delete(ExerciceController.EXERCICE_PATH_ID, testExercice.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        ArgumentCaptor <UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(exerciceService).deleteExerciceById(uuidArgumentCaptor.capture());
        assertThat(testExercice.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }


    @Test
    void deleteExerciceByIdNotFound() throws Exception {
        //doThrow if the method return nothing
        doThrow(new NotFoundException()).when(exerciceService).deleteExerciceById(any(UUID.class));

        mockMvc.perform(delete(ExerciceController.EXERCICE_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}