package com.my_training_log.controller;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.service.ExerciceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ExerciceController {
    public static final String EXERCICE_PATH = "/api/v1/exercice";
    public static final String EXERCICE_PATH_ID = EXERCICE_PATH + "/{beerId}";

    private final ExerciceService exerciceService;
    @GetMapping(EXERCICE_PATH_ID)
    public ExerciceDto getExerciceById(UUID id){
        return exerciceService.findExerciceById(id);
    }
}
