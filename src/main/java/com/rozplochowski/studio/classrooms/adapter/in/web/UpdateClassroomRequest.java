package com.rozplochowski.studio.classrooms.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdateClassroomRequest(
        @NotBlank String name,
        @Positive int capacity
) {
}
