package com.rozplochowski.studio.classmanagement.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class DanceClass {

    private Long id;
    private String activity;
    private String description;
    private DifficultyLevel difficultyLevel;

    public DanceClass(String activity, String description, DifficultyLevel difficultyLevel) {
        validateActivity(activity);
        validateDifficulty(difficultyLevel);

        this.activity = activity;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
    }

    public static DanceClass reconstitute(Long id, String activity, String description, DifficultyLevel difficultyLevel) {
        DanceClass danceClass = new DanceClass(activity, description, difficultyLevel);
        danceClass.id = id;
        return danceClass;
    }

    private void validateActivity(String activity) {
        Objects.requireNonNull(activity, "Activity cannot be null");

        if (activity.isBlank()) {
            throw new IllegalArgumentException("Activity cannot be blank");
        }
    }

    private void validateDifficulty(DifficultyLevel difficultyLevel) {
        Objects.requireNonNull(difficultyLevel, "Difficulty level cannot be null");
    }
}
