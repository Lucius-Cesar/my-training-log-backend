package com.my_training_log.dto;

import com.my_training_log.entity.Muscle;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private List <MuscleDto> muscles;

    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
