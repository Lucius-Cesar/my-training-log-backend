package com.my_training_log.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
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
public class Exercice {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR) // say to hibernate to store UID As character instead of binary (not compatible with sql)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotNull @NotBlank
    private String name;

    private String description;

    @NotEmpty @NotNull
    private List<UUID> referenceMuscles;
    @NotEmpty @NotNull
    private List <UUID> primaryMusclesEngaged;
    private List <UUID> secondaryMusclesEngaged;

    @Version
    private Integer version;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
