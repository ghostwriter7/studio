package com.rozplochowski.studio.classmanagement.application.port.in;

import com.rozplochowski.studio.classmanagement.domain.DifficultyLevel;


public record CreateDanceClassCommand(
        String activity,
        String description,
        DifficultyLevel difficultyLevel
) {

}
