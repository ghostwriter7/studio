package com.rozplochowski.studio.classmanagement.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DanceClassTest {

    @ParameterizedTest
    @MethodSource("invalidDanceClasses")
    void shouldFailToCreateDanceClassWithInvalidParameters(String activity, Difficulty difficulty) {
        assertThrows(IllegalArgumentException.class, () -> new DanceClass(activity, "description", difficulty));
    }

    @Test
    void shouldInstantiateDanceClass() {
        var danceClass = new DanceClass("valid activity", "description", Difficulty.EASY);

        assertEquals("valid activity", danceClass.getActivity());
        assertEquals("description", danceClass.getDescription());
        assertEquals(Difficulty.EASY, danceClass.getDifficulty());
        assertNull(danceClass.getId());
    }

    @Test
    void shouldReconstituteDanceClassFromPersistence() {
        var danceClass =  DanceClass.reconstitute(10L, "valid activity", "description", Difficulty.HARD);

        assertEquals(10L, danceClass.getId());
        assertEquals("valid activity", danceClass.getActivity());
        assertEquals("description", danceClass.getDescription());
        assertEquals(Difficulty.HARD, danceClass.getDifficulty());
    }

    private static Stream<Arguments> invalidDanceClasses() {
        return Stream.of(
                Arguments.of(null, Difficulty.EASY),
                Arguments.of("", Difficulty.EASY),
                Arguments.of(" ", Difficulty.EASY),
                Arguments.of("valid activity", null)
        );
    }

}