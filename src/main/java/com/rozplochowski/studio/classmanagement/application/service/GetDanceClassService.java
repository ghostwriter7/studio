package com.rozplochowski.studio.classmanagement.application.service;

import com.rozplochowski.studio.classmanagement.application.exception.DanceClassNotFoundException;
import com.rozplochowski.studio.classmanagement.application.port.in.GetDanceClassUseCase;
import com.rozplochowski.studio.classmanagement.application.port.out.LoadDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetDanceClassService implements GetDanceClassUseCase {

    private final LoadDanceClassPort loadDanceClassPort;

    @Override
    public DanceClass getById(Long id) {
        return loadDanceClassPort.findById(id)
                .orElseThrow(() -> new DanceClassNotFoundException(id));
    }
}
