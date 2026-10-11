package com.rozplochowski.studio.classrooms.application.port.in;

public record CreateClassroomCommand(
        String name,
        Byte capacity
) {
}
