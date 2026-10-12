package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.domain.DifficultyLevel;

public record CreateDanceClassRequest(
        String activity,
        String description,
        DifficultyLevel difficultyLevel
) {
}
