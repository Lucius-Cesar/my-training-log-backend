package com.my_training_log.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exercice {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR) // say to hibernate to store UID As character instead of binary (not compatible with sql)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    @ManyToMany
    @JoinTable(
            name = "exercice_reference_muscle_group",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private List<MuscleGroup> referenceMuscleGroups;

    @NotEmpty
    @ManyToMany
    @JoinTable(
            name = "exercice_primary_muscle_group",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private List <MuscleGroup> primaryMuscleGroups;

    @ManyToMany
    @JoinTable(
            name = "exercice_secondary_muscle_group",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private List <MuscleGroup> secondaryMuscleGroups;

    @ManyToMany
    @JoinTable(
            name = "exercice_specific_muscle_target",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_id")
    )

    // only if there is specific muscle of the muscle group targeted in the exercice
    // e.g. only the long head of the triceps is targeted in pull-up movements.
    private List <Muscle> specificMusclesInGroup;

    @Version
    private Integer version;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
