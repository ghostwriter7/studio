package com.rozplochowski.studio.classrooms.application.service;

import com.rozplochowski.studio.classrooms.application.exception.ClassroomNotFoundException;
import com.rozplochowski.studio.classrooms.application.port.in.UpdateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.out.LoadClassroomPort;
import com.rozplochowski.studio.classrooms.application.port.out.SaveClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
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
class UpdateClassroomServiceTest {

    @Mock
    private LoadClassroomPort loadClassroomPort;

    @Mock
    private SaveClassroomPort saveClassroomPort;

    @InjectMocks
    private UpdateClassroomService updateClassroomService;

    @Test
    void shouldUpdateClassroom() {
        var id = 10L;
        var command = new UpdateClassroomCommand(id, "New Classroom Name", 50);
        var existing = Classroom.reconstitute(id, "Old Classroom Name", 30);
        var expected = Classroom.reconstitute(id, command.name(), command.capacity());

        when(loadClassroomPort.loadById(command.id())).thenReturn(Optional.of(existing));
        when(saveClassroomPort.save(expected)).thenReturn(expected);

        var result = updateClassroomService.update(command);

        assertEquals(expected, result);
        verify(saveClassroomPort).save(expected);
    }

    @Test
    void shouldFailToUpdateClassroomWhenClassroomNotFound() {
        var command = new UpdateClassroomCommand(10L, "New Classroom Name", 50);

        when(loadClassroomPort.loadById(command.id())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> updateClassroomService.update(command));
        verify(loadClassroomPort).loadById(command.id());
        verifyNoInteractions(saveClassroomPort);
    }
}