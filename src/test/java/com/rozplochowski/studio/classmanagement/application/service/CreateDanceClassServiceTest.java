package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.application.port.out.SaveDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.classmanagement.domain.Difficulty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDanceClassServiceTest {

    @Mock
    private SaveDanceClassPort saveDanceClassPort;

    @InjectMocks
    private CreateDanceClassService createDanceClassService;

    @Test
    void shouldCreateDanceClass() {
        var command = new CreateDanceClassCommand("Lindy Hop Intro", "An introduction to Lindy Hop dance style for beginners.", Difficulty.EASY);
        var danceClass = new DanceClass(command.activity(), command.description(), command.difficulty());
        var expected = DanceClass.reconstitute(10L, command.activity(), command.description(), command.difficulty());
        when(saveDanceClassPort.save(danceClass)).thenReturn(expected);

        var actual = createDanceClassService.create(command);

        assertEquals(expected, actual);
        verify(saveDanceClassPort).save(danceClass);
    }
}