package com.rozplochowski.studio.classrooms.adapter.in.web;

import com.rozplochowski.studio.classrooms.application.port.in.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ClassroomController {
    private final CreateClassroomUseCase createClassroomUseCase;
    private final UpdateClassroomUseCase updateClassroomUseCase;
    private final GetClassroomByIdUseCase getClassroomByIdUseCase;

    @GetMapping("/{id}")
    ClassroomResponse getClassroomById(@PathVariable @Positive long id) {
        var classroom = getClassroomByIdUseCase.getById(id);
        return new ClassroomResponse(classroom.getId(), classroom.getName(), classroom.getCapacity());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    ResponseEntity<ClassroomResponse> createClassroom(@Valid @RequestBody CreateClassroomRequest request) {
       var command = new CreateClassroomCommand(request.name(), request.capacity());
       var classroom = createClassroomUseCase.create(command);

       var location = URI.create("/classrooms/" + classroom.getId());

       return ResponseEntity
               .created(location)
               .body(new ClassroomResponse(classroom.getId(), classroom.getName(), classroom.getCapacity()));
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
