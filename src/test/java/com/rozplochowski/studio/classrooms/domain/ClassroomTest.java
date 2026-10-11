package com.rozplochowski.studio.classrooms.domain;

import com.rozplochowski.studio.classrooms.domain.exception.InvalidClassroomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ClassroomTest {

    @ParameterizedTest
    @MethodSource("invalidClassroomValues")
    void shouldRejectInvalidValuesWhenCreating(String name, Integer capacity) {
        assertThrows(InvalidClassroomException.class, () -> new Classroom(name, capacity));
    }

    @ParameterizedTest
    @MethodSource("invalidClassroomValues")
    void shouldRejectInvalidValuesWhenUpdating(String name, Integer capacity) {
        var classroom = new Classroom("Test", 10);

        assertThrows(InvalidClassroomException.class, () -> classroom.updateDetails(name, capacity));

        assertEquals("Test", classroom.getName());
        assertEquals(10, classroom.getCapacity());
    }

    @Test
    void shouldCreateNewClassroom() {
        var classroom = new Classroom("Test", 10);

        assertEquals("Test", classroom.getName());
        assertEquals(10, classroom.getCapacity());
    }

    private static Stream<Arguments> invalidClassroomValues() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, 0),
                Arguments.of(null, 1),
                Arguments.of(null, -1),
                Arguments.of("", 1),
                Arguments.of("  ", 1)
        );
    }

}