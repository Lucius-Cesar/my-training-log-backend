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

    @NotNull @NotBlank
    private String name;

    private UUID muscleGroupId;

    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
