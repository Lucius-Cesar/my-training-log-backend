package com.my_training_log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ExerciceDetailDto {
    public class ExerciceDto {

        private UUID id;

        @NotNull
        @NotBlank
        private String name;

        private String description;

        @NotEmpty @NotNull
        private List<MuscleDto> referenceMuscles;
        @NotEmpty @NotNull
        private List <MuscleDto> primaryMusclesEngaged;
        private List <MuscleDto> secondaryMusclesEngaged;

        private Integer version;
        private LocalDateTime creationDate;
        private LocalDateTime lastUpdateDate;
    }
}
