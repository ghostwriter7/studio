package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.exception.DanceClassNotFoundException;
import com.rozplochowski.studio.classmanagement.application.port.in.GetDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.in.GetDanceClassesUseCase;
import com.rozplochowski.studio.classmanagement.application.port.out.LoadDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.shared.application.in.PagedRequest;
import com.rozplochowski.studio.shared.application.in.PagedResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GetDanceClassService implements GetDanceClassUseCase, GetDanceClassesUseCase {

    private final LoadDanceClassPort loadDanceClassPort;

    @Override
    public DanceClass getById(Long id) {
        return loadDanceClassPort.getById(id)
                .orElseThrow(() -> new DanceClassNotFoundException(id));
    }

    @Override
    public PagedResponse<DanceClass> getAll(PagedRequest pagedRequest) {
        return loadDanceClassPort.getAll(pagedRequest);
    }
}
