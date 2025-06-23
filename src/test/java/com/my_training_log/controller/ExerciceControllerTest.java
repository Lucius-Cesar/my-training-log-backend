package com.my_training_log.controller;

import com.my_training_log.service.ExerciceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class ExerciceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ExerciceService exerciceService;

    @Autowired
    ExerciceController exerciceController;

    @Test
    void getExerciceById(

    ) {
    }
}