package com.my_training_log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class MuscleDto {
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private MuscleGroupDto muscleGroup;

    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
