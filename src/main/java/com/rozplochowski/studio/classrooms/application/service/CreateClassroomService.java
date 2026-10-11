package com.rozplochowski.studio.classrooms.application.service;

import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomUseCase;
import com.rozplochowski.studio.classrooms.application.port.out.SaveClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateClassroomService implements CreateClassroomUseCase {

    private final SaveClassroomPort saveClassroomPort;

    @Override
    public Classroom create(CreateClassroomCommand command) {
        var classroom = new Classroom(command.name(), command.capacity());
        return saveClassroomPort.save(classroom);
    }
}
