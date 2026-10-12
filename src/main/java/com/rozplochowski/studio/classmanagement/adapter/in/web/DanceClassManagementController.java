package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.in.GetDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.in.GetDanceClassesUseCase;
import com.rozplochowski.studio.classmanagement.application.port.in.UpdateDanceClassUseCase;
import com.rozplochowski.studio.shared.application.in.PagedRequest;
import com.rozplochowski.studio.shared.application.in.PagedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/dance-classes")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DanceClassManagementController {
    private final CreateDanceClassUseCase createDanceClassUseCase;
    private final UpdateDanceClassUseCase updateDanceClassUseCase;
    private final GetDanceClassUseCase getDanceClassUseCase;
    private final GetDanceClassesUseCase getDanceClassesUseCase;
    private final WebDanceClassMapper mapper;

    @GetMapping("/{id}")
    DanceClassResponse getById(@PathVariable @Positive long id) {
        var danceClass = getDanceClassUseCase.getById(id);
        return mapper.toResponse(danceClass);
    }

    @GetMapping
    PagedResponse<DanceClassResponse> getAll(@RequestParam(value = "pageSize", defaultValue = "20") @Max(200) @Min(1) int pageSize,
                                             @RequestParam(value = "pageNumber", defaultValue = "0") @Min(0) int pageNumber) {
        var pagedRequest = new PagedRequest(pageSize, pageNumber);
        var pagedResponse = getDanceClassesUseCase.getAll(pagedRequest);

        return new PagedResponse<>(
                pagedResponse.content().stream().map(mapper::toResponse).toList(),
                pagedResponse.pageNumber(),
                pagedResponse.pageSize(),
                pagedResponse.totalPages()
        );

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DanceClassResponse> createDanceClass(@Valid @RequestBody CreateDanceClassRequest request) {
        var command = mapper.toCommand(request);
        var danceClass = createDanceClassUseCase.create(command);

        var location = URI.create("/dance-classes/" + danceClass.getId());
        return ResponseEntity
                .created(location)
                .body(mapper.toResponse(danceClass));
    }

    @PutMapping("/{id}")
    DanceClassResponse updateDanceClass(@PathVariable @Positive long id, @Valid @RequestBody UpdateDanceClassRequest request) {
        var command = mapper.toCommand(id, request);
        var danceClass = updateDanceClassUseCase.updateDanceClass(command);
        return mapper.toResponse(danceClass);
    }
}
