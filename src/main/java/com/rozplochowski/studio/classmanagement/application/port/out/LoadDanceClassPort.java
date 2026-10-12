package com.rozplochowski.studio.classmanagement.application.port.out;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;

import java.util.Optional;

public interface LoadDanceClassPort {

    Optional<DanceClass> findById(Long id);
}
