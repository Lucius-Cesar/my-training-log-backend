package com.my_training_log.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ExerciceDto {

    private UUID id;

    @NotNull @NotBlank
    private String name;

    private String description;

    @NotEmpty @NotNull
    private List<UUID> referenceMuscles;
    @NotEmpty @NotNull
    private List <UUID> primaryMusclesEngaged;
    private List <UUID> secondaryMusclesEngaged;

    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;

}
