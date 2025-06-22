package com.my_training_log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class MuscleGroupDto {
    private UUID id;

    @NotNull @NotBlank
    private String name;

    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
