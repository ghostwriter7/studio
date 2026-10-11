package com.rozplochowski.studio.classrooms.application.exception;

public class DuplicateClassroomNameException extends RuntimeException {

    public DuplicateClassroomNameException(String name) {
        super("A classroom '%s' with this name already exists".formatted(name));
    }
}
