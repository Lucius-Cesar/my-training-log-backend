package com.my_training_log.controller;

import com.my_training_log.dto.MuscleGroupDto;
import com.my_training_log.service.MuscleGroupService;
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
public class MuscleGroupController {

    public static final String MUSCLE_GROUP_PATH = "/api/v1/muscleGroup";
    public static final String MUSCLE_GROUP_PATH_ID = MUSCLE_GROUP_PATH + "/{muscleGroupId}";

    private final MuscleGroupService muscleGroupService;

    @GetMapping(value = MUSCLE_GROUP_PATH, produces = "application/json")
    public ResponseEntity<List<MuscleGroupDto>> listMuscleGroups() {
        return new ResponseEntity<>(muscleGroupService.listAllMuscleGroups(), HttpStatus.OK);
    }

    @GetMapping(value = MUSCLE_GROUP_PATH_ID, produces = "application/json")
    public ResponseEntity<MuscleGroupDto> getMuscleGroupById(@PathVariable("muscleGroupId") UUID muscleGroupId) {
        return new ResponseEntity<>(muscleGroupService.getMuscleGroupById(muscleGroupId), HttpStatus.OK);
    }

    @PostMapping(MUSCLE_GROUP_PATH)
    public ResponseEntity<Void> createMuscleGroup(@RequestBody @Validated MuscleGroupDto muscleGroupDto) {
        MuscleGroupDto savedMuscleGroup = muscleGroupService.createMuscleGroup(muscleGroupDto);
        String location = MUSCLE_GROUP_PATH + "/" + savedMuscleGroup.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", location);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(MUSCLE_GROUP_PATH_ID)
    public ResponseEntity<Void> updateMuscleGroupById(@PathVariable("muscleGroupId") UUID id, @RequestBody @Validated MuscleGroupDto muscleGroupDto) {
        muscleGroupService.updateMuscleGroupById(id, muscleGroupDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(MUSCLE_GROUP_PATH_ID)
    public ResponseEntity<Void> deleteMuscleGroupById(@PathVariable("muscleGroupId") UUID id) {
        muscleGroupService.deleteMuscleGroupById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
