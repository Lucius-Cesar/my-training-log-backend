package com.my_training_log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ExerciceDto {

    private UUID id;

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<MuscleGroupDto> referenceMuscleGroups;
    @NotEmpty
    private List <MuscleGroupDto> primaryMuscleGroups;
    private List <MuscleGroupDto> secondaryMuscleGroups;

    private List <MuscleDto> specificMusclesInGroup	;

    private Integer version;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
