package com.rozplochowski.studio.classrooms.application.service;

import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.out.SaveClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClassroomServiceTest {

    @Mock
    private SaveClassroomPort saveClassroomPort;

    @InjectMocks
    private CreateClassroomService createClassroomService;

    @Test
    void shouldCreateClassroom() {
        var command = new CreateClassroomCommand("New Classroom Name", 50);
        var classroom = new Classroom(command.name(), command.capacity());
        var expected = Classroom.reconstitute(10L, classroom.getName(), classroom.getCapacity());
        when(saveClassroomPort.save(classroom)).thenReturn(expected);

        var result = createClassroomService.create(command);

        verify(saveClassroomPort).save(classroom);
        assertEquals(expected, result);
    }

}