package com.my_training_log.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class MuscleGroupDto {
    private UUID id;

    @NotBlank
    private String name;

    private Integer version;

    @NotEmpty
    private List<MuscleDto> muscles;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
