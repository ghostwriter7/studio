package com.rozplochowski.studio.classrooms.application.port.in;

public record UpdateClassroomCommand(
        long id,
        String name,
        int capacity
) {
}
