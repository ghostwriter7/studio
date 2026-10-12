package com.rozplochowski.studio.classmanagement.application.port.out;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.shared.application.in.PagedRequest;
import com.rozplochowski.studio.shared.application.in.PagedResponse;

import java.util.Optional;

public interface LoadDanceClassPort {

    Optional<DanceClass> getById(Long id);

    PagedResponse<DanceClass> getAll(PagedRequest pagedRequest);
}
