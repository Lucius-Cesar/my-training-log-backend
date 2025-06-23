/*package com.my_training_log.dto;

import com.my_training_log.entity.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ExerciceDetailDto {
        private UUID id;

        @NotBlank
        private String name;

        private String description;

        @NotEmpty
        private List<MuscleGroupDto> referenceMuscleGroups;
        @NotEmpty
        private List <MuscleGroupDto> primaryMusclesGroups;
        private List <MuscleGroupDto> secondaryMuscleGroups;
        private List <MuscleDto> specificMuscleTarget;

        private Integer version;
        private LocalDateTime creationDate;
        private LocalDateTime lastUpdateDate;

}
*/