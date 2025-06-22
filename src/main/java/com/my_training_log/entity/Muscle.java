package com.my_training_log.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Muscle {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR) // say to hibernate to store UID As character instead of binary (not compatible with sql)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    private String name;

    @ManyToOne
    private MuscleGroup muscleGroup;

    @ManyToMany
    @JoinTable(
            name = "muscle_reference_exercice",
            joinColumns = @JoinColumn(name = "muscle_id"),
            inverseJoinColumns = @JoinColumn(name = "exercice_id")
    )
    private List<Exercice> referenceExercices;

    @ManyToMany
    @JoinTable(
            name = "muscle_primary_exercice",
            joinColumns = @JoinColumn(name = "muscle_id"),
            inverseJoinColumns = @JoinColumn(name = "exercice_id")
    )
    private List<Exercice> primaryEngagedInExercices;

    @ManyToMany
    @JoinTable(
            name = "muscle_secondary_exercice",
            joinColumns = @JoinColumn(name = "muscle_id"),
            inverseJoinColumns = @JoinColumn(name = "exercice_id")
    )
    private List<Exercice> secondaryEngagedInExercices;



    @Version
    private Integer version;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
