package com.rozplochowski.studio.classmanagement.application.port.in;

import com.rozplochowski.studio.classmanagement.domain.Difficulty;


public record CreateDanceClassCommand(
        String activity,
        String description,
        Difficulty difficulty
) {

}
