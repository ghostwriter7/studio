package com.rozplochowski.studio.classmanagement.domain;

import com.rozplochowski.studio.classmanagement.domain.exception.InvalidDanceClassException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DanceClass {

    private Long id;
    private String activity;
    private String description;
    private Difficulty difficulty;

    public DanceClass(String activity, String description, Difficulty difficulty) {
        validateActivity(activity);
        validateDifficulty(difficulty);

        this.activity = activity;
        this.description = description;
        this.difficulty = difficulty;
    }

    public static DanceClass reconstitute(Long id, String activity, String description, Difficulty difficulty) {
        DanceClass danceClass = new DanceClass(activity, description, difficulty);
        danceClass.id = id;
        return danceClass;
    }

    public void updateDetails(String activity, String description, Difficulty difficulty) {
        validateActivity(activity);
        validateDifficulty(difficulty);

        this.activity = activity;
        this.description = description;
        this.difficulty = difficulty;
    }

    private void validateActivity(String activity) {
        if (activity == null || activity.isBlank()) {
            throw new InvalidDanceClassException("Activity cannot be blank");
        }
    }

    private void validateDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new InvalidDanceClassException("Difficulty level cannot be null");
        }
    }
}
