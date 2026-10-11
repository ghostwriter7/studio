package com.rozplochowski.studio.classrooms.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateClassroomRequest(
        @NotBlank String name,
        @Positive int capacity
) {
}
