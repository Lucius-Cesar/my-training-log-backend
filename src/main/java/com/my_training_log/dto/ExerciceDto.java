package com.my_training_log.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import jakarta.validation.constraints.NotNull;


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
    private List<MuscleDto> referenceMuscles;
    @NotEmpty
    private List <MuscleDto> primaryMusclesEngaged;
    private List <MuscleDto> secondaryMusclesEngaged;

    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;

}
