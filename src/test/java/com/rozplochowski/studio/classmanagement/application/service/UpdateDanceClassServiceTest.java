package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.exception.DanceClassNotFoundException;
import com.rozplochowski.studio.classmanagement.application.port.in.UpdateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.application.port.out.LoadDanceClassPort;
import com.rozplochowski.studio.classmanagement.application.port.out.UpdateDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.classmanagement.domain.Difficulty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDanceClassServiceTest {

    @Mock
    private UpdateDanceClassPort updateDanceClassPort;

    @Mock
    private LoadDanceClassPort loadDanceClassPort;

    @InjectMocks
    private UpdateDanceClassService updateDanceClassService;

    @Test
    void shouldThrowExceptionWhenDanceClassCannotBeFound() {
        var command = new UpdateDanceClassCommand(10L, "Dance", "Introduction class for beginners", Difficulty.EASY);
        when(loadDanceClassPort.findById(command.id())).thenReturn(Optional.empty());

        assertThrows(DanceClassNotFoundException.class, () -> updateDanceClassService.updateDanceClass(command));

        verify(updateDanceClassPort, never()).update(any(DanceClass.class));
    }

    @Test
    void shouldUpdateDanceClass() {
        var command = new UpdateDanceClassCommand(10L, "Dance", "Introduction class for beginners", Difficulty.EASY);
        when(loadDanceClassPort.findById(command.id()))
                .thenReturn(Optional.of(DanceClass.reconstitute(command.id(), "Old Name", "Old Description", Difficulty.HARD)));
        var expected = DanceClass.reconstitute(command.id(), command.activity(), command.description(), command.difficulty());
        when(updateDanceClassPort.update(expected)).thenReturn(expected);

        var actual = updateDanceClassService.updateDanceClass(command);

        verify(updateDanceClassPort).update(expected);
        assertEquals(expected, actual);
    }
}