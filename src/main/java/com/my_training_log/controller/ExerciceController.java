package com.my_training_log.controller;

import com.my_training_log.dto.ExerciceDto;
import com.my_training_log.service.ExerciceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ExerciceController {
    public static final String EXERCICE_PATH = "/api/v1/exercice";
    public static final String EXERCICE_PATH_ID = EXERCICE_PATH + "/{exerciceId}";

    private final ExerciceService exerciceService;

    @GetMapping(value = EXERCICE_PATH, produces = "application/json")
    public ResponseEntity<List<ExerciceDto>> listExercices(
            @RequestParam(value = "muscleGroupName", required = false) String muscleGroupName
    ) {

        if (muscleGroupName != null && !muscleGroupName.isBlank()) {
            return new ResponseEntity<>(
                    exerciceService.listExercicesByReferenceMuscleGroupName(muscleGroupName),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    exerciceService.listAllExercices(),
                    HttpStatus.OK
            );
        }
    }

    public ResponseEntity<List<ExerciceDto>> listExercices() {
        return listExercices(null);
    }


    @GetMapping(value = EXERCICE_PATH_ID, produces = "application/json")
    public ResponseEntity<ExerciceDto> getExerciceById(@PathVariable("exerciceId") UUID exerciceId){
        return new ResponseEntity<>(exerciceService.getExerciceById(exerciceId), HttpStatus.OK);
    }




    @PostMapping(EXERCICE_PATH)
    public ResponseEntity <Void> createExercice(@RequestBody @Validated ExerciceDto exerciceDto) {
        ExerciceDto savedExercice = exerciceService.createExercice(exerciceDto);
        System.out.println(savedExercice);
        String location = EXERCICE_PATH + "/" + savedExercice.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", location);

        return new ResponseEntity <>(headers, HttpStatus.CREATED);
    }

    @PutMapping(EXERCICE_PATH_ID)
    public ResponseEntity <Void> updateExerciceById(@PathVariable("exerciceId") UUID id, @RequestBody @Validated ExerciceDto exerciceDto) {
        exerciceService.updateExerciceById(id, exerciceDto);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(EXERCICE_PATH_ID)
    public ResponseEntity <Void> deleteExerciceById(@PathVariable("exerciceId") UUID id) {
        exerciceService.deleteExerciceById(id);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }





}
