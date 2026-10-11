package com.rozplochowski.studio.classrooms.adapter.out.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "classroom",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_classroom_name", columnNames = "name")
        })
public class ClassroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Positive
    private byte capacity;

}
