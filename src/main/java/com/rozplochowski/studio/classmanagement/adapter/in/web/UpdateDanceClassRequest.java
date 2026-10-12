package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.domain.Difficulty;

public record UpdateDanceClassRequest(
        Long id,
        String activity,
        String description,
        Difficulty difficulty
) {
}
