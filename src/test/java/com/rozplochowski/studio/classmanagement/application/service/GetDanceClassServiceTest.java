package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.exception.DanceClassNotFoundException;
import com.rozplochowski.studio.classmanagement.application.port.out.LoadDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.classmanagement.domain.Difficulty;
import com.rozplochowski.studio.shared.application.in.PagedRequest;
import com.rozplochowski.studio.shared.application.in.PagedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDanceClassServiceTest {

    @Mock
    private LoadDanceClassPort loadDanceClassPort;

    @InjectMocks
    private GetDanceClassService getDanceClassService;

    @Test
    void shouldThrowExceptionWhenDanceClassDoesNotExist() {
        when(loadDanceClassPort.getById(10L)).thenReturn(Optional.empty());

        assertThrows(DanceClassNotFoundException.class, () -> getDanceClassService.getById(10L));
    }

    @Test
    void shouldFetchDanceClassSuccessfully() {
        var danceClass = DanceClass.reconstitute(20L, "Dance", "Beginners' class", Difficulty.EASY);
        when(loadDanceClassPort.getById(20L)).thenReturn(Optional.of(danceClass));

        var actual = getDanceClassService.getById(20L);

        assertEquals(danceClass, actual);
    }

    @Test
    void shouldGetAllDanceClasses() {
        var pagedRequest = new PagedRequest(10, 2);
        var pagedResponse = new PagedResponse<DanceClass>(List.of(), pagedRequest.pageNumber(), pagedRequest.pageSize(), 2);
        when(loadDanceClassPort.getAll(pagedRequest)).thenReturn(pagedResponse);

        var actual = getDanceClassService.getAll(pagedRequest);

        assertEquals(pagedResponse, actual);
    }
}