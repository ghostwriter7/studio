package com.rozplochowski.studio.classrooms.application.service;

import com.rozplochowski.studio.classrooms.application.exception.ClassroomNotFoundException;
import com.rozplochowski.studio.classrooms.application.port.out.LoadClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClassroomServiceTest {

    @Mock
    private LoadClassroomPort loadClassroomPort;

    @InjectMocks
    private GetClassroomService getClassroomByIdUseCase;

    @Test
    void shouldReturnClassroomById() {
        var classroom = Classroom.reconstitute(10L, "Classroom 10", 20);
        when(loadClassroomPort.loadById(10L)).thenReturn(Optional.of(classroom));

        var result = getClassroomByIdUseCase.getById(10L);

        assertEquals(classroom, result);
    }

    @Test
    void shouldThrowExceptionWhenClassroomNotFound() {
        when(loadClassroomPort.loadById(10L)).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> getClassroomByIdUseCase.getById(10L));
    }

}