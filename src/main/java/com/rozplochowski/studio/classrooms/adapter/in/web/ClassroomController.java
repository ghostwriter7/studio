package com.rozplochowski.studio.classrooms.adapter.in.web;

import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.in.CreateClassroomUseCase;
import com.rozplochowski.studio.classrooms.application.port.in.UpdateClassroomCommand;
import com.rozplochowski.studio.classrooms.application.port.in.UpdateClassroomUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ClassroomController {
    private final CreateClassroomUseCase createClassroomUseCase;
    private final UpdateClassroomUseCase updateClassroomUseCase;

    @PostMapping
    ClassroomResponse createClassroom(@Valid @RequestBody CreateClassroomRequest request) {
       var command = new CreateClassroomCommand(request.name(), request.capacity());
       var classroom = createClassroomUseCase.create(command);
       return new ClassroomResponse(classroom.getId(), classroom.getName(), classroom.getCapacity());
    }

    @PutMapping("/{id}")
    ClassroomResponse updateClassroom(
            @PathVariable @Positive long id,
            @Valid @RequestBody UpdateClassroomRequest request) {
        var command = new UpdateClassroomCommand(id, request.name(), request.capacity());
        var classroom = updateClassroomUseCase.update(command);
        return new ClassroomResponse(classroom.getId(), classroom.getName(), classroom.getCapacity());
    }
}
