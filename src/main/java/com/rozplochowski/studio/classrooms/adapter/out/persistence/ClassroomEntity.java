package com.rozplochowski.studio.classrooms.adapter.out.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "classroom",
        check = {
                @CheckConstraint(name = "ck_classroom_capacity_positive", constraint = "capacity > 0")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_classroom_name", columnNames = "name")
        })
@Getter
@Setter
public class ClassroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Positive @NotNull
    private int capacity;

}
